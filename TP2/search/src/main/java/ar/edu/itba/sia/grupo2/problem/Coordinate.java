package ar.edu.itba.sia.grupo2.problem;

import java.util.Objects;
import java.util.Optional;

public class Coordinate {
    private final int row;
    private final int column;

    public static int manhattanDistance(final Coordinate a, final Coordinate b) {
        final int rowDistance = Math.abs(a.getRow() - b.getRow());
        final int columnDistance = Math.abs(a.getColumn() - b.getColumn());

        return rowDistance + columnDistance;
    }

    public Coordinate(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }


    public static Optional<Coordinate> between(final Coordinate from, final Coordinate to) {
        final int betweenRow = from.getRow() + (to.getRow() - from.getRow()) / 2;
        final int betweenColumn = from.getColumn() + (to.getColumn() - from.getColumn()) / 2;
        final Coordinate between = new Coordinate(betweenRow, betweenColumn);

        return (between.equals(from) || between.equals(to)) ? Optional.empty() : Optional.of(between);
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
