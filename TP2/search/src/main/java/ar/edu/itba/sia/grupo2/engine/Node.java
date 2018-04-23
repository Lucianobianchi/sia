package ar.edu.itba.sia.grupo2.engine;

import ar.com.itba.sia.Rule;

import java.util.Objects;

public class Node<S> {
    private final S state;
    private final Node<S> parent;
    private final Rule<S> rule;
    private final double cost;

    public static <S> Node<S> rootNode(final S state) {
        return new Node<>(state, null, null, 0);
    }

    public static <S> Node<S> childNode(final Node<S> parent, final Rule<S> rule) {
        final S state = rule.applyToState(parent.getState());
        final double stepCost = rule.getCost();

        return new Node<>(state, parent, rule, stepCost + parent.getCost());
    }

    private Node(final S state, final Node<S> parent, final Rule<S> rule, final double cost) {
        if ((Objects.isNull(parent) && Objects.nonNull(rule)) || Objects.isNull(rule) && Objects.nonNull(parent))
            throw new IllegalStateException("Parent node and rule must both be null or non null simultaneously");

        this.state = Objects.requireNonNull(state);
        this.parent = parent;
        this.rule = rule;
        this.cost = cost;
    }

    public S getState() {
        return state;
    }

    public Node<S> getParent() {
        return Objects.requireNonNull(parent);
    }

    public Rule<S> getRule() {
        return Objects.requireNonNull(rule);
    }

    public boolean hasParent() {
        return parent != null;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Node))
            return false;

        final Node<?> other = (Node) obj;

        if (hasParent() != other.hasParent())
            return false;

        if (!hasParent())
            return getState().equals(other.getState()) && getCost() == other.getCost();

        return getState().equals(other.getState()) && getRule().equals(other.getRule())
                && getCost() == other.getCost() && getParent().equals(other.getParent());
    }

    @Override
    public int hashCode() {
        return hasParent() ? Objects.hash(state, parent, rule, cost) : Objects.hash(state, cost);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        Node<S> node = this;

        while (node.hasParent()) {
            stringBuilder.insert(0, '\n');
            stringBuilder.insert(0, node.getState());
            stringBuilder.insert(0, '\n');
            node = node.getParent();
        }

        stringBuilder.insert(0, '\n');
        stringBuilder.insert(0, node.getState());

        return stringBuilder.toString();
    }
}
