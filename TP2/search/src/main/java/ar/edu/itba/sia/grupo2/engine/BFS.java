package ar.edu.itba.sia.grupo2.engine;

import java.util.LinkedList;
import java.util.Queue;

public class BFS<S> extends Search<S> {
    private final Queue<Node<S>> queue = new LinkedList<>();

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
