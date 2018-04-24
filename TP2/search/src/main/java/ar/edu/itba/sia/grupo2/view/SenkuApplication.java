package ar.edu.itba.sia.grupo2.view;

import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.problem.*;
import ar.edu.itba.sia.grupo2.problem.heuristic.MeanDistanceAllPegs;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

public class SenkuApplication extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start (final Stage stage) throws Exception {
		Node<SenkuBoard> rootNode = runSearch();

		SenkuStateDrawer drawer = new SenkuStateDrawer();

		Iterator<Node<SenkuBoard>> iterator = getSolutionStates(rootNode).iterator();

		final Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run () {
				if (iterator.hasNext()) {
					Node<SenkuBoard> node = iterator.next();
					drawer.draw(node.getState(), (SenkuMultipleMovement) node.getRule());
				}
				else {
					t.cancel();
				}
			}
		}, 0, 3000);

		Group root = new Group();
		stage.setResizable(false);
		stage.setScene(new Scene(root));

		root.getChildren().add(drawer.getCanvas());
		stage.show();
	}

	private Node<SenkuBoard> runSearch() {
		final Problem<SenkuBoard> problem = new SenkuMultipleProblem(SenkuBoardLoader.load("boards/easy.txt"));
		final Search<SenkuBoard> search = new AStar<>(new MeanDistanceAllPegs());

		final Optional<Node<SenkuBoard>> result = search.graphSearch(problem);
		return result.get();
	}

	private List<Node<SenkuBoard>> getSolutionStates(Node<SenkuBoard> last) {
		LinkedList<Node<SenkuBoard>> solution = new LinkedList<>();

		Node<SenkuBoard> node = last;
		while (node.hasParent()) {
			solution.addFirst(node);
			node = node.getParent();
		}

		solution.addFirst(node);

		return solution;
	}
}
