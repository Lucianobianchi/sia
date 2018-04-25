package ar.edu.itba.sia.grupo2.problem;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class InterestingCoordinatesIterator implements Iterator<Coordinate> {

    private int lastRow, lastCol;
    private final RowBoundary[] boundaries;
    private final SenkuBoard board;
    private int remaining;

    private final SenkuContent interest;

    public InterestingCoordinatesIterator(final SenkuBoard board, final SenkuContent interest){
        if(interest != SenkuContent.PEG && interest != SenkuContent.EMPTY){
            throw new IllegalArgumentException();
        }

        this.boundaries = board.getBoundaries();
        this.board = board;
        this.lastRow = 0;
        this.lastCol = boundaries[0].getFrom() - 1;
        this.remaining = interest == SenkuContent.PEG ? board.getPegCount() : board.getEmptyCount();
        this.interest = interest;
    }

    @Override
    public boolean hasNext() {
        return remaining > 0;
    }

    @Override
    public Coordinate next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }

        for(int i = lastRow ; i < boundaries.length ; i++){
            int startCol = (i == lastRow ? lastCol + 1 : boundaries[i].getFrom());
            for(int j = startCol ; j <= boundaries[i].getTo() ; j++){
                if(board.getContent(i,j) == this.interest){
                    this.lastRow = i;
                    this.lastCol = j;
                    this.remaining--;
                    return new Coordinate(i,j);
                }
            }

        }

        System.out.println(board);

        throw new RuntimeException(); // Unreachable
    }


}
