package ar.edu.itba.sia.grupo2.problem;

import java.util.Arrays;
import java.util.Objects;

import static ar.edu.itba.sia.grupo2.problem.SenkuContent.EMPTY;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.INVALID;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.PEG;

// TODO: hashcode, toString & equals
public class SenkuBoard {
    private final SenkuContent[][] board;
    private final RowBoundary[] boundaries;
    private int cellCount;
    private int emptyCellCount;
    private Coordinate target;

    public SenkuBoard(final SenkuContent[][] board) {
        this.board = Objects.requireNonNull(board);

        if (!isSquare())
            throw new IllegalArgumentException("Board must be square");

        this.target = new Coordinate(board.length/2, board[0].length/2); // Center

        this.boundaries = calculateBoundaries();
    }

    private SenkuBoard(final SenkuContent[][] board, final RowBoundary[] boundaries, final int cellCount, final int emptyCellCount, final Coordinate target) {
        this.board = board;
        this.boundaries = boundaries;
        this.cellCount = cellCount;
        this.emptyCellCount = emptyCellCount;
        this.target = target;
    }

    public int getPegCount() {
        return getCellCount() - getEmptyCount();
    }

    public int getCellCount() {
        return cellCount;
    }

    public int getEmptyCount() {
        return emptyCellCount;
    }

    public int getDimension() {
        return board.length;
    }

    public Coordinate getTarget() {
        return target;
    }

    // warning: mutable
    public RowBoundary[] getBoundaries() {
        return boundaries;
    }

    public SenkuContent getContent(final Coordinate coordinate) {
        return getContent(coordinate.getRow(), coordinate.getColumn());
    }

    public SenkuContent getContent(final int row, final int column) {
        return board[row][column];
    }

    public boolean isValidMovement(final SenkuMovement movement) {
        final Coordinate from = movement.getFrom();
        final Coordinate to = movement.getTo();
        final SenkuContent fromContent = getContent(movement.getFrom());
        final SenkuContent toContent = getContent(movement.getTo());

        if (fromContent != PEG)
            return false;

        if (toContent != EMPTY)
            return false;

        if (Coordinate.manhattanDistance(from, to) != 2)
            return false;

        final Coordinate between = Coordinate.between(from, to);
        final SenkuContent betweenContent = getContent(between);

        if (betweenContent != PEG)
            return false;

        return true;
    }

    public SenkuBoard applyMovement(final SenkuMovement movement) {
        if (!isValidMovement(movement))
            throw new IllegalArgumentException("Invalid movement");

        final Coordinate from = movement.getFrom();
        final Coordinate to = movement.getTo();
        final Coordinate between = Coordinate.between(from, to);

        final SenkuContent[][] newBoard = Arrays.copyOf(board, board.length);
        newBoard[from.getRow()][from.getColumn()] = EMPTY;
        newBoard[to.getRow()][to.getColumn()] = PEG;
        newBoard[between.getRow()][to.getColumn()] = EMPTY;

        return new SenkuBoard(newBoard, boundaries, cellCount, emptyCellCount + 1, target);
    }

    private boolean isSquare() {
        return board.length == board[0].length;
    }

    private RowBoundary[] calculateBoundaries() {
        final int dim = getDimension();
        final RowBoundary[] boundaries = new RowBoundary[dim];

        for (int i = 0; i < dim; i++)
            boundaries[i] = calculateRowBoundary(i);

        return boundaries;
    }

    private RowBoundary calculateRowBoundary(final int row) {
        final int dim = getDimension();
        RowBoundary rowBoundary = null;
        int fromBoundary = -1;
        int toBoundary = -1;

        for (int j = 0; j < dim; j++) {
            final SenkuContent content = board[row][j];

            if (content == PEG || content == EMPTY) {
                cellCount += 1;
                emptyCellCount += content == EMPTY ? 1 : 0;

                fromBoundary = fromBoundary == -1 ? j : fromBoundary;

                if (rowBoundary != null)
                    throw new IllegalArgumentException("Row " + row + " contains holes");
            }

            if ((content == INVALID || j == dim - 1) && fromBoundary != -1 && toBoundary == -1) {
                toBoundary = content == INVALID ? j - 1 : j;
                rowBoundary = new RowBoundary(fromBoundary, toBoundary);
            }
        }

        if (rowBoundary == null)
            throw new IllegalArgumentException("Row " + row + " does not contain any empty cell nor peg");

        return rowBoundary;
    }
}
