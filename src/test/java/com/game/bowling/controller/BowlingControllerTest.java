package com.game.bowling.controller;

import com.game.bowling.model.BowlingRequest;
import com.game.bowling.model.BowlingRollRequest;
import com.game.bowling.service.BowlingService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class BowlingControllerTest {

    private final BowlingService bowlingService = mock(BowlingService.class);
    private final BowlingController bowlingController = new BowlingController(bowlingService);
    private final String gameId = "some-game-id";

    @Test
    void shouldCallServiceToStartGame() {

        BowlingRequest request = mock(BowlingRequest.class);

        bowlingController.start(request);

        verify(bowlingService, times(1)).startGame(request);
    }

    @Test
    void shouldCallServiceToRoll() {

        BowlingRollRequest rollRequest = mock(BowlingRollRequest.class);

        bowlingController.roll(gameId, List.of(rollRequest));

        verify(bowlingService, times(1)).roll(gameId, List.of(rollRequest));
    }

    @Test
    void shouldCallServiceToGetScore() {

        bowlingController.score(gameId);

        verify(bowlingService, times(1)).scoreBoard(gameId);
    }

    @Test
    void shouldCallServiceToGetScoreOfGivenPlayer() {

        String playerName = "test-player";
        bowlingController.score(gameId, playerName);

        verify(bowlingService, times(1)).scoreBoard(gameId, playerName);
    }
}