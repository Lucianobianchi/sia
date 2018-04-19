package ar.edu.itba.sia.grupo2.problem;

import java.util.Objects;

public class RowBoundary {
    private final int from;
    private final int to;

    public RowBoundary(final int from, final int to) {
        if (from < 0 || to < 0)
            throw new IllegalArgumentException("Row boundaries must be non negative");

        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;

        if (!(o instanceof RowBoundary))
            return false;

        final RowBoundary other = (RowBoundary) o;

        return getFrom() == other.getFrom() && getTo() == other.getTo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo());
    }

    @Override
    public String toString() {
        return "[" + from + ", " + to + "]";
    }
}
