package com.game.bowling.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.bowling.model.BowlingPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BowlingRequest {

    @JsonProperty("players")
    Set<String> players;

    public Map<String, BowlingPlayer> toBowlingPlayers() {
        return this.players
                .stream()
                .map(name -> new BowlingPlayer(name, 10, 10, 10, 10))
                .collect(Collectors.toMap(BowlingPlayer::getName, Function.identity()));
    }
}
