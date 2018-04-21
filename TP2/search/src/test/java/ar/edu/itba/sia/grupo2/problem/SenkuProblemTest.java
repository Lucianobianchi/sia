package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SenkuProblemTest {
    @Test
    public void getInitialStateTest() {
        final SenkuBoard senkuBoard = new SenkuBoard(SenkuBoardParser.parse("testBoards/test1.txt"));
        final Problem<SenkuBoard> senkuProblem = new SenkuProblem(senkuBoard);

        assertEquals(senkuBoard, senkuProblem.getInitialState());
    }

    @Test
    public void getRulesFindEmptyTest() {
        final SenkuBoard senkuBoard = new SenkuBoard(SenkuBoardParser.parse("testBoards/test1.txt"));
        final Problem<SenkuBoard> senkuProblem = new SenkuProblem(senkuBoard);

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

    @Test
    public void getRulesFindPegsTest() {
        final SenkuBoard senkuBoard = new SenkuBoard(SenkuBoardParser.parse("testBoards/test2.txt"));
        final Problem<SenkuBoard> senkuProblem = new SenkuProblem(senkuBoard);

        final Coordinate from = new Coordinate(2, 2);
        final List<Rule<SenkuBoard>> expectedRules = Arrays.asList(
                new SenkuMovement(from, new Coordinate(0, 2)),
                new SenkuMovement(from, new Coordinate(2, 0)),
                new SenkuMovement(from, new Coordinate(4, 2)),
                new SenkuMovement(from, new Coordinate(2, 4))
        );

        final List<Rule<SenkuBoard>> actualRules = senkuProblem.getRules(senkuBoard);

        assertTrue(expectedRules.containsAll(actualRules));
        assertTrue(actualRules.containsAll(expectedRules));
    }
}