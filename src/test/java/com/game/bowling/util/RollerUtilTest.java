//package com.game.bowling.util;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class RollerUtilTest {
//    private final Math math = mock(Math.class);
//
//
//    @Test
//    void shouldReturnPointUpToTen() {
//
//        when(math.random()).thenReturn(0.3);
//
//        Integer actual = new RollerUtil(math).points(10);
//        assertEquals(3, actual);
//    }
//
//    @Test
//    void shouldReturnNumberInGivenRange() {
//
//        when(math.random()).thenReturn(0.3).thenReturn(0.5);
//
//        List<Integer> actual = new RollerUtil(math).getFrameScore(10);
//        assertEquals(List.of(3, 4), actual);
//    }
//}