package ar.edu.itba.sia.grupo2.engine.uninformed;

import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.Node;

import java.util.Optional;

public class IterativeDFS<S> {
    private final LimitedDFS<S> ldfs = new LimitedDFS<>();

    public Optional<Node<S>> search(final Problem<S> problem) {
        Optional<Node<S>> solution = Optional.empty();

        for (int i = 0; !solution.isPresent(); i++)
            solution = ldfs.search(problem, i);

        return solution;
    }
}
