package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Rule;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SenkuMultipleProblem extends SenkuProblem {

    public SenkuMultipleProblem(final SenkuBoard initialState) {
        super(initialState);
    }


    @NotNull
    @Override
    public List<Rule<SenkuBoard>> getRules(final SenkuBoard senkuBoard) {
        List<Rule<SenkuBoard>> rules = new ArrayList<>();

        InterestingCoordinatesIterator iterator = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.PEG);

        while(iterator.hasNext()){
            Coordinate peg = iterator.next();
            Set<List<Coordinate>> moveSet = getMovementsFrom(senkuBoard, peg);
            for(List<Coordinate> move : moveSet){
                if(move.size() > 1) {
                    rules.add(new SenkuMultipleMovement(move));
                }
            }
        }

        return rules;
    }

    private Set<List<Coordinate>> getMovementsFrom(final SenkuBoard senkuBoard, final Coordinate originPeg) {
        Set<List<Coordinate>> moves = new HashSet<>();

        List<Coordinate> possibleMove = new ArrayList<>();
        possibleMove.add(originPeg);
        moves.add(possibleMove);

        final int row  = originPeg.getRow();
        final int col = originPeg.getColumn();

        for (int delta = -2; delta <= 2; delta += 4) {
            final Coordinate vertical = new Coordinate(row + delta, col);
            final Coordinate horizontal = new Coordinate(row, col + delta);

            if (senkuBoard.isValidMovement(originPeg, horizontal)){
                addMoves(senkuBoard, originPeg, horizontal, moves);
            }

            if (senkuBoard.isValidMovement(originPeg, vertical)){
                addMoves(senkuBoard, originPeg, vertical, moves);
            }

        }

        return moves;
    }

    private void addMoves(final SenkuBoard board, final Coordinate originPeg, final Coordinate nextPosition, Set<List<Coordinate>> moves) {
        SenkuBoard modifiedBoard = board.applyMovement(new SenkuMovement(originPeg, nextPosition));
        Set<List<Coordinate>> additional = getMovementsFrom(modifiedBoard, nextPosition);

        for(List<Coordinate> nextMoves : additional){
            List<Coordinate> possibleMove = new ArrayList<>();
            possibleMove.add(originPeg);
            possibleMove.addAll(nextMoves);
            moves.add(possibleMove);
        }
    }


}
