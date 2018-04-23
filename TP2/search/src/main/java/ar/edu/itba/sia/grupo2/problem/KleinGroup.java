package ar.edu.itba.sia.grupo2.problem;

public enum KleinGroup {


    ZERO(0), X(1), Y(2), Z(3);

    private int index;

    KleinGroup(int index){
        this.index = index;
    }

    public static KleinGroup fromPosition(int row, int col, int dim){
        int position = row * (dim-1) + col;
        int ordinal = position % 3 + 1;
        return KleinGroup.values()[ordinal];
    }

    public int getIndex(){
        return index;
    }

    KleinGroup add(KleinGroup k){
        return adder[getIndex()][k.getIndex()];
    }

    private static KleinGroup[][] adder =
            {{ZERO, X, Y, Z},
             {X, ZERO, Z, Y},
             {Y, Z, ZERO, X},
             {Z, Y, X, ZERO}};


}
