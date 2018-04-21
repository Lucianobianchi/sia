package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.Coordinate;
import ar.edu.itba.sia.grupo2.problem.RowBoundary;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;

public class MeanDistanceToTarget implements Heuristic<SenkuBoard> {

    @Override
    public double getValue(final SenkuBoard senkuBoard) {
        final Coordinate target = senkuBoard.getTarget();
        final RowBoundary[] boardBoundaries = senkuBoard.getBoundaries();

        int cumulativeDistance = 0;
        int remainingPegs = senkuBoard.getPegCount();
        int  row = 0;
        for(RowBoundary boundary: boardBoundaries){
            int from = boundary.getFrom();
            int to = boundary.getTo();
            for(int col = from; col <= to ; col++){
                if(senkuBoard.getContent(row, col) == SenkuContent.PEG){
                    cumulativeDistance += Coordinate.manhattanDistance(target, new Coordinate(row,col));
                    remainingPegs--;
                    if(remainingPegs == 0){ // Early return por eficiencia
                        return cumulativeDistance / senkuBoard.getPegCount(); // Promedio
                    }
                }
            }
            row++;
        }

        return cumulativeDistance / senkuBoard.getPegCount(); // Promedio
    }
}
