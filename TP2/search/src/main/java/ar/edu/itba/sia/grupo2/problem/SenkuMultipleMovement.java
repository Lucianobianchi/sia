package ar.edu.itba.sia.grupo2.problem;

import ar.com.itba.sia.Rule;

import java.util.ArrayList;
import java.util.Collections;
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
        SenkuBoard duplicate = senkuBoard.duplicate();
        for(int i = 0; i < path.size() - 1; i++){
            SenkuMovement movement = new SenkuMovement(path.get(i), path.get(i+1));
            duplicate.applyMovement(movement, true);
        }
        return duplicate;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(path.get(0).toString());
        for(int i = 1; i < path.size() ; i++){
            sb.append(" -> ");
            sb.append(path.get(i));
        }
        return sb.toString();
    }

    public List<Coordinate> getPath () {
        return Collections.unmodifiableList(path);
    }
}
