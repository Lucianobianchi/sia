package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SenkuProblemTest {
    private SenkuBoard senkuBoard = new SenkuBoard(SenkuBoardParser.parse("testBoards/test1.txt"));
    private Problem<SenkuBoard> senkuProblem = new SenkuProblem(senkuBoard);

    @Test
    public void getInitialStateTest() {
        assertEquals(senkuBoard, senkuProblem.getInitialState());
    }

    @Test
    public void getRulesTest() {
        final Coordinate to = new Coordinate(2, 2);
        final List<Rule<SenkuBoard>> expectedRules = Arrays.asList(
            new SenkuMovement(new Coordinate(0, 2), to),
            new SenkuMovement(new Coordinate(2, 0), to),
            new SenkuMovement(new Coordinate(4, 2), to),
            new SenkuMovement(new Coordinate(2, 4), to)
        );

        final List<Rule<SenkuBoard>> actualRules = senkuProblem.getRules(senkuBoard);

        assertTrue(expectedRules.containsAll(actualRules));
        assertTrue(actualRules.containsAll(expectedRules));
    }
}