package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.edu.itba.sia.grupo2.problem.Coordinate;
import ar.edu.itba.sia.grupo2.problem.InterestingCoordinatesIterator;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;

import java.util.HashMap;
import java.util.Map;

public class EnglishBoardPagoda {

    private static final int DIMENSION = 7;




    private static final int[][] MAP2 =
                    {{-2, -2, -1, 0, -1, -2, -2},
                    {-2, -2, 1, 1, 1, -2, -2},
                    {-1, 1, 0, 1, 0, 1, -1},
                    {0, 1, 1, 0, 1, 1, 0},
                    {-1, 1, 0, 1, 0, 1, -1},
                    {-2, -2, 1, 1, 1, -2, -2},
                    {-2, -2, -1, 0, -1, -2, -2}};

    private static final double[][] MAP =
                    {{-2, -2, -0.3, 0.4, 0, -2, -2},
                    {-2, -2, 1, 0, 1, -2, -2},
                    {0.5, 0, 0.5, 0.4, 0.1, 0.3, -0.1},
                    {0, 0.9, 0.7, 0.3, 0.9, 1.1, 0.4},
                    {0.5, 0.6, 0.1, 0.5, 0.2, 0.6, 0.2},
                    {-2, -2, 0.8, 0, 0.8, -2, -2},
                    {-2, -2, 0, 0.5, -0.2, -2, -2}};


    public static boolean inDomain(Coordinate coordinate){
        return coordinate.getRow() > 0 && coordinate.getColumn() > 0
                && coordinate.getRow() < DIMENSION - 1
                && coordinate.getColumn() < DIMENSION - 1
                && MAP[coordinate.getRow()][coordinate.getColumn()] > -1.5;
    }

    public static double pagoda(SenkuBoard board){
        InterestingCoordinatesIterator iterator = new InterestingCoordinatesIterator(board, SenkuContent.PEG);
        double pagoda = 0;

        while(iterator.hasNext()){
            pagoda += eval(iterator.next());
        }

        return pagoda;
    }

    public static double eval(Coordinate coordinate){
        return MAP2[coordinate.getRow()][coordinate.getColumn()];
    }
}
