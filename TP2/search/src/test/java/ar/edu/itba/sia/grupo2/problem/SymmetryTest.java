package ar.edu.itba.sia.grupo2.problem;

import org.junit.Test;

import static org.junit.Assert.*;

public class SymmetryTest {
    private Coordinate ZERO_ZERO = new Coordinate(0,0);
    private Coordinate ZERO_ONE = new Coordinate(0,1);
    private Coordinate ONE_ZERO = new Coordinate(1,0);
    private Coordinate ONE_ONE = new Coordinate(1,1);

    @Test
    public void rot90Test(){
        Symmetry s = Symmetry.ROT_90;
        assertEquals(ZERO_ONE, s.transform(ZERO_ZERO,2));
        assertEquals(ONE_ONE, s.transform(ZERO_ONE,2));
        assertEquals(ZERO_ZERO, s.transform(ONE_ZERO,2));
        assertEquals(ONE_ZERO, s.transform(ONE_ONE,2));
    }

    @Test
    public void rot180Test(){
        Symmetry s = Symmetry.ROT_180;
        assertEquals(ONE_ONE, s.transform(ZERO_ZERO,2));
        assertEquals(ONE_ZERO, s.transform(ZERO_ONE,2));
        assertEquals(ZERO_ONE, s.transform(ONE_ZERO,2));
        assertEquals(ZERO_ZERO, s.transform(ONE_ONE,2));
    }


    @Test
    public void rot270Test(){
        Symmetry s = Symmetry.ROT_270;
        assertEquals(ONE_ZERO, s.transform(ZERO_ZERO,2));
        assertEquals(ZERO_ZERO, s.transform(ZERO_ONE,2));
        assertEquals(ONE_ONE, s.transform(ONE_ZERO,2));
        assertEquals(ZERO_ONE, s.transform(ONE_ONE,2));
    }


    @Test
    public void flipHorTest(){
        Symmetry s = Symmetry.FLIP_HOR;
        assertEquals(ZERO_ONE, s.transform(ZERO_ZERO,2));
        assertEquals(ZERO_ZERO, s.transform(ZERO_ONE,2));
        assertEquals(ONE_ONE, s.transform(ONE_ZERO,2));
        assertEquals(ONE_ZERO, s.transform(ONE_ONE,2));
    }



    @Test
    public void flipVerTest(){
        Symmetry s = Symmetry.FLIP_VER;
        assertEquals(ONE_ZERO, s.transform(ZERO_ZERO,2));
        assertEquals(ONE_ONE, s.transform(ZERO_ONE,2));
        assertEquals(ZERO_ZERO, s.transform(ONE_ZERO,2));
        assertEquals(ZERO_ONE, s.transform(ONE_ONE,2));
    }


    @Test
    public void diag1Test(){
        Symmetry s = Symmetry.DIAG_1;
        assertEquals(ZERO_ZERO, s.transform(ZERO_ZERO,2));
        assertEquals(ONE_ZERO, s.transform(ZERO_ONE,2));
        assertEquals(ZERO_ONE, s.transform(ONE_ZERO,2));
        assertEquals(ONE_ONE, s.transform(ONE_ONE,2));
    }


    @Test
    public void diag2Test(){
        Symmetry s = Symmetry.DIAG_2;
        assertEquals(ONE_ONE, s.transform(ZERO_ZERO,2));
        assertEquals(ZERO_ONE, s.transform(ZERO_ONE,2));
        assertEquals(ONE_ZERO, s.transform(ONE_ZERO,2));
        assertEquals(ZERO_ZERO, s.transform(ONE_ONE,2));
    }






}
