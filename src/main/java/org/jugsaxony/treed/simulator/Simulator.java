package org.jugsaxony.treed.simulator;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import org.jugsaxony.treed.api.AnimationStrategy;
import org.jugsaxony.treed.api.Bulb;
import org.jugsaxony.treed.examples.DukeSaxony;
import org.jugsaxony.treed.examples.TeleLotto;
import org.jugsaxony.treed.examples.VerticalRainbow;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Simulator extends Thread implements InitializingBean
{
    @Autowired
    private Sinks.Many sink;

    @Autowired
    List<double[]> positions;

    AnimationStrategy strategy;

    public Simulator() {
        setDaemon(true);
        strategy = loadStrategy(VerticalRainbow.class.getName());
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        start();
    }

    public AnimationStrategy loadStrategy(String className) {
        AnimationStrategy result;

        try {
            result = (AnimationStrategy) (Class.forName(className).newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    protected List<Bulb> createBulbs(List<double[]> positions)
    {
        var result = new ArrayList<Bulb>(positions.size());
        for (int i = 0; i < positions.size(); i++) {
            double[] xyz = positions.get(i);
            result.add(new NotifyingBulb(sink, i, xyz[0], xyz[1], xyz[2]));
        }
        return result;
    }

    public void run() {
        List<Bulb> bulbs;
        long timeStamp;

        System.out.println(strategy.getAuthorName()+" : "+strategy.getStrategyName());

        bulbs = createBulbs(positions);
        strategy.onStartAnimation(bulbs, System.currentTimeMillis());
        int loopCount = 0;
        StringBuffer buffer = new StringBuffer();
        do {
            timeStamp = System.currentTimeMillis();
            strategy.onStartFrame(bulbs, timeStamp);
            strategy.calcFrame(bulbs, timeStamp);
            strategy.onEndFrame(bulbs, System.currentTimeMillis());
/*            var text = bulbs.stream()
                .flatMapToInt(bulb -> Arrays(Ints.toByteArray(bulb.getRgb()), 1, 4))
                .mapToObj(b -> color(b))
                .collect(Collectors.joining(","));*/
            buffer.append(loopCount);
            for (Bulb bulb : bulbs) {
                byte[] bytes = Ints.toByteArray(bulb.getRgb());
                if (buffer.length() > 0) {
                    buffer.append(",");
                }
                buffer.append(color(bytes[1])+","+color(bytes[2])+","+color(bytes[3]));
            }
            sink.tryEmitNext(buffer.toString());
            buffer.setLength(0);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
            }
            loopCount += 1;
        } while (!isInterrupted());
        strategy.onEndAnimation(bulbs, timeStamp);
    }


    private class NotifyingBulb extends Bulb {
        private Sinks.Many sink;

        public NotifyingBulb(Sinks.Many sink, int index, double x, double y, double z) {
            super(index, x, y, z);
            this.sink = sink;
        }

        @Override
        public void setRgb(int rgb)
        {
            /*if (getRgb() != rgb) {
                byte[] bytes = Ints.toByteArray(rgb);
                sink.tryEmitNext(getIndex()+","+color(bytes[1])+","+color(bytes[2])+","+color(bytes[3]));
            }*/
            super.setRgb(rgb);
        }
    }


    private static String color(int b) {
        return String.format(Locale.ROOT, "%.2f", Byte.toUnsignedInt((byte)b)/255f);
    }
}