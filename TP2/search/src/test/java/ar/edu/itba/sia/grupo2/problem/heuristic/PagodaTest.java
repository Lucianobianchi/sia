package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.edu.itba.sia.grupo2.problem.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class PagodaTest {

    private static final double EPSILON = 0.00000001;

    @Test
    public void actualPagodaFunction(){
        SenkuBoard emptyBoard = SenkuBoardLoader.load("testBoards/empty.txt");
        int dimension = emptyBoard.getDimension();
        InterestingCoordinatesIterator allPositions = new InterestingCoordinatesIterator(emptyBoard, SenkuContent.EMPTY);
        while (allPositions.hasNext()) {
            Coordinate curr = allPositions.next();
            for (int delta = -1; delta <= 1 ; delta += 2){
                Coordinate neighborHor = new Coordinate(curr.getRow(), curr.getColumn() + delta);
                Coordinate nextHor = new Coordinate(curr.getRow(), curr.getColumn() + 2*delta);

                if (EnglishBoardPagoda.inDomain(neighborHor) && EnglishBoardPagoda.inDomain(nextHor)){
                    assertGreaterThan(EnglishBoardPagoda.eval(curr) + EnglishBoardPagoda.eval(neighborHor), EnglishBoardPagoda.eval(nextHor));
                }

                Coordinate neighborVert = new Coordinate(curr.getRow() + delta, curr.getColumn());
                Coordinate nextVert = new Coordinate(curr.getRow() + 2*delta, curr.getColumn());


                if (EnglishBoardPagoda.inDomain(neighborVert) && EnglishBoardPagoda.inDomain(nextVert)){
                    assertGreaterThan(EnglishBoardPagoda.eval(curr) + EnglishBoardPagoda.eval(neighborVert), EnglishBoardPagoda.eval(nextVert));

                }

            }
        }

    }

    private void assertGreaterThan(double x, double y) {
        assert(x >= y || Math.abs(x-y) < EPSILON);
    }
}
