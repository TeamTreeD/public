package org.jugsaxony.treed.web.controller;

import org.jugsaxony.treed.api.AnimationStrategy;
import org.jugsaxony.treed.web.WebSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    public ResponseEntity<Map<String, String>> getStrategies() {
        Map<String, String> result = strategies.stream()
                .filter(s -> callOk(s, AnimationStrategy::getAuthorName))
                .filter(s -> callOk(s, AnimationStrategy::getStrategyName))
                .filter(s -> callOk(s, AnimationStrategy::getAuthorEmail))
                .filter(s -> callOk(s, AnimationStrategy::getDescription))
                .collect(Collectors.toMap(
            s -> s.getClass().getName(),
            s -> s.getStrategyName()+" ("+s.getAuthorName()+")"));
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


    private boolean callOk(AnimationStrategy target, Function<AnimationStrategy, String> call) {
        try {
            call.apply(target);
            return true;
        } catch (RuntimeException re) {
            return false;
        }
    }
}
