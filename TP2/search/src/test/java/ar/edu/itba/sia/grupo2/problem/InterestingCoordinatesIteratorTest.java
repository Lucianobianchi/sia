package ar.edu.itba.sia.grupo2.problem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InterestingCoordinatesIteratorTest {

    private SenkuBoard senkuBoard;
    private InterestingCoordinatesIterator iterator;




    @Test
    public void testPegIterator(){
        senkuBoard = SenkuBoardLoader.load("testBoards/test3.txt");
        iterator = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.PEG);

        assert(iterator.hasNext());
        assertEquals(new Coordinate(0,1), iterator.next());
        assertEquals(new Coordinate(2,1), iterator.next());
        assertEquals(new Coordinate(2,3), iterator.next());
        assertEquals(new Coordinate(3,2), iterator.next());
        assertEquals(new Coordinate(4,3), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testEmptyIterator(){
        senkuBoard = SenkuBoardLoader.load("testBoards/test4.txt");
        iterator = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.EMPTY);

        assert(iterator.hasNext());
        assertEquals(new Coordinate(0,2), iterator.next());
        assertEquals(new Coordinate(1,2), iterator.next());
        assertEquals(new Coordinate(1,3), iterator.next());
        assertEquals(new Coordinate(2,2), iterator.next());
        assertEquals(new Coordinate(4,3), iterator.next());

        assertFalse(iterator.hasNext());
    }


}
