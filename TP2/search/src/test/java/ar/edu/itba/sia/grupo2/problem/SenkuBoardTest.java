package ar.edu.itba.sia.grupo2.problem;

import org.junit.Test;

import static org.junit.Assert.*;

public class SenkuBoardTest {
    private SenkuBoard senkuBoard = new SenkuBoard(SenkuBoardParser.parse("testBoards/test1.txt"));

    private RowBoundary[] expectedBoundaries = new RowBoundary[]{
        new RowBoundary(1, 1),
        new RowBoundary(0, 2),
        new RowBoundary(1, 1)
    };

    @Test
    public void cellCountTest() {
        assertEquals(5, senkuBoard.getCellCount());
    }

    @Test
    public void emptyCellCountTest() {
        assertEquals(1, senkuBoard.getEmptyCount());
    }

    @Test
    public void pegCountTest() {
        assertEquals(4, senkuBoard.getPegCount());
    }

    @Test
    public void dimensionTest() {
        assertEquals(3, senkuBoard.getDimension());
    }

    @Test
    public void boundariesTest() {
        assertArrayEquals(expectedBoundaries, senkuBoard.getBoundaries());
    }

    @Test
    public void targetTest(){ assertEquals(new Coordinate(1,1), senkuBoard.getTarget()); }
    // TODO: isValidMovement, applyMovement, equals
}
