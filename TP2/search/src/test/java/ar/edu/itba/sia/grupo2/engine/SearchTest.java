package ar.edu.itba.sia.grupo2.engine;

import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.puzzlen.ManhattanDistance;
import ar.edu.itba.sia.grupo2.puzzlen.PuzzleNProblem;
import ar.edu.itba.sia.grupo2.puzzlen.PuzzleNBoard;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class SearchTest {
    private final double expectedCost = 22;
    private final int[][] board = new int[][] {
        {5, 4, PuzzleNBoard.EMPTY},
        {6, 1, 8},
        {7, 3, 2}
    };

    private final Problem<PuzzleNBoard> problem = new PuzzleNProblem(board);

    @Test(timeout = 250)
    public void aStarTest() {
        final AStar<PuzzleNBoard> algorithm = new AStar<>(ManhattanDistance.getInstance());

        assertOptimalGraphSearch(algorithm);
    }

    @Test(timeout = 600)
    public void bfsTest() {
        final BFS<PuzzleNBoard> algorithm = new BFS<>();

        assertOptimalGraphSearch(algorithm);
    }

    @Test(timeout = 600)
    public void uniformCostTest() {
        final UniformCost<PuzzleNBoard> algorithm = new UniformCost<>();

        assertOptimalGraphSearch(algorithm);
    }

    @Test(timeout = 1000)
    public void dfsTest() {
        final DFS<PuzzleNBoard> algorithm = new DFS<>();

        assertGraphSearch(algorithm);
    }

    @Test(timeout = 1500)
    public void idfsTest() {
        final IterativeDFS<PuzzleNBoard> algorithm = new IterativeDFS<>();

        assertOptimalResult(algorithm.search(problem));
    }

    @Test
    public void noResultTest() {
        final int[][] unsolvableBoard = new int[][] {{1,PuzzleNBoard.EMPTY,3},{2,4,5},{6,7,8}};
        final Problem<PuzzleNBoard> unsolvableProblem = new PuzzleNProblem(unsolvableBoard);
        final AStar<PuzzleNBoard> algorithm = new AStar<>(ManhattanDistance.getInstance());
        final Optional<Node<PuzzleNBoard>> result = algorithm.graphSearch(unsolvableProblem);

        assertFalse(result.isPresent());
    }

    private void assertOptimalGraphSearch(final Search<PuzzleNBoard> algorithm) {
        final Optional<Node<PuzzleNBoard>> result = algorithm.graphSearch(problem);
        assertOptimalResult(result);
    }

    private void assertOptimalResult(final Optional<Node<PuzzleNBoard>> result) {
        assertResult(result);
        assertEquals(expectedCost, result.get().getCost(), 0.01);
    }

    private void assertGraphSearch(final Search<PuzzleNBoard> algorithm) {
        final Optional<Node<PuzzleNBoard>> result = algorithm.graphSearch(problem);
        assertResult(result);
    }

    private void assertResult(final Optional<Node<PuzzleNBoard>> result) {
        assertTrue(result.isPresent());

        final Node<PuzzleNBoard> resultNode = result.get();

        assertTrue(problem.isResolved(resultNode.getState()));
    }
}