package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.Coordinate;
import ar.edu.itba.sia.grupo2.problem.RowBoundary;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;

public class MeanDistanceToTarget implements Heuristic<SenkuBoard> {

    private double getDistance(Coordinate a, Coordinate b){
        return Math.abs(a.getColumn() - b.getColumn()) + Math.abs(a.getRow() - b.getRow()); // Manhattan distance
    }

    @Override
    public double getValue(SenkuBoard senkuBoard) {
        Coordinate target = senkuBoard.getTarget();
        RowBoundary[] boardBoundaries = senkuBoard.getBoundaries();

        int cumulativeDistance = 0;
        int  row = 0;
        for(RowBoundary boundary: boardBoundaries){
            int from = boundary.getFrom();
            int to = boundary.getTo();
            for(int col = from; col <= to ; col++){
                if(senkuBoard.getContent(row, col) == SenkuContent.PEG){
                    cumulativeDistance += getDistance(target, new Coordinate(row,col));
                }
            }
            row++;
        }

        return cumulativeDistance / senkuBoard.getPegCount(); // Promedio
    }
}
