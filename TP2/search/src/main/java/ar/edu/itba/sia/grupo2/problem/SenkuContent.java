package ar.edu.itba.sia.grupo2.problem;

public enum SenkuContent {
    INVALID("X"),
    EMPTY("_"),
    PEG("O");

    private String c;

    private SenkuContent(final String c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return c;
    }
}
