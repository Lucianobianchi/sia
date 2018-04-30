package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.*;

public class DistanceToTarget implements Heuristic<SenkuBoard> {


    @Override
    public double getValue(final SenkuBoard senkuBoard) {
        final Coordinate target = senkuBoard.getTarget();

        if(EnglishBoardPagoda.pagoda(senkuBoard) < EnglishBoardPagoda.eval(target)){
            return Double.MAX_VALUE; // Infinity

        }

        InterestingCoordinatesIterator iterator = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.PEG);

        int cumulativeDistance = 0;

        while(iterator.hasNext()){
            cumulativeDistance += Coordinate.manhattanDistance(target, iterator.next());
        }

        return 2*cumulativeDistance;
    }
}
