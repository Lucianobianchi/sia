package ar.edu.itba.sia.grupo2.problem.heuristic;

import ar.com.itba.sia.Heuristic;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;

public class NumberOfPegs implements Heuristic<SenkuBoard> {

    @Override
    public double getValue(SenkuBoard senkuBoard) {
        return senkuBoard.getPegCount();
    }
}
