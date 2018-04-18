package ar.edu.itba.sia.grupo2.puzzlen;

import java.util.function.ToIntFunction;

public class ManhattanDistance implements ToIntFunction<PuzzleNBoard> {
    private static ManhattanDistance ourInstance = new ManhattanDistance();

    public static ManhattanDistance getInstance() {
        return ourInstance;
    }

    private ManhattanDistance() { }

    @Override
    public int applyAsInt(final PuzzleNBoard board) {
        final int dim = board.getDim();
        int distance = 0;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int goalRow;
                int goalCol;
                final int element = board.getElement(i, j);

                if (element == PuzzleNBoard.EMPTY) {
                    goalRow = dim - 1;
                    goalCol = dim - 1;
                }
                else {
                    goalRow = (element - 1) / dim;
                    goalCol = (element - 1) % dim;
                }

                distance += manhattanDistance(i, j, goalRow, goalCol);
            }
        }

        return distance;
    }

    private int manhattanDistance(final int row1, final int col1, final int row2, final int col2) {
        final int rowDistance = row1 - row2;
        final int colDistance = col1 - col2;

        return Math.abs(rowDistance) + Math.abs(colDistance);
    }
}
