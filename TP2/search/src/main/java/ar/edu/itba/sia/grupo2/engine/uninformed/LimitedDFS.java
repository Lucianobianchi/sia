package ar.edu.itba.sia.grupo2.engine.uninformed;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.utils.Cronometer;
import ar.edu.itba.sia.grupo2.utils.EngineStats;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class LimitedDFS<S> {
    private EngineStats stats = new EngineStats();
    private final Cronometer cronometer = new Cronometer();

    public Optional<Node<S>> search(final Problem<S> problem, final int limit) {
        final Node<S> root = Node.rootNode(problem.getInitialState());

        cronometer.start();
        final Node<S> solution = search(problem, root, limit, new HashSet<S>());
        cronometer.stop();

        stats.setTimeElapsed(cronometer.getTimeElapsed() + stats.getTimeElapsed());

        return Optional.ofNullable(solution);
    }

    private Node<S> search(final Problem<S> problem, final Node<S> node, final int limit, final Set<S> exploredBranch) {
        final S state = node.getState();

        if (exploredBranch.contains(state))
            return null;

        if (problem.isResolved(state))
            return node;

        if (limit == 0)
            return null;

        exploredBranch.add(state);

        stats.expansion(node);

        for (final Rule<S> rule : problem.getRules(state)) {
            final Node<S> childNode = Node.childNode(node, rule);
            final Node<S> solution = search(problem, childNode, limit - 1, exploredBranch);

            if (solution != null)
                return solution;
        }

        exploredBranch.remove(state);

        return null;
    }

    public EngineStats getStats() {
        return stats;
    }
}
