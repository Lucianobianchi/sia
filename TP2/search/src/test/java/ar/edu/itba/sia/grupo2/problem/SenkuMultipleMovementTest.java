package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Rule;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SenkuMultipleMovementTest {

    private static final double EPSILON = 0.0001;
    @Test
    public void ruleApplyTest(){
        SenkuBoard senkuBoardPre = SenkuBoardLoader.load("testBoards/test5.txt");
        SenkuBoard senkuBoardPost = SenkuBoardLoader.load("testBoards/test6.txt");

        final Rule<SenkuBoard> rule = new SenkuMultipleMovement(Arrays.asList(new Coordinate(4,3),
                new Coordinate(2,3),
                new Coordinate(2,5)));


        assertEquals(1, rule.getCost(), EPSILON);

        SenkuBoard appliedBoard = rule.applyToState(senkuBoardPre);

        assertEquals(senkuBoardPost, appliedBoard);

    }
}
