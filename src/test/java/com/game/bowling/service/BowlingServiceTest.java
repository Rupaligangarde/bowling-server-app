package com.game.bowling.service;

import com.game.bowling.bean.IdGenerator;
import com.game.bowling.config.BowlingConfig;
import com.game.bowling.model.BowlingRequest;
import com.game.bowling.model.ScoreBoard;
import com.game.bowling.util.RollerUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BowlingServiceTest {

    private final IdGenerator idGenerator = mock(IdGenerator.class);
    private final BowlingConfig bowlingConfig = mock(BowlingConfig.class);
    private final RollerUtil rollerUtil = mock(RollerUtil.class);
    private final BowlingService bowlingService = new BowlingService(idGenerator, bowlingConfig, rollerUtil);
    private final String gameId = "89780d64-681a-46c3-a2c";
    private final String playerName = "test-player1";
    private final BowlingRequest bowlingRequest = new BowlingRequest(Set.of(playerName, "test-player2", "test-player3"));

    @BeforeEach
    void setUp() {
        when(bowlingConfig.getMaxPlayers()).thenReturn(3);
        when(idGenerator.generate()).thenReturn(gameId);
    }

    @Test
    void shouldThrowExceptionIfPlayersAreMoreThanMaxPlayerCountPerLane() {

        when(bowlingConfig.getMaxPlayers()).thenReturn(2);
        BowlingService bowlingService = new BowlingService(idGenerator, bowlingConfig, rollerUtil);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> bowlingService.startGame(bowlingRequest));

        assertEquals("400 BAD_REQUEST \"Max number of players allowed are 2\"", ex.getLocalizedMessage());
    }

    @Test
    void shouldReturnGameIdPostStart() {

        String actual = bowlingService.startGame(bowlingRequest);
        assertEquals(gameId, actual);
    }


    @Test
    void shouldReturnScoreBoardForGivenPlayerNameAndGameId() {

        bowlingService.startGame(bowlingRequest);
        ScoreBoard actual = bowlingService.scoreBoard(gameId, playerName);

        assertEquals(playerName, actual.getPlayerName());
        assertEquals(0, actual.getScore());
    }

    @Test
    void shouldThrowExceptionIfGameIdIsUnknown() {

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> bowlingService.scoreBoard("some-game-id", playerName));
        assertEquals(
                "400 BAD_REQUEST \"No record found for given gameId some-game-id and player name test-player1\"",
                ex.getLocalizedMessage()
        );
    }

    @Test
    void shouldThrowExceptionIfPlayerNameIsUnknown() {

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> bowlingService.scoreBoard(gameId, "some-other-player-name"));
        assertEquals(
                "400 BAD_REQUEST \"No record found for given gameId 89780d64-681a-46c3-a2c and player name some-other-player-name\"",
                ex.getLocalizedMessage()
        );
    }
}