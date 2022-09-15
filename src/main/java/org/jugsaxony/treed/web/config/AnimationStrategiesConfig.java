package org.jugsaxony.treed.web.config;

import org.jugsaxony.treed.api.AnimationStrategy;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AnimationStrategiesConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimationStrategiesConfig.class);

    @Bean
    public Set<AnimationStrategy> strategies() {
        Reflections reflections = new Reflections("org");
        Set<AnimationStrategy> result = new HashSet<>();

        Set<Class<? extends AnimationStrategy>> candidates = reflections.getSubTypesOf(AnimationStrategy.class);
        for (Class<? extends AnimationStrategy> candidate : candidates) {
            if (Modifier.isAbstract(candidate.getModifiers())) {
                // Not instantiatable, skip.
                continue;
            }
            try {
                AnimationStrategy strategy = candidate.getConstructor().newInstance();
                result.add(strategy);
                LOGGER.info("Instantiated strategy "+strategy.getStrategyName()+" by "+ strategy.getAuthorName());
            } catch (RuntimeException re) {
                // Skip this one.
            } catch (Exception e) {
                LOGGER.error("Cannot instantiate strategy "+candidate.getName());
            }
        }

        return result;
    }
}
