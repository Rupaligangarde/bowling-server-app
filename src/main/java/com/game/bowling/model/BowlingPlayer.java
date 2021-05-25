package com.game.bowling.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

@Data
public class BowlingPlayer {
    private final String name;
    private static Integer maxPoints;
    private static Integer maxFrames;
    private static Integer strikePoint;
    private static Integer sparePoint;
    private final int[] rolls;
    private int currentRoll;
    private int currentFrame;

    public BowlingPlayer(
            String name,
            @Value("${bowling.maxPoints}") Integer maxPoints,
            @Value("${bowling.maxFrame}") Integer maxFrames,
            @Value("${bowling.strikePoint}") Integer strikePoint,
            @Value("${bowling.sparePoint}") Integer sparePoint
    ) {
        this.name = name;
        BowlingPlayer.maxPoints = maxPoints;
        BowlingPlayer.maxFrames = maxFrames;
        BowlingPlayer.strikePoint = strikePoint;
        BowlingPlayer.sparePoint = sparePoint;
        this.rolls = new int[21];
        this.currentRoll = 0;
        this.currentFrame = 0;
    }

    public void roll(List<Integer> points) {
        points.forEach(this::roll);
    }

    public void roll(Integer points) {
        rolls[currentRoll] = points;
        currentRoll++;
    }

    public ScoreBoard toScoreBoard() {
        return new ScoreBoard(this.name, score(), Arrays.copyOfRange(rolls, 0, currentRoll));
    }

    public void incrementFrame() {
        currentFrame++;
    }

    public boolean isLastFrame() {
        return currentFrame == BowlingPlayer.maxFrames;
    }

    public boolean isLastStrike() {
        return rolls[currentRoll - 1] == BowlingPlayer.strikePoint;
    }

    public boolean isLastSpare() {
        return rolls[currentRoll - 1] + rolls[currentRoll - 2] == BowlingPlayer.strikePoint;
    }

    private int score() {
        int frame = 0, score = 0;
        for (int i = 0; i < BowlingPlayer.maxFrames; i++) {
            if (isStrike(frame)) {
                score += BowlingPlayer.strikePoint + strikeBonus(frame);
                frame++;
            } else if (isSpare(frame)) {
                score += BowlingPlayer.sparePoint + spareBonus(frame);
                frame += 2;
            } else {
                score += sumOfRolls(frame);
                frame += 2;
            }
        }
        return score;
    }

    private boolean isStrike(int frame) {
        return rolls[frame] == BowlingPlayer.strikePoint;
    }

    private boolean isSpare(int frame) {
        return sumOfRolls(frame) == BowlingPlayer.sparePoint;
    }

    private int strikeBonus(int frame) {
        return sumOfRolls(frame + 1);
    }

    private int spareBonus(int frame) {
        return rolls[frame + 2];
    }

    private int sumOfRolls(int frame) {
        return rolls[frame] + rolls[frame + 1];
    }
}
