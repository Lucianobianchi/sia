package ar.edu.itba.sia.grupo2.utils;

import ar.edu.itba.sia.grupo2.engine.Node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EngineStats {
    private final static int LOG_INTERVAL = 5000;
    private final Map<Integer, Integer> levelExpansions;
    private final Map<Integer, Integer> levelFrontier;
    private long timeElapsed;
    private boolean log;

    public EngineStats() {
        log = false;
        levelExpansions = new HashMap<>();
        levelFrontier = new HashMap<>();
        timeElapsed = 0;
    }

    public void reset() {
        levelExpansions.clear();
        timeElapsed = 0;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(final long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public int getTotalExpansions() {
        return levelExpansions.values()
                .stream()
                .mapToInt(i -> i)
                .sum();
    }

    public int getTotalFrontier() {
        return levelFrontier.values()
                .stream()
                .mapToInt(i -> i)
                .sum();
    }

    public int getTotalGenerated() {
        return getTotalExpansions() + getTotalFrontier();
    }

    public Map<Integer, Integer> getLevelExpansions() {
        return Collections.unmodifiableMap(levelExpansions);
    }

    public Map<Integer, Integer> getLevelFrontier() {
        return Collections.unmodifiableMap(levelFrontier);
    }

    public Map<Integer, Integer> getLevelGenerated() {
        final Map<Integer, Integer> levelGenerated = new HashMap<>(levelFrontier);
        levelExpansions.forEach((k, v) -> levelGenerated.put(k, levelGenerated.get(k) + v));
        return levelGenerated;
    }

    public void expansion(final Node<?> node) {
        final int level = node.getLevel();
        final int current = levelExpansions.getOrDefault(level, 0) + 1;
        levelExpansions.put(level, current);

        if (log && current % LOG_INTERVAL == 0)
            printStats();
    }

    public void frontier(final Node<?> node) {
        final int level = node.getLevel();
        final int current = levelFrontier.getOrDefault(level, 0) + 1;
        levelFrontier.put(level, current);
    }

    public void removeFrontier(final Node<?> node) {
        final int level = node.getLevel();
        levelFrontier.put(level, levelFrontier.get(level) - 1);
    }

    private void printStats() {
        System.out.println(levelExpansions);
    }

    public void setLogging(final boolean hasToLog) {
        this.log = hasToLog;
    }
}
