package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SenkuMultipleProblemTest {

    @Test
    public void findRules(){
        final SenkuBoard senkuBoard = SenkuBoardLoader.load("testBoards/test5.txt");
        final Problem<SenkuBoard> senkuProblem = new SenkuMultipleProblem(senkuBoard);


        final Rule<SenkuBoard> rule0 = new SenkuMultipleMovement(Arrays.asList(new Coordinate(4,3),
                                                                               new Coordinate(2,3)));


        final Rule<SenkuBoard> rule1 = new SenkuMultipleMovement(Arrays.asList(new Coordinate(4,3),
                                                                                new Coordinate(2,3),
                                                                                new Coordinate(2,5)));

        final Rule<SenkuBoard> rule2 = new SenkuMultipleMovement(Arrays.asList(new Coordinate(4,3),
                                                                                new Coordinate(2,3),
                                                                                new Coordinate(2,1)));

        final Rule<SenkuBoard> rule3 = new SenkuMultipleMovement(Arrays.asList(new Coordinate(3,3),
                                                                                new Coordinate(5,3)));



        final List<Rule<SenkuBoard>> expectedRules = Arrays.asList(rule0, rule1, rule2, rule3);
        final List<Rule<SenkuBoard>> actualRules = senkuProblem.getRules(senkuBoard);

        assertTrue(expectedRules.containsAll(actualRules));
        assertTrue(actualRules.containsAll(expectedRules));

    }

}
