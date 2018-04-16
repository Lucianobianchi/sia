package ar.edu.itba.sia.grupo2.engine;

import java.util.Deque;
import java.util.LinkedList;

public class DFS<S> extends Search<S> {
    private final Deque<Node<S>> stack = new LinkedList<>();

    @Override
    protected boolean checkBeforeExpansion() {
        return true;
    }

    @Override
    protected void addToFrontier(final Node<S> node) {
        stack.push(node);
    }

    @Override
    protected Node<S> removeFromFrontier() {
        return stack.pop();
    }

    @Override
    protected boolean isFrontierEmpty() {
        return stack.isEmpty();
    }

}
