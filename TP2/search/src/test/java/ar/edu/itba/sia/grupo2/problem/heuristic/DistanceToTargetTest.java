package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;
import org.junit.Test;

import static ar.edu.itba.sia.grupo2.problem.SenkuContent.EMPTY;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.INVALID;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.PEG;
import static org.junit.Assert.assertEquals;

public class DistanceToTargetTest {
    private static final double EPSILON = 0.001;

    private SenkuBoard senkuBoard = new SenkuBoard(new SenkuContent[][] {
            {INVALID, PEG, PEG, PEG, INVALID},
            {INVALID, EMPTY, EMPTY, EMPTY, INVALID},
            {PEG, PEG, EMPTY, EMPTY, EMPTY},
            {INVALID, EMPTY, PEG, EMPTY, INVALID},
            {INVALID, EMPTY, EMPTY, EMPTY, INVALID},
    });

    private DistanceToTarget heuristic = new DistanceToTarget();


    @Test
    public void correctDistance(){
        assertEquals(2, heuristic.getValue(senkuBoard), EPSILON);
    }

}
