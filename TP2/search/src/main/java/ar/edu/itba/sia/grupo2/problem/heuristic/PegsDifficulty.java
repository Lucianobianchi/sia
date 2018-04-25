package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.Coordinate;
import ar.edu.itba.sia.grupo2.problem.InterestingCoordinatesIterator;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;

public class PegsDifficulty implements Heuristic<SenkuBoard> {
    private static final int[][] difficultyTable = new int[][] {
        {0, 0, 4, 1, 4, 0, 0},
        {0, 0, 1, 1, 1, 0, 0},
        {4, 1, 2, 0, 2, 1, 4},
        {1, 1, 0, 1, 0, 1, 1},
        {4, 1, 2, 0, 2, 1, 4},
        {0, 0, 1, 1, 1, 0, 0},
        {0, 0, 4, 1, 4, 0, 0}
    };

    @Override
    public double getValue(final SenkuBoard senkuBoard) {
        int difficulty = 0;
        final InterestingCoordinatesIterator iter = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.PEG);

        while (iter.hasNext()) {
            final Coordinate position = iter.next();
            difficulty += difficultyTable[position.getRow()][position.getColumn()];
        }

        return senkuBoard.getPegCount() * 2 + difficulty;
    }
}
