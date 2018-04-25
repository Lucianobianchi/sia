package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.*;

import java.util.Random;

public class MeanDistanceAllPegs implements Heuristic<SenkuBoard> {

    private static Random random = new Random();
    private long pruned = 0;
    private long iterations = 0;
    @Override
    public double getValue(final SenkuBoard senkuBoard) {
        iterations++;
        if(EnglishBoardPagoda.pagoda(senkuBoard) < EnglishBoardPagoda.eval(senkuBoard.getTarget())){
            pruned++;
            if(random.nextDouble() < 0.0005){
                System.out.printf("%d : %d\n", iterations, pruned);
            }
            return Double.MAX_VALUE; // Infinity

        }

        double cumulativeDistance = 0;
        final int pegCount = senkuBoard.getPegCount();
        int remainingPegs = pegCount;
        final int numberOfPairs = pegCount*(pegCount-1)/2;

        InterestingCoordinatesIterator iterator = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.PEG);

        while(iterator.hasNext()){
            Coordinate hit = iterator.next();
            cumulativeDistance += getDistanceFromLowerPegs(senkuBoard, hit, remainingPegs);
            remainingPegs--;
        }

        return cumulativeDistance / numberOfPairs;

    }


    private int getDistanceFromLowerPegs(final SenkuBoard senkuBoard, final Coordinate from, int remainingPegs) {
        RowBoundary[] boardBoundaries = senkuBoard.getBoundaries();
        int cumulative = 0;
        for(int row = from.getRow() ; row < boardBoundaries.length ; row++) {

            // Traverse the first row partially (beginning from the 'from' column) and other rows from the start
            int columnLowerLimit = row == from.getRow() ? from.getColumn() : boardBoundaries[row].getFrom();
            int columnUpperLimit = boardBoundaries[row].getTo();

            for (int col = columnLowerLimit; col <= columnUpperLimit; col++) {
                if (senkuBoard.getContent(row, col) == SenkuContent.PEG) {
                    cumulative += Coordinate.manhattanDistance(from, new Coordinate(row, col));

                    remainingPegs--;
                    if(remainingPegs == 0){
                        return cumulative;
                    }
                }
            }
        }

        return cumulative;
    }
}
