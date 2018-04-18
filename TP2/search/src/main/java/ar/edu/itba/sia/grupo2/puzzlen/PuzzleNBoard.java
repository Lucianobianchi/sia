package ar.edu.itba.sia.grupo2.puzzlen;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class PuzzleNBoard {
    public static final int EMPTY = 0;

    private final int[] board;
    private final int emptyCell;
    private final int dim;

    public PuzzleNBoard(final int[][] board) {
        Objects.requireNonNull(board);

        if (board.length != board[0].length)
            throw new IllegalArgumentException("Board must be square");

        this.dim = board.length;
        this.board = new int[this.dim * this.dim];

        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                final int index = coordinateToIndex(i, j);
                this.board[index] = board[i][j];
            }
        }

        this.emptyCell = findEmptyCell();
    }

    private PuzzleNBoard(final int[] board, final int dim) {
        this.board = Objects.requireNonNull(board);
        this.emptyCell = findEmptyCell();
        this.dim = dim;
    }

    public int getDim() {
        return dim;
    }

    public int[][] getBoard() {
        int[][] board = new int[this.dim][this.dim];

        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++)
                board[i][j] = getElement(i, j);

        return board;
    }

    public int getElement(final int row, final int column) {
        if (!isValidPosition(row, column))
            throw new IllegalStateException("Invalid position");

        final int index = coordinateToIndex(row, column);
        return board[index];
    }

    public PuzzleNBoard applyMovement(final int rows, final int columns) {
        if (!isValidMovement(rows, columns))
            throw new IllegalArgumentException("Invalid movement");

        final int row = emptyCell / this.dim + rows;
        final int column = emptyCell % this.dim + columns;
        final int element = getElement(row, column);
        final int[] newBoard = Arrays.copyOf(board, board.length);

        newBoard[emptyCell] = element;
        newBoard[coordinateToIndex(row, column)] = EMPTY;

        return new PuzzleNBoard(newBoard, dim);
    }

    public boolean isValidMovement(final int rows, final int columns) {
        final int row = emptyCell / this.dim + rows;
        final int column = emptyCell % this.dim + columns;
        return isValidPosition(row, column);
    }

    public boolean isValidPosition(final int row, final int column) {
        return isValidPosition(column) && isValidPosition(row);
    }

    public boolean isEmpty(final int row, final int column) {
        return isEmpty(coordinateToIndex(row, column));
    }

    private boolean isValidPosition(final int position) {
        return position >= 0 && position < this.dim;
    }

    private int findEmptyCell() {
        for (int i = 0; i < board.length; i++)
            if (isEmpty(i))
                return i;

        throw new NoSuchElementException();
    }

    private boolean isEmpty(final int index) {
        return board[index] == EMPTY;
    }

    private int coordinateToIndex(final int row, final int column) {
        return column + row * this.dim;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof PuzzleNBoard))
            return false;

        final PuzzleNBoard other = (PuzzleNBoard) obj;

        return Arrays.equals(board, other.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++)
                stringBuilder.append(getElement(i, j)).append(' ');

            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
