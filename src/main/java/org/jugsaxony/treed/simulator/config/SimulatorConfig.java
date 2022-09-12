package org.jugsaxony.treed.simulator.config;

import org.jugsaxony.treed.simulator.Simulator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimulatorConfig
{
    @Bean
    public Simulator simulator() {
        return new Simulator();
    }
}
