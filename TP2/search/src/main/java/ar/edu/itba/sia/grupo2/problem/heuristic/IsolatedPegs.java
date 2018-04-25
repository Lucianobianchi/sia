package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.Coordinate;
import ar.edu.itba.sia.grupo2.problem.InterestingCoordinatesIterator;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;

public class IsolatedPegs implements Heuristic<SenkuBoard> {
    @Override
    public double getValue(final SenkuBoard senkuBoard) {
        int isolatedPegCount = 0;
        final InterestingCoordinatesIterator iter = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.PEG);

        while (iter.hasNext()) {
            final Coordinate position = iter.next();
            if (isIsolated(senkuBoard, position))
                isolatedPegCount++;
        }

        return senkuBoard.getPegCount() * 2 + isolatedPegCount;
    }

    private boolean isIsolated(final SenkuBoard senkuBoard, final Coordinate position) {
        final int row = position.getRow();
        final int column = position.getColumn();

        for (int i = -1; i <= 1; i += 2) {
            final Coordinate vertical = new Coordinate(row + i, column);
            final Coordinate horizontal = new Coordinate(row, column + i);

            if (senkuBoard.isValidPosition(vertical) && senkuBoard.getContent(vertical) != SenkuContent.EMPTY)
                return false;

            if (senkuBoard.isValidPosition(horizontal) && senkuBoard.getContent(horizontal) != SenkuContent.EMPTY)
                return false;
        }

        return true;
    }
}
