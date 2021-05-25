package com.game.bowling;

import com.game.bowling.model.BowlingRequest;
import com.game.bowling.util.JsonUtil;
import com.game.bowling.util.RollerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BowlingGameApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private RollerUtil rollerUtil;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldTestBowlingGame() throws IOException {

        when(rollerUtil.getFrameScore(any())).thenReturn(List.of(1, 6)).thenReturn(List.of(5, 0));

        BowlingRequest bowlingRequest = JsonUtil.readJson("/bowlingRequest.json", BowlingRequest.class);

        ResponseEntity<String> startGameResponse =
                testRestTemplate
                        .exchange(
                                "/bowling/start",
                                HttpMethod.POST,
                                new HttpEntity<>(bowlingRequest),
                                String.class
                        );

        assertEquals(CREATED, startGameResponse.getStatusCode());

        String gameId = startGameResponse.getBody();

        ResponseEntity<String> rollResponse =
                testRestTemplate
                        .exchange(
                                "/bowling/v2/" + gameId + "/roll",
                                HttpMethod.POST,
                                null,
                                String.class
                        );

        assertEquals(CREATED, rollResponse.getStatusCode());

        ResponseEntity<String> scoreBoardResponse =
                testRestTemplate
                        .exchange(
                                "/bowling/" + gameId + "/score",
                                HttpMethod.GET,
                                null,
                                String.class
                        );

        assertEquals(OK, scoreBoardResponse.getStatusCode());
        assertEquals(
                "[{\"playerName\":\"test-player2\",\"score\":7,\"rolls\":[1,6]},{\"playerName\":\"test-player1\",\"score\":5,\"rolls\":[5,0]}]",
                scoreBoardResponse.getBody()
        );

        ResponseEntity<String> playerScoreResponse =
                testRestTemplate
                        .exchange(
                                "/bowling/" + gameId + "/score/test-player1",
                                HttpMethod.GET,
                                null,
                                String.class
                        );

        assertEquals(OK, playerScoreResponse.getStatusCode());
        assertEquals("{\"playerName\":\"test-player1\",\"score\":5,\"rolls\":[5,0]}", playerScoreResponse.getBody());
    }
}
