package org.jugsaxony.treed.web;

import com.google.common.primitives.Ints;
import org.jugsaxony.treed.api.AnimationStrategy;
import org.jugsaxony.treed.api.Bulb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.*;

public class WebSimulator extends Thread implements InitializingBean
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSimulator.class);

    private Map<String, StrategyEntry> strategyEntries = new HashMap<>();

    @Autowired
    List<double[]> positions;

    @Autowired
    Set<AnimationStrategy> strategies;

    public WebSimulator() {
        setDaemon(true);
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        start();
    }

    public Optional<Flux<String>> getFlux(String strategy) {
        synchronized (strategyEntries) {
            StrategyEntry strategyEntry = strategyEntries.get(strategy);
            if (strategyEntry != null) {
                return Optional.of(strategyEntry.getSink().asFlux());
            }

            // Set up new StrategyEntry.
            Optional<AnimationStrategy> animationStrategyOpt = strategies.stream().filter(s -> strategy.equals(s.getClass().getName())).findFirst();
            if (!animationStrategyOpt.isPresent()) {
                return Optional.empty();
            }
            Sinks.Many<String> sink = Sinks.many().multicast().directBestEffort();
            strategyEntry = new StrategyEntry(animationStrategyOpt.get(), sink);
            strategyEntries.put(strategy, strategyEntry);

            return Optional.ofNullable(strategyEntry.getSink().asFlux());
        }
    }

    protected List<Bulb> createBulbs(Sinks.Many sink, List<double[]> positions)
    {
        List<Bulb> result = new ArrayList<>(positions.size());
        for (int i = 0; i < positions.size(); i++) {
            double[] xyz = positions.get(i);
            result.add(new Bulb(i, xyz[0], xyz[1], xyz[2]));
        }
        return result;
    }

    public void run() {
        do {
            synchronized (strategyEntries) {
                int activeStrategies = 0;
                Iterator<String> strategyKeyIter = strategyEntries.keySet().iterator();
                while (strategyKeyIter.hasNext())
                {
                    try
                    {
                        String strategyKey = strategyKeyIter.next();
                        StrategyEntry strategyEntry = strategyEntries.get(strategyKey);

                        AnimationStrategy strategy = strategyEntry.getStrategy();
                        Sinks.Many sink = strategyEntry.getSink();
                        if (sink.currentSubscriberCount() == 0)
                        {
                            // No subscribes for this strategy, so don't advance it.
                            continue;
                        }
                        activeStrategies += 1;
                        List<Bulb> bulbs = strategyEntry.getBulbs();
                        advanceStrategy(strategy, bulbs, sink);
                    } catch (RuntimeException re)
                    {
                        // Don't log this to extensively.
                        LOGGER.info("Deactivating strategy ", re);
                    }
                }
                LOGGER.debug("Active strategies: "+activeStrategies+"/"+strategyEntries.size()+" "+this);
            }
        } while (true);
    }

    private void advanceStrategy(AnimationStrategy strategy, List<Bulb> bulbs, Sinks.Many sink) {
        long timeStamp = System.currentTimeMillis();
        strategy.onStartAnimation(bulbs, System.currentTimeMillis());
        int loopCount = 0;
        StringBuffer buffer = new StringBuffer();
        strategy.onStartFrame(bulbs, timeStamp);
        strategy.calcFrame(bulbs, timeStamp);
        strategy.onEndFrame(bulbs, System.currentTimeMillis());
        buffer.append(loopCount);
        for (Bulb bulb : bulbs)
        {
            byte[] bytes = Ints.toByteArray(bulb.getRgb());
            if (buffer.length() > 0)
            {
                buffer.append(",");
            }
            buffer.append(color(bytes[1]) + "," + color(bytes[2]) + "," + color(bytes[3]));
        }
        sink.tryEmitNext(buffer.toString());
        buffer.setLength(0);
        try
        {
            Thread.sleep(1);
        } catch (InterruptedException ie)
        {
        }
        strategy.onEndAnimation(bulbs, timeStamp);
    }

    private class StrategyEntry {
        private AnimationStrategy strategy;
        private Sinks.Many<String> sink;
        private List<Bulb> bulbs;

        public StrategyEntry(AnimationStrategy strategy, Sinks.Many<String> sink)
        {
            this.strategy = strategy;
            this.sink = sink;
            this.bulbs = createBulbs(sink, positions);
        }

        public List<Bulb> getBulbs()
        {
            return bulbs;
        }

        public AnimationStrategy getStrategy()
        {
            return strategy;
        }

        public Sinks.Many<String> getSink()
        {
            return sink;
        }
    }

    private static String color(int b) {
        return String.format(Locale.ROOT, "%.2f", Byte.toUnsignedInt((byte)b)/255f);
    }
}