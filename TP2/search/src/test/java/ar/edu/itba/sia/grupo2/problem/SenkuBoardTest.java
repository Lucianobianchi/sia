package ar.edu.itba.sia.grupo2.problem;

import org.junit.Test;

import static ar.edu.itba.sia.grupo2.problem.SenkuContent.EMPTY;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.INVALID;
import static ar.edu.itba.sia.grupo2.problem.SenkuContent.PEG;
import static org.junit.Assert.*;

public class SenkuBoardTest {
    private SenkuBoard senkuBoard = SenkuBoardLoader.load("testBoards/test1.txt");

    private String expectedString =
            "XOOOX\n" +
            "XOOOX\n" +
            "OO_OO\n" +
            "XOOOX\n" +
            "XOOOX";

    private int expectedCellCount = 17;
    private int expectedEmptyCellCount = 1;
    private int expectedPegCount = 16;
    private int expectedDimension = 5;
    private Coordinate expectedTarget = new Coordinate(2, 2);

    private RowBoundary[] expectedBoundaries = new RowBoundary[] {
        new RowBoundary(1, 3),
        new RowBoundary(1, 3),
        new RowBoundary(0, 4),
        new RowBoundary(1, 3),
        new RowBoundary(1, 3),
    };

    @Test
    public void cellCountTest() {
        assertEquals(expectedCellCount, senkuBoard.getCellCount());
    }

    @Test
    public void emptyCellCountTest() {
        assertEquals(expectedEmptyCellCount, senkuBoard.getEmptyCount());
    }

    @Test
    public void pegCountTest() {
        assertEquals(expectedPegCount, senkuBoard.getPegCount());
    }

    @Test
    public void dimensionTest() {
        assertEquals(expectedDimension, senkuBoard.getDimension());
    }

    @Test
    public void boundariesTest() {
        assertArrayEquals(expectedBoundaries, senkuBoard.getBoundaries());
    }

    @Test
    public void isValidMovementTest() {
        final SenkuMovement movement = new SenkuMovement(new Coordinate(0, 2), new Coordinate(2, 2));
        assertTrue(senkuBoard.isValidMovement(movement));

        final SenkuMovement[] invalidMovements = new SenkuMovement[] {
            new SenkuMovement(new Coordinate(1, 1), new Coordinate(2, 2)),
            new SenkuMovement(new Coordinate(1, 1), new Coordinate(3, 3)),
            new SenkuMovement(new Coordinate(0, 0), new Coordinate(0, 1)),
            new SenkuMovement(new Coordinate(1, 1), new Coordinate(1, 3)),
            new SenkuMovement(new Coordinate(2, 2), new Coordinate(0, 2)),
            new SenkuMovement(new Coordinate(1, 0), new Coordinate(3, 0))
        };

        for (final SenkuMovement m : invalidMovements)
            assertFalse(senkuBoard.isValidMovement(m));
    }

    @Test
    public void applyMovementTest() {
        final SenkuMovement movement = new SenkuMovement(new Coordinate(0, 2), new Coordinate(2, 2));
        final SenkuBoard expectedSenkuBoard = new SenkuBoard(new SenkuContent[][] {
            {INVALID, PEG, EMPTY, PEG, INVALID},
            {INVALID, PEG, EMPTY, PEG, INVALID},
            {PEG, PEG, PEG, PEG, PEG},
            {INVALID, PEG, PEG, PEG, INVALID},
            {INVALID, PEG, PEG, PEG, INVALID}
        });

        final SenkuBoard actualSenkuBoard = senkuBoard.applyMovement(movement, false);

        System.out.println(actualSenkuBoard.getId());
        System.out.println(expectedSenkuBoard.getId());

        assertEquals(expectedSenkuBoard, actualSenkuBoard);
        assertNotEquals(senkuBoard, actualSenkuBoard); // Inmutability
    }

    @Test(expected = IllegalArgumentException.class)
    public void applyMovementThrow() {
        final SenkuMovement invalidMovement = new SenkuMovement(new Coordinate(0, 1), new Coordinate(2, 2));
        senkuBoard.applyMovement(invalidMovement, false);
    }

    @Test
    public void targetTest() {
        assertEquals(expectedTarget, senkuBoard.getTarget());
    }

    @Test
    public void equalsTest() {
        final SenkuBoard expectedSenkuBoard = new SenkuBoard(new SenkuContent[][] {
            {INVALID, PEG, PEG, PEG, INVALID},
            {INVALID, PEG, PEG, PEG, INVALID},
            {PEG, PEG, EMPTY, PEG, PEG},
            {INVALID, PEG, PEG, PEG, INVALID},
            {INVALID, PEG, PEG, PEG, INVALID}
        });

        assertEquals(expectedSenkuBoard, senkuBoard);
        assertEquals(expectedSenkuBoard.hashCode(), senkuBoard.hashCode());
    }


    @Test
    public void symmetryTest(){
        SenkuBoard sym1 = SenkuBoardLoader.load("testBoards/sym1.txt");
        SenkuBoard sym2 = SenkuBoardLoader.load("testBoards/sym2.txt");
        SenkuBoard sym3 = SenkuBoardLoader.load("testBoards/sym3.txt");
        SenkuBoard sym4 = SenkuBoardLoader.load("testBoards/sym4.txt");
        SenkuBoard sym5 = SenkuBoardLoader.load("testBoards/sym5.txt");

        assert(SenkuBoard.areSymmetric(sym1, sym2));
        assert(SenkuBoard.areSymmetric(sym2, sym1));
        assert(SenkuBoard.areSymmetric(sym2, sym3));
        assert(SenkuBoard.areSymmetric(sym1, sym3));
        assert(SenkuBoard.areSymmetric(sym3, sym4));
        assert(SenkuBoard.areSymmetric(sym1, sym4));
        assert(SenkuBoard.areSymmetric(sym2, sym4));
        assert(SenkuBoard.areSymmetric(sym4, sym1));
        assert(SenkuBoard.areSymmetric(sym1, sym5));
        assert(SenkuBoard.areSymmetric(sym4, sym5));


    }

    @Test
    public void notSymmetricTest(){
        SenkuBoard sym1 = SenkuBoardLoader.load("testBoards/sym1.txt");
        SenkuBoard notsym = SenkuBoardLoader.load("testBoards/notsym.txt");

        assertFalse(SenkuBoard.areSymmetric(sym1, notsym));

    }

    @Test
    public void toStringTest() {
        assertEquals(expectedString, senkuBoard.toString());
    }
}
