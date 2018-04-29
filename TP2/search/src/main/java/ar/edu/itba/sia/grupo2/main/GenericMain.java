package ar.edu.itba.sia.grupo2.main;

import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.problem.*;
import ar.edu.itba.sia.grupo2.problem.heuristic.*;
import ar.edu.itba.sia.grupo2.utils.EngineStats;

import java.util.Optional;

public class GenericMain {
    public static void main(String[] args) {
        final Problem<SenkuBoard> problem = new SenkuProblem(SenkuBoardLoader.load("boards/board4.txt"));

        final Search<SenkuBoard> search = new AStar<>(new IsolatedPegs());
        //final Search<SenkuBoard> search = new DFS<>();
        //final Search<SenkuBoard> search = new BFS<>();
        final Optional<Node<SenkuBoard>> node = search.graphSearch(problem);

        final EngineStats stats = search.getStats();

        System.out.println("Time elapsed: " + stats.getTimeElapsed());
        System.out.println("Expansions: " + stats.getLevelExpansions());
        System.out.println("Total expansions: " + stats.getTotalExpansions());
        System.out.println("Frontier: " + stats.getLevelFrontier());
        System.out.println("Total frontier: " + stats.getTotalFrontier());
        System.out.println("Generated: " + stats.getLevelGenerated());
        System.out.println("Total generated: " + stats.getTotalGenerated());
        node.ifPresent(n -> System.out.println("Solution level: " + n.getLevel()));
        System.out.println(node);
    }
}
