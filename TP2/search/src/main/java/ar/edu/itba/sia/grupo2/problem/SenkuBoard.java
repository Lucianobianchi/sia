package ar.edu.itba.sia.grupo2.problem;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static ar.edu.itba.sia.grupo2.problem.SenkuContent.EMPTY;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.INVALID;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.PEG;

public class SenkuBoard {
    private final SenkuContent[] board;
    private final RowBoundary[] boundaries;
    private final int dimension;
    private int cellCount;
    private int emptyCellCount;
    private Coordinate target;
    private long id;


    public static boolean areSymmetric(SenkuBoard board, SenkuBoard other) {
        return board.id  == other.id;
    }

    public SenkuBoard(final SenkuContent[][] board) {
        Objects.requireNonNull(board);

        if (!isSquare(board))
            throw new IllegalArgumentException("Board must be square");

        this.dimension = board.length;
        this.board = new SenkuContent[this.dimension * this.dimension];

        for(int i = 0 ; i < board.length ; i++){
            for(int j = 0 ; j < board[i].length ; j++){
                setContent(i, j, board[i][j]);
            }
        }

        this.target = new Coordinate(this.dimension /2, this.dimension /2); // Center

        this.boundaries = calculateBoundaries();
        this.id = calculateId();
    }

    // TODO: ver que hacer cuando no ponemos target
    private SenkuBoard(final SenkuContent[] board, final RowBoundary[] boundaries, final int dimension, final int cellCount, final int emptyCellCount, final Coordinate target, final long id) {
        this.board = board;
        this.boundaries = boundaries;
        this.dimension = dimension;
        this.cellCount = cellCount;
        this.emptyCellCount = emptyCellCount;
        this.target = target;
        this.id = id;
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
        return this.dimension;
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
        return board[row*getDimension() + column];
    }

    public void setContent(final Coordinate coordinate, SenkuContent content) {
        setContent(coordinate.getRow(), coordinate.getColumn(), content);
    }

    private void setContent(final int row, final int column, SenkuContent content) {
        board[row*getDimension() + column] = content;
    }

    public long getId() {
        return id;
    }

    public boolean isValidPosition(final Coordinate coordinate) {
        return isValidPosition(coordinate.getRow(), coordinate.getColumn());
    }

    public boolean isValidPosition(final int row, final int column) {
        final int dim = getDimension();
        return row >= 0 && row < dim && column >= 0 && column < dim;
    }

    public boolean isValidMovement(final Coordinate from, final Coordinate to) {
        final int dim = getDimension();

        if (!isValidPosition(from) || !isValidPosition(to))
            return false;

        final SenkuContent fromContent = getContent(from);
        final SenkuContent toContent = getContent(to);

        if (fromContent != PEG)
            return false;

        if (toContent != EMPTY)
            return false;

        if (Coordinate.manhattanDistance(from, to) != 2)
            return false;

        final Optional<Coordinate> between = Coordinate.between(from, to);

        if (!between.isPresent())
            return false;

        final SenkuContent betweenContent = getContent(between.get());

        if (betweenContent != PEG)
            return false;

        return true;
    }

    public boolean isValidMovement(final SenkuMovement movement) {
        final Coordinate from = movement.getFrom();
        final Coordinate to = movement.getTo();

        return isValidMovement(from, to);
    }

    public SenkuBoard applyMovement(final SenkuMovement movement, boolean mutate) {
        if (!isValidMovement(movement))
            throw new IllegalArgumentException("Invalid movement");

        final Coordinate from = movement.getFrom();
        final Coordinate to = movement.getTo();
        final Coordinate between = Coordinate.between(from, to).get();

        SenkuBoard modifiedBoard = mutate ? this : duplicate();

        modifiedBoard.emptyCellCount++;
        modifiedBoard.setContent(from, EMPTY);
        modifiedBoard.setContent(to, PEG);
        modifiedBoard.setContent(between, EMPTY);
        modifiedBoard.id = modifiedBoard.calculateId();

        return modifiedBoard;
    }


    public SenkuBoard revertMovement(final SenkuMovement movement, boolean mutate){
        if (!isValidReverseMovement(movement))
            throw new IllegalArgumentException("Invalid movement");

        final Coordinate from = movement.getFrom();
        final Coordinate to = movement.getTo();
        final Coordinate between = Coordinate.between(from, to).get();

        SenkuBoard modifiedBoard = mutate ? this : duplicate();

        modifiedBoard.emptyCellCount--;
        modifiedBoard.setContent(from, EMPTY);
        modifiedBoard.setContent(to, PEG);
        modifiedBoard.setContent(between, PEG);
        modifiedBoard.id = modifiedBoard.calculateId();

        return modifiedBoard;
    }

    public boolean isValidReverseMovement(SenkuMovement movement) {
        Coordinate from = movement.getFrom();
        Coordinate to = movement.getTo();

        if (!isValidPosition(from) || !isValidPosition(to))
            return false;

        final SenkuContent fromContent = getContent(from);
        final SenkuContent toContent = getContent(to);

        if (fromContent != PEG) {
            return false;
        }
        if (toContent != EMPTY)
            return false;

        if (Coordinate.manhattanDistance(from, to) != 2) {
            return false;
        }

        final Optional<Coordinate> between = Coordinate.between(from, to);

        if (!between.isPresent())
            return false;

        final SenkuContent betweenContent = getContent(between.get());

        return betweenContent == EMPTY;
    }

    public SenkuBoard duplicate() {
        final SenkuContent[] newBoard = Arrays.copyOf(board, board.length);
        return new SenkuBoard(newBoard, boundaries, dimension, cellCount, emptyCellCount, target, id);
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof SenkuBoard))
            return false;

        final SenkuBoard other = (SenkuBoard) obj;

        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        final int dim = getDimension();

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
                stringBuilder.append(getContent(i,j));
            
            stringBuilder.append('\n');
        }

        // remove last newline
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private boolean isSquare(SenkuContent[][] board) {
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
            final SenkuContent content = getContent(row,j);

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

    private long calculateId() {
        return Arrays.stream(Symmetry.values())
                .mapToLong(this::calculateIdRot)
                .min()
                .getAsLong();
    }

    private long calculateIdRot(Symmetry sym) {
        final int dim = getDimension();
        long id = 0;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                final SenkuContent content = getContent(sym.transform(i, j, dim));
                id = (id << 1) | content.getNum();
            }
        }

        return id;
    }

    public void recalculateId() {
        this.id = calculateId();
    }
}
