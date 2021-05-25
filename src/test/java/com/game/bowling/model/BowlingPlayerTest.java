package com.game.bowling.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BowlingPlayerTest {

    @Test
    void shouldReturnReturnScoreForZeroPointEveryRoll() {
        BowlingPlayer player = new BowlingPlayer("test_player", 10, 10, 10, 10);

        for (int i = 0; i < 20; i++) {
            player.roll(List.of(0));
        }

        assertEquals(0, player.toScoreBoard().getScore());
    }

    @Test
    void shouldReturnScoreForOnePointEveryRoll() {
        BowlingPlayer player = new BowlingPlayer("test_player", 10, 10, 10, 10);

        for (int i = 0; i < 20; i++) {
            player.roll(List.of(1));
        }

        assertEquals(20, player.toScoreBoard().getScore());
    }

    @Test
    void shouldReturnScoreForStrikeOnEveryRoll() {

        BowlingPlayer player = new BowlingPlayer("test_player", 10, 10, 10, 10);

        for (int i = 0; i < 20; i++) {
            player.roll(List.of(10));
        }

        assertEquals(300, player.toScoreBoard().getScore());
    }

    @Test
    void shouldReturnScoreWithOneStrike() {
        BowlingPlayer player = new BowlingPlayer("test_player", 10, 10, 10, 10);

        player.roll(List.of(10));
        player.roll(List.of(3));
        player.roll(List.of(4));

        for (int i = 0; i < 17; i++) {
            player.roll(List.of(0));
        }

        assertEquals(24, player.toScoreBoard().getScore());
    }

    @Test
    void shouldReturnScore90() {
        BowlingPlayer player = new BowlingPlayer("test_player", 10, 10, 10, 10);

        for (int i = 0; i < 10; i++) {
            player.roll(List.of(9));
            player.roll(List.of(0));
        }
        assertEquals(90, player.toScoreBoard().getScore());
    }

    @Test
    void shouldReturnScore150() {
        BowlingPlayer player = new BowlingPlayer("test_player", 10, 10, 10, 10);

        for (int i = 0; i < 10; i++) {
            player.roll(List.of(5));
            player.roll(List.of(5));
        }
        player.roll(List.of(5));

        assertEquals(150, player.toScoreBoard().getScore());
    }

    @Test
    void shouldReturnScore131() {
        BowlingPlayer player = new BowlingPlayer("test_player", 10, 10, 10, 10);

        int[] points = {1, 3, 7, 3, 10, 1, 7, 5, 2, 5, 3, 8, 2, 8, 2, 10, 9, 0};

        for (int point : points)
            player.roll(List.of(point));

        assertEquals(131, player.toScoreBoard().getScore());
    }
}