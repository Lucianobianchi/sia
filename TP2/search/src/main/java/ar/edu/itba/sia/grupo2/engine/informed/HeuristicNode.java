package ar.edu.itba.sia.grupo2.engine.informed;

import ar.edu.itba.sia.grupo2.engine.Node;

import java.util.Objects;

class HeuristicNode<S> {
    private final Node<S> node;
    private final double heuristicValue;

    public HeuristicNode(final Node<S> node, final double heuristicValue) {
        this.node = Objects.requireNonNull(node);
        this.heuristicValue = heuristicValue;
    }

    public Node<S> getNode() {
        return node;
    }

    public double getHeuristicValue() {
        return heuristicValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;

        if (!(o instanceof HeuristicNode))
            return false;

        final HeuristicNode<?> other = (HeuristicNode<?>) o;

        return Double.compare(other.getHeuristicValue(), getHeuristicValue()) == 0 &&
                Objects.equals(getNode(), other.getNode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNode(), getHeuristicValue());
    }

    @Override
    public String toString() {
        return getNode().toString();
    }
}
