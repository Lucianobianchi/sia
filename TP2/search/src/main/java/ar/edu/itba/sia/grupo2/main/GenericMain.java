package ar.edu.itba.sia.grupo2.main;

import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuBoardLoader;
import ar.edu.itba.sia.grupo2.problem.SenkuProblem;
import ar.edu.itba.sia.grupo2.problem.heuristic.MeanDistanceAllPegs;

import java.util.Optional;

public class GenericMain {
    public static void main(String[] args) {
        final Problem<SenkuBoard> problem = new SenkuProblem(SenkuBoardLoader.load("boards/easy.txt"));
        final Search<SenkuBoard> search = new AStar<>(new MeanDistanceAllPegs());

        final Optional<Node<SenkuBoard>> result = search.graphSearch(problem);
        System.out.println(result);
    }
}
