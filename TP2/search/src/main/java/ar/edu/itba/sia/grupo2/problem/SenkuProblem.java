package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Problem;
import ar.com.itba.sia.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.sia.grupo2.problem.SenkuContent.EMPTY;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.INVALID;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.PEG;

public class SenkuProblem implements Problem<SenkuBoard> {
    private static final SenkuBoard DEFAULT_BOARD = new SenkuBoard(new SenkuContent[][] {
            {INVALID, INVALID, PEG, PEG, PEG, INVALID, INVALID},
            {INVALID, INVALID, PEG, PEG, PEG, INVALID, INVALID},
            {PEG, PEG, PEG, PEG, PEG, PEG, PEG},
            {PEG, PEG, PEG, EMPTY, PEG, PEG, PEG},
            {PEG, PEG, PEG, PEG, PEG, PEG, PEG},
            {INVALID, INVALID, PEG, PEG, PEG, INVALID, INVALID},
            {INVALID, INVALID, PEG, PEG, PEG, INVALID, INVALID}
    });

    private final SenkuBoard initialState;

    public SenkuProblem(final SenkuBoard initialState) {
        this.initialState = initialState;
    }

    public SenkuProblem() {
        this(DEFAULT_BOARD);
    }

    @Override
    public SenkuBoard getInitialState() {
        return initialState;
    }

    @Override
    public boolean isResolved(final SenkuBoard senkuBoard) {
        final boolean singlePegLeft = senkuBoard.getPegCount() == 1;
        final boolean pegAtTarget = senkuBoard.getContent(senkuBoard.getTarget()) == PEG;

        return singlePegLeft && pegAtTarget;
    }

    @NotNull
    @Override
    public List<Rule<SenkuBoard>> getRules(final SenkuBoard senkuBoard) {
        final List<Rule<SenkuBoard>> rules = new ArrayList<>(); // TODO: initial capacity o sino usar LinkedList
        final int pegCount = senkuBoard.getPegCount();
        final int emptyCount = senkuBoard.getEmptyCount();

        final SenkuContent contentToConsider = pegCount > emptyCount ? EMPTY : PEG;

        InterestingCoordinatesIterator iterator = new InterestingCoordinatesIterator(senkuBoard, contentToConsider);

        while(iterator.hasNext()){
            addRulesAtCell(senkuBoard, iterator.next(), contentToConsider, rules);
        }

        return rules;
    }

    private void addRulesAtCell(final SenkuBoard senkuBoard, final Coordinate coordinate, final SenkuContent content, final List<Rule<SenkuBoard>> rules) {
        final int row = coordinate.getRow();
        final int col = coordinate.getColumn();

        for (int delta = -2; delta <= 2; delta += 4) {
            final Coordinate vertical = new Coordinate(row + delta, col);
            final Coordinate horizontal = new Coordinate(row, col + delta);

            if (content == EMPTY) {
                tryAddRule(senkuBoard, vertical, coordinate, rules);
                tryAddRule(senkuBoard, horizontal, coordinate, rules);
            }
            else {
                tryAddRule(senkuBoard, coordinate, vertical, rules);
                tryAddRule(senkuBoard, coordinate, horizontal, rules);
            }
        }
    }

    private void tryAddRule(final SenkuBoard senkuBoard, final Coordinate from, final Coordinate to, final List<Rule<SenkuBoard>> rules) {
        if (senkuBoard.isValidMovement(from, to))
            rules.add(new SenkuMovement(from, to));
    }
}
