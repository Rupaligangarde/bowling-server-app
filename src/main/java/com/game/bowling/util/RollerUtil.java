package com.game.bowling.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RollerUtil {


    public Integer points(Integer maxPoint) {
        return (int) (Math.random() * (maxPoint + 1));
    }

    public List<Integer> getFrameScore(Integer maxPoint) {
        Integer r1 = points(maxPoint);
        if (r1.equals(maxPoint)) {
            return List.of(r1);
        } else {
            Integer r2 = points(maxPoint - r1);
            return List.of(r1, r2);
        }
    }
}
