package org.jugsaxony.treed.simulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Controller
public class SimulationController
{
    @Autowired
    private Sinks.Many sink;

    @Autowired
    private List<double[]> positions;

    @GetMapping(path = "/positions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<double[]>> getPositions() {
        return ResponseEntity.ok(positions);
    }

    @CrossOrigin(allowedHeaders = "*")
    @GetMapping(path = "/color-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<String> streamFlux() {
        return this.sink.asFlux();
    }
}
