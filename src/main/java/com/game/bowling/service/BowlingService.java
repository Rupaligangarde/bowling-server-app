package com.game.bowling.service;

import com.game.bowling.bean.IdGenerator;
import com.game.bowling.config.BowlingConfig;
import com.game.bowling.model.*;
import com.game.bowling.util.RollerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BowlingService {

    private final IdGenerator idGenerator;
    private final BowlingConfig config;
    private final RollerUtil rollerUtil;
    private final Map<String, Map<String, BowlingPlayer>> database = new HashMap<>();

    public BowlingService(@Autowired IdGenerator idGenerator, @Autowired BowlingConfig config, @Autowired RollerUtil rollerUtil) {
        this.idGenerator = idGenerator;
        this.config = config;
        this.rollerUtil = rollerUtil;
    }

    public String startGame(BowlingRequest request) {
        validatePlayerCount(request);
        String gameId = idGenerator.generate();
        database.put(gameId, request.toBowlingPlayers());
        return gameId;
    }

    public ScoreBoard scoreBoard(String gameId, String playerName) {
        try {
            return database.get(gameId).get(playerName).toScoreBoard();
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No record found for given gameId " + gameId + " and player name " + playerName);
        }
    }


    public List<ScoreBoard> scoreBoard(String gameId) {
        return database.get(gameId)
                .values()
                .stream()
                .map(BowlingPlayer::toScoreBoard)
                .collect(Collectors.toList());
    }

    public void roll(String gameId, List<BowlingRollRequest> request) {
        request.forEach(req -> database.get(gameId).get(req.getPlayerName()).roll(req.getRolls()));
    }

    public void roll(String gameId) {
        database
                .get(gameId)
                .values()
                .forEach(player -> {
                    player.roll(rollerUtil.getFrameScore(config.getMaxPoints()));
                    player.incrementFrame();
                    if (player.isLastFrame()) {
                        if (player.isLastStrike()) {
                            Integer r1 = rollerUtil.points(config.getMaxPoints());
                            player.roll(List.of(r1));
                            player.roll(r1.equals(config.getMaxPoints())
                                    ? rollerUtil.points(config.getMaxPoints())
                                    : rollerUtil.points(config.getMaxPoints() - r1));
                            return;
                        }
                        if (player.isLastSpare()) {
                            player.roll(rollerUtil.points(config.getMaxPoints()));
                        }
                    }
                });
    }

    private void validatePlayerCount(BowlingRequest request) {
        if (request.getPlayers().size() > config.getMaxPlayers())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max number of players allowed are " + config.getMaxPlayers());
    }
}
