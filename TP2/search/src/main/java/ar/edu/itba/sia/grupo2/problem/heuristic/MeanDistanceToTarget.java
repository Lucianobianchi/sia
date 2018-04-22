package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.*;

public class MeanDistanceToTarget implements Heuristic<SenkuBoard> {

    @Override
    public double getValue(final SenkuBoard senkuBoard) {
        final Coordinate target = senkuBoard.getTarget();

        InterestingCoordinatesIterator iterator = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.PEG);

        int cumulativeDistance = 0;

        while(iterator.hasNext()){
            cumulativeDistance += Coordinate.manhattanDistance(target, iterator.next());
        }

        return cumulativeDistance / senkuBoard.getPegCount(); // Promedio
    }
}
