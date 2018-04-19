package ar.edu.itba.sia.grupo2.problem;

import java.util.Objects;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(final int row, final int column) {
        if (row < 0 || column < 0)
            throw new IllegalArgumentException("Coordinates components must be non negative");

        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int manchesterDistance(final Coordinate other) {
        final int rowDistance = Math.abs(other.getRow() - getRow());
        final int columnDistance = Math.abs(other.getColumn() - getColumn());

        return rowDistance + columnDistance;
    }

    public Coordinate between(final Coordinate other) {
        final int betweenRow = getRow() + (other.getRow() - getRow()) / 2;
        final int betweenColumn = getColumn() + (other.getColumn() - getColumn()) / 2;

        return new Coordinate(betweenRow, betweenColumn);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Coordinate))
            return false;

        final Coordinate other = (Coordinate) o;

        return getRow() == other.getRow() && getColumn() == other.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }

    @Override
    public String toString() {
        return "[" + row + ", " + column + "]";
    }
}
