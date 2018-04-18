package ar.edu.itba.sia.grupo2.puzzlen;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PuzzleNProblem implements Problem<PuzzleNBoard> {
    private final PuzzleNBoard initialState;

    public PuzzleNProblem(final int[][] initialState) {
        this.initialState = new PuzzleNBoard(Objects.requireNonNull(initialState));
    }

    @Override
    public PuzzleNBoard getInitialState() {
        return initialState;
    }

    @Override
    public List<Rule<PuzzleNBoard>> getRules(final PuzzleNBoard state) {
        final List<Rule<PuzzleNBoard>> actions = new LinkedList<>();

        for (final AdyacentMovement movement : AdyacentMovement.values())
            if (state.isValidMovement(movement.getRows(), movement.getColumns()))
                actions.add(movement);

        return actions;
    }

    @Override
    public boolean isResolved(final PuzzleNBoard state) {
        final int dim = state.getDim();
        int prev = -1;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                final int element = state.getElement(i,j);

                if (element == PuzzleNBoard.EMPTY && (i != dim - 1 || j != dim - 1))
                    return false;
                else if (element != PuzzleNBoard.EMPTY && element <= prev)
                    return false;

                prev = element;
            }
        }

        return true;
    }
}
