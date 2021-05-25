package com.game.bowling.model;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BowlingConfigRequestTest {
    @Test
    void shouldReturnBowlingPlayer() {

        String playerName1 = "test-player1";
        String playerName2 = "test-player2";
        BowlingRequest bowlingRequest = new BowlingRequest(Set.of(playerName1, playerName2));

        Map<String, BowlingPlayer> actual = bowlingRequest.toBowlingPlayers();
        Map<String, BowlingPlayer> expected = Map.of(
                playerName1, new BowlingPlayer(playerName1, 10, 10, 10, 10),
                playerName2, new BowlingPlayer(playerName2, 10, 10, 10, 10)
        );

        assertEquals(expected, actual);
    }
}