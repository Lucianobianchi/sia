package ar.edu.itba.sia.grupo2.main;

import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.BFS;
import ar.edu.itba.sia.grupo2.engine.Search;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuBoardLoader;
import ar.edu.itba.sia.grupo2.problem.SenkuProblem;
import ar.edu.itba.sia.grupo2.problem.heuristic.MeanDistanceToTarget;

public class GenericMain {
    public static void main(String[] args) {
        final Problem<SenkuBoard> problem = new SenkuProblem(SenkuBoardLoader.load("boards/board2.txt"));
        final Search<SenkuBoard> search = new AStar<>(new MeanDistanceToTarget());

        System.out.println(search.graphSearch(problem));
    }
}
