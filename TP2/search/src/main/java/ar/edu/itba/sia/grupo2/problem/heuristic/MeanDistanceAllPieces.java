package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.Coordinate;
import ar.edu.itba.sia.grupo2.problem.RowBoundary;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;

public class MeanDistanceAllPieces implements Heuristic<SenkuBoard> {

    @Override
    public double getValue(final SenkuBoard senkuBoard) {
        RowBoundary[] boardBoundaries = senkuBoard.getBoundaries();

        double cumulativeDistance = 0;
        int checkedPegs = 0;
        int pegCount = senkuBoard.getPegCount();
        int numberOfPairs = pegCount*(pegCount-1)/2;
        for(int row = 0 ; row < boardBoundaries.length ; row++){
            int from = boardBoundaries[row].getFrom();
            int to = boardBoundaries[row].getTo();
            for(int col = from; col <= to ; col++){
                if(senkuBoard.getContent(row, col) == SenkuContent.PEG){
                    cumulativeDistance += getDistanceFromLowerPegs(senkuBoard, new Coordinate(row, col), pegCount - checkedPegs);
                    checkedPegs++;
                    if(checkedPegs == senkuBoard.getPegCount()){
                        return cumulativeDistance / numberOfPairs; // Early return por eficiencia
                    }
                }
            }
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
