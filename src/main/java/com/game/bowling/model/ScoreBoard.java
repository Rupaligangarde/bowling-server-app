package com.game.bowling.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreBoard {

    @JsonProperty("playerName")
    private String playerName;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("rolls")
    private int[] rolls;
}
