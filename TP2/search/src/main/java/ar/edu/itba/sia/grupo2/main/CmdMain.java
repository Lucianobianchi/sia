package ar.edu.itba.sia.grupo2.main;

import ar.com.itba.sia.Heuristic;
import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.engine.informed.Greedy;
import ar.edu.itba.sia.grupo2.engine.uninformed.BFS;
import ar.edu.itba.sia.grupo2.engine.uninformed.DFS;
import ar.edu.itba.sia.grupo2.engine.uninformed.IterativeDFS;
import ar.edu.itba.sia.grupo2.engine.uninformed.UniformCost;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuBoardLoader;
import ar.edu.itba.sia.grupo2.problem.SenkuProblem;
import ar.edu.itba.sia.grupo2.problem.heuristic.*;
import ar.edu.itba.sia.grupo2.utils.EngineStats;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CmdMain {
    private static final Map<String, Search<SenkuBoard>> strToUninformed = new HashMap<>();
    private static final Map<String, Heuristic<SenkuBoard>> strToHeuristic = new HashMap<>();

    static {
        strToUninformed.put("dfs", new DFS<>());
        strToUninformed.put("bfs", new BFS<>());
        strToUninformed.put("uniform", new UniformCost<>());

        strToHeuristic.put("ha", new NumberOfPegs());
        strToHeuristic.put("hb", new IsolatedPegs());
        strToHeuristic.put("hc", new PegsDifficulty());
        strToHeuristic.put("hd", new DistanceAllPegs());
        strToHeuristic.put("he", new DistanceToTarget());
    }

    public static void main(final String[] args) {
        final Problem<SenkuBoard> problem = new SenkuProblem(SenkuBoardLoader.load("boards/board4.txt"));
        Search<SenkuBoard> search = null;
        IterativeDFS<SenkuBoard> idfs = null;

        if (args.length < 1) {
            System.out.println("Missing algorithm argument");
            System.exit(1);
        }

        final String algorithm = args[0].toLowerCase();

        if (strToUninformed.containsKey(algorithm)) {
            search = strToUninformed.get(algorithm);
        }
        else if (algorithm.equals("idfs")) {
            idfs = new IterativeDFS<>();
        }
        else if (algorithm.equals("a*")) {
            search = new AStar<>(extractHeuristic(args));
        }
        else if (algorithm.equals(("greedy"))) {
            search = new Greedy<>(extractHeuristic(args));
        }
        else {
            System.out.println("No such algorithm " + args[0]);
            System.exit(2);
        }

        System.out.println("Running problem with " + args[0] + " algorithm");

        final Optional<Node<SenkuBoard>> node = search != null ? search.graphSearch(problem) : idfs.search(problem);

        printStats(search != null ? search.getStats() : idfs.getStats());
        node.ifPresent(CmdMain::printSolution);
    }

    private static Heuristic<SenkuBoard> extractHeuristic(String[] args) {
        if (args.length < 2) {
            System.out.println("Missing heuristic argument");
            System.exit(3);
        }

        final String heuristic = args[1].toLowerCase();

        if (!strToHeuristic.containsKey(heuristic)) {
            System.out.println("No such heuristic " + heuristic);
            System.exit(4);
        }

        return strToHeuristic.get(heuristic);
    }

    private static void printStats(final EngineStats stats) {
        System.out.println("Time elapsed: " + stats.getTimeElapsed());
        System.out.println("Expansions: " + stats.getLevelExpansions());
        System.out.println("Total expansions: " + stats.getTotalExpansions());
        System.out.println("Frontier: " + stats.getLevelFrontier());
        System.out.println("Total frontier: " + stats.getTotalFrontier());
        System.out.println("Generated: " + stats.getLevelGenerated());
        System.out.println("Total generated: " + stats.getTotalGenerated());
    }

    private static void printSolution(final Node<?> node) {
        System.out.println("Solution level: " + node.getLevel());
        System.out.println(node);
    }
}
