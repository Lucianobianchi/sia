package ar.edu.itba.sia.grupo2.generator;

import ar.edu.itba.sia.grupo2.problem.Coordinate;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;

import java.util.Optional;

public class ReverseMove {
    private final Coordinate from;
    private final Coordinate to;
    private final Coordinate between;

    public ReverseMove(Coordinate from, Coordinate to) {
        System.out.println(from);
        System.out.println(to);
        this.from = from;
        this.to = to;
        Optional<Coordinate> bet = Coordinate.between(from, to);

        if (!bet.isPresent()) {
            throw new IllegalArgumentException();
        }

        between = bet.get();

    }

    public void apply(SenkuBoard board){
        board.setContent(from, SenkuContent.EMPTY);
        board.setContent(between, SenkuContent.PEG);
        board.setContent(to, SenkuContent.PEG);
        board.recalculateId();
    }
}
