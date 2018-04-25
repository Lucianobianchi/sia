package ar.edu.itba.sia.grupo2.main;

import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.DFS;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.generator.RandomBoardGenerator;
import ar.edu.itba.sia.grupo2.problem.*;
import ar.edu.itba.sia.grupo2.problem.heuristic.*;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuBoardLoader;
import ar.edu.itba.sia.grupo2.problem.SenkuMultipleProblem;
import ar.edu.itba.sia.grupo2.problem.heuristic.MeanDistanceAllPegs;
import ar.edu.itba.sia.grupo2.problem.heuristic.PegsDifficulty;

import java.util.Optional;

public class GenericMain {
    public static void main(String[] args) {

        long time = System.currentTimeMillis();

        final Problem<SenkuBoard> problem = new SenkuProblem(SenkuBoardLoader.load("boards/board4.txt"));

//        final Search<SenkuBoard> search = new AStar<>(new MeanDistanceAllPegs());
        final Search<SenkuBoard> search = new DFS<>();
        //final Search<SenkuBoard> search = new BFS<>();
        final Optional<Node<SenkuBoard>> node = search.graphSearch(problem);

        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time);
        System.out.println(node);
    }
}
