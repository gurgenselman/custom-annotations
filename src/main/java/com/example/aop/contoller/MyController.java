package com.example.aop.contoller;

import com.example.aop.annotation.ForceUpdate;
import com.example.aop.annotation.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/entertainment")
@RequiredArgsConstructor
public class MyController {

    @ForceUpdate(
            platforms = {
            @Platform(platformType = 1, 	version = "${eachgol100mb.force.update.version}"),
            @Platform(platformType = 2, 	version = "${eachgol100mb.force.update.version}")
            }, returnType = Exception.class)
    @GetMapping(value = "/play-each-goal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> playEachGoal100MB() {
        return ResponseEntity.of(Optional.of("test"));
    }

}