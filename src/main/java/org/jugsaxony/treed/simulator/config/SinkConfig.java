package org.jugsaxony.treed.simulator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig
{
    @Bean
    public Sinks.Many sink() {
        return Sinks.many().multicast().directBestEffort();
    }
}
