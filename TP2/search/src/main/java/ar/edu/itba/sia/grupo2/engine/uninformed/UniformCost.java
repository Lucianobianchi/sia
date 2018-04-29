package ar.edu.itba.sia.grupo2.engine.uninformed;

import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class UniformCost<S> extends Search<S> {
    private final Queue<Node<S>> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::getCost));

    @Override
    protected boolean checkBeforeExpansion() {
        return false;
    }

    @Override
    protected void addToFrontier(final Node<S> node) {
        queue.add(node);
    }

    @Override
    protected Node<S> removeFromFrontier() {
        return queue.remove();
    }

    @Override
    protected boolean isFrontierEmpty() {
        return queue.isEmpty();
    }
}
