package ar.edu.itba.sia.grupo2.engine.informed;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar<S> extends Search<S> {
    private final Queue<HeuristicNode<S>> queue;
    private final Heuristic<S> heuristic;

    public AStar(final Heuristic<S> heuristic) {
        this.heuristic = Objects.requireNonNull(heuristic);
        this.queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.getNode().getCost() + n.getHeuristicValue()));
    }

    @Override
    protected boolean checkBeforeExpansion() {
        return false;
    }

    @Override
    protected void addToFrontier(final Node<S> node) {
        final HeuristicNode<S> hNode = new HeuristicNode<>(node, heuristic.getValue(node.getState()));
        queue.add(hNode);
    }

    @Override
    protected Node<S> removeFromFrontier() {
        return queue.remove().getNode();
    }

    @Override
    protected boolean isFrontierEmpty() {
        return queue.isEmpty();
    }
}
