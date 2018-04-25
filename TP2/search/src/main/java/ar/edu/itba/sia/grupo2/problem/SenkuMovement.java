package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Rule;

import java.util.Objects;

public class SenkuMovement implements Rule<SenkuBoard> {
    private final Coordinate from;
    private final Coordinate to;

    public SenkuMovement(final Coordinate from, final Coordinate to) {
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
    }

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    @Override
    public double getCost() {
        return 1.0;
    }

    @Override
    public void setCost(double v) {
        throw new UnsupportedOperationException("Senku movement set cost not implemented");
    }

    @Override
    public SenkuBoard applyToState(final SenkuBoard senkuBoard) {
        return senkuBoard.applyMovement(this, false);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SenkuMovement))
            return false;

        final SenkuMovement other = (SenkuMovement) o;

        return getFrom().equals(other.getFrom()) && getTo().equals(other.getTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrom(), getTo());
    }

    @Override
    public String toString() {
        return getFrom() + " -> " + getTo();
    }
}
