package ar.edu.itba.sia.grupo2.puzzlen;

import ar.com.itba.sia.Rule;

public enum AdyacentMovement implements Rule<PuzzleNBoard> {
    UP (1,0),
    RIGHT (0,1),
    DOWN (-1,0),
    LEFT (0,-1);

    private final int rows;
    private final int columns;

    private AdyacentMovement(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public double getCost() {
        return 1;
    }

    @Override
    public void setCost(double v) { }

    @Override
    public PuzzleNBoard applyToState(final PuzzleNBoard puzzleNBoard) {
        return puzzleNBoard.applyMovement(rows, columns);
    }
}
