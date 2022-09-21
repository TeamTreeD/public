package org.jugsaxony.treed.web.controller;

import com.google.common.collect.Maps;
import org.jugsaxony.treed.api.AnimationStrategy;
import org.jugsaxony.treed.web.WebSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class SimulationController
{
    @Autowired
    private WebSimulator webSimulator;

    @Autowired
    private List<double[]> positions;

    @Autowired
    private Set<AnimationStrategy> strategies;

    @GetMapping(path = "/positions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<double[]>> getPositions() {
        return ResponseEntity.ok(positions);
    }

    @GetMapping(path = "/strategies", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Set<Map<String, String>>> getStrategies() {
        Set<Map<String, String>> result = strategies.stream()
                .map(s -> toDescription(s))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(result);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping(path = "/color-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<Flux<String>> streamFlux(@RequestParam("strategy") String strategy) {
        Optional<Flux<String>> fluxOpt = webSimulator.getFlux(strategy);
        if (fluxOpt.isPresent()) {
            return ResponseEntity.ok(fluxOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Map<String, String> toDescription(AnimationStrategy strategy) {
        Map<String, String> result = new HashMap<>();
        result.put("class", strategy.getClass().getName());
        result.put("name", callOk(strategy, AnimationStrategy::getStrategyName) ? strategy.getStrategyName() : "Your new unnamed strategy");
        result.put("author", callOk(strategy, AnimationStrategy::getAuthorName) ? strategy.getAuthorName() : "");
        result.put("enabled", Boolean.toString(
        callOk(strategy, AnimationStrategy::getAuthorName) &&
           callOk(strategy, AnimationStrategy::getStrategyName) &&
           callOk(strategy, AnimationStrategy::getAuthorEmail) &&
           callOk(strategy, AnimationStrategy::getDescription)
        ));
        return result;
    }

    private boolean callOk(AnimationStrategy target, Function<AnimationStrategy, String> call) {
        try {
            call.apply(target);
            return true;
        } catch (RuntimeException re) {
            return false;
        }
    }
}
