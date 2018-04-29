package ar.edu.itba.sia.grupo2.utils;

import java.util.HashMap;
import java.util.Map;

public class EngineStats {
	private final static int LOG_INTERVAL = 5000;
	private final Map<Integer, Integer> levelExpansions;
	private boolean log;

	public EngineStats() {
		log = false;
		levelExpansions = new HashMap<>();
	}

	public void expansion(int level) {
		final int current = levelExpansions.getOrDefault(level, 0) + 1;
		levelExpansions.put(level, current);

		if (log && current % LOG_INTERVAL == 0)
			printStats();
	}

	private void printStats() {
		System.out.println(levelExpansions);
	}

	public void setLogging(final boolean hasToLog) {
		this.log = hasToLog;
	}
}
