package org.jugsaxony.treed.web.config;

import org.jugsaxony.treed.web.WebSimulator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Configuration
public class WebSimulatorConfig
{
    @Bean
    public WebSimulator webSimulator() {
        return new WebSimulator();
    }
}
