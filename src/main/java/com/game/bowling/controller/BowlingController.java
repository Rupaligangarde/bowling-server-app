package com.game.bowling.controller;

import com.game.bowling.model.BowlingRequest;
import com.game.bowling.model.BowlingRollRequest;
import com.game.bowling.model.ScoreBoard;
import com.game.bowling.service.BowlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bowling")
public class BowlingController {

    private final BowlingService bowlingService;

    public BowlingController(@Autowired BowlingService bowlingService) {
        this.bowlingService = bowlingService;
    }

    @PostMapping("/start")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String start(@RequestBody BowlingRequest request) {
        return bowlingService.startGame(request);
    }

    @PostMapping("/{gameId}/roll")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void roll(@PathVariable String gameId, @RequestBody List<BowlingRollRequest> request) {
        bowlingService.roll(gameId, request);
    }

    @PostMapping("/v2/{gameId}/roll")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void roll(@PathVariable String gameId) {
        bowlingService.roll(gameId);
    }

    @GetMapping("/{gameId}/score")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ScoreBoard> score(@PathVariable String gameId) {
        return bowlingService.scoreBoard(gameId);
    }

    @GetMapping("/{gameId}/score/{playerName}")
    @ResponseStatus(value = HttpStatus.OK)
    public ScoreBoard score(@PathVariable String gameId, @PathVariable String playerName) {
        return bowlingService.scoreBoard(gameId, playerName);
    }
}
