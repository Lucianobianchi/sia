package ar.edu.itba.sia.grupo2.utils;

public class Cronometer {
    private long startTime;
    private long stopTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public long stop() {
        stopTime = System.currentTimeMillis();
        return getTimeElapsed();
    }

    public long getTimeElapsed() {
        return stopTime - startTime;
    }
}
