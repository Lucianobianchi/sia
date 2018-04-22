package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SenkuMultipleMovement implements Rule<SenkuBoard> {

    private final List<Coordinate> path;

    public SenkuMultipleMovement(final List<Coordinate> path) {
        if(path.size() < 2){
            throw new IllegalArgumentException();
        }
        this.path = new ArrayList<>(path);
    }




    @Override
    public double getCost() {
        return 1;
    }

    @Override
    public void setCost(double v) {

    }

    @Override
    public SenkuBoard applyToState(SenkuBoard senkuBoard) {
        for(int i = 0; i < path.size() - 1; i++){
            SenkuMovement movement = new SenkuMovement(path.get(i), path.get(i+1));
            senkuBoard = senkuBoard.applyMovement(movement);
        }
        return senkuBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SenkuMultipleMovement))
            return false;

        SenkuMultipleMovement other = (SenkuMultipleMovement) o;
        return Objects.equals(path, other.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
