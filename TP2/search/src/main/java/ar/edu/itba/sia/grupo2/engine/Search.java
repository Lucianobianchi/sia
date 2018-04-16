package ar.edu.itba.sia.grupo2.engine;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class Search<S> {

    abstract protected boolean checkBeforeExpansion();
    abstract protected void addToFrontier(Node<S> node);
    abstract protected Node<S> removeFromFrontier();
    abstract protected boolean isFrontierEmpty();

    public Optional<Node<S>> graphSearch(final Problem<S> problem) {
        return search(problem, true);
    }

    public Optional<Node<S>> treeSearch(final Problem<S> problem) {
        return search(problem, false);
    }

    private Optional<Node<S>> search(final Problem<S> problem, final boolean pruneExpanded) {
        final Set<S> explored = new HashSet<>();
        final Node<S> root = Node.rootNode(problem.getInitialState());

        addToFrontier(root);

        if (problem.isResolved(root.getState()))
            return Optional.of(root);

        while (!isFrontierEmpty()) {
            final Node<S> node = removeFromFrontier();
            final S state = node.getState();

            if (!explored.contains(state)) {
                if (checkBeforeExpansion() && problem.isResolved(state))
                    return Optional.of(node);

                if (pruneExpanded)
                    explored.add(state);

                for (final Rule<S> rule : problem.getRules(state)) {
                    final Node<S> childNode = Node.childNode(node, rule);
                    final S childState = childNode.getState();

                    if (!checkBeforeExpansion() && problem.isResolved(childState))
                        return Optional.of(childNode);

                    if (!explored.contains(childState))
                        addToFrontier(childNode);
                }
            }
        }

        return Optional.empty();
    }
}
