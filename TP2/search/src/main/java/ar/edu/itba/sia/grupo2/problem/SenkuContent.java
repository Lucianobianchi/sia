package ar.edu.itba.sia.grupo2.problem;

public enum SenkuContent {
    INVALID("X", 0),
    EMPTY("_", 0),
    PEG("O", 1);

    private String c;
    private long num;

    private SenkuContent(final String c, final long num) {
        this.c = c;
        this.num = num;
    }

    @Override
    public String toString() {
        return c;
    }

    public long getNum() {
        return num;
    }
}
