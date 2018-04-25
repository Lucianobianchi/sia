package ar.edu.itba.sia.grupo2.generator;

import ar.edu.itba.sia.grupo2.problem.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBoardGenerator {

    private static final int SEED = 1233;
    private static Random random = new Random(SEED);

    public static SenkuBoard generate(int pegs){
        SenkuBoard board = SenkuBoardLoader.load("boards/empty.txt");

        for (int i = 0 ; i < pegs ; i++) {
            List<SenkuMovement> movements = getRules(board);
            if(movements.size() == 0){
                return null;
            }
            SenkuMovement move = movements.get(random.nextInt(movements.size()));
            board.revertMovement(move, true);

        }

        return board;
    }


    private static List<SenkuMovement> getRules(final SenkuBoard senkuBoard) {
        final List<SenkuMovement> rules = new ArrayList<>();

        InterestingCoordinatesIterator iterator = new InterestingCoordinatesIterator(senkuBoard, SenkuContent.PEG);

        while(iterator.hasNext()){
            addRulesAtCell(senkuBoard, iterator.next(), rules);
        }

        return rules;
    }

    private static void addRulesAtCell(final SenkuBoard senkuBoard, final Coordinate coordinate, final List<SenkuMovement> rules) {
        final int row = coordinate.getRow();
        final int col = coordinate.getColumn();

        for (int delta = -2; delta <= 2; delta += 4) {
            final Coordinate vertical = new Coordinate(row + delta, col);
            final Coordinate horizontal = new Coordinate(row, col + delta);

             tryAddRule(senkuBoard, coordinate, vertical, rules);
             tryAddRule(senkuBoard, coordinate, horizontal, rules);

        }
    }

    private static void tryAddRule(final SenkuBoard senkuBoard, final Coordinate from, final Coordinate to, final List<SenkuMovement> rules) {
        SenkuMovement move = new SenkuMovement(from, to);
        if (senkuBoard.isValidReverseMovement(move)) {
            rules.add(move);
        }
    }


}
