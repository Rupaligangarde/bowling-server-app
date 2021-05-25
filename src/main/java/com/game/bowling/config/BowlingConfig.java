package com.game.bowling.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BowlingConfig {

    @Getter
    private final Integer maxPlayers;
    @Getter
    private final Integer maxPoints;

    public BowlingConfig(
            @Value("${bowling.maxPlayers}") Integer maxPlayers,
            @Value("${bowling.maxPoints}") Integer maxPoints
    ) {
        this.maxPlayers = maxPlayers;
        this.maxPoints = maxPoints;
    }
}
