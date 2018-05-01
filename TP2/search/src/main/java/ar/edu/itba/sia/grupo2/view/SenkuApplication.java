package ar.edu.itba.sia.grupo2.view;

import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.problem.*;
import ar.edu.itba.sia.grupo2.problem.heuristic.PegsDifficulty;
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
        final int period = 2000;
        Node<SenkuBoard> rootNode = runSearch();

        SenkuStateDrawer drawer = new SenkuStateDrawer();

        Iterator<StateAndAction> iterator = getSolutionStates(rootNode, false).iterator();

        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run () {
                if (iterator.hasNext()) {
                    StateAndAction stateAndAction = iterator.next();
                    drawer.draw(stateAndAction.state, stateAndAction.nextAction, (int) (period * 0.85));
                }
                else {
                    t.cancel();
                }
            }
        }, 0, period);

        Group root = new Group();
        stage.setResizable(false);
        stage.setScene(new Scene(root));

        root.getChildren().add(drawer.getCanvas());
        stage.show();
    }

    private Node<SenkuBoard> runSearch() {
        final Problem<SenkuBoard> problem = new SenkuProblem(SenkuBoardLoader.load("boards/board4.txt"));
        final Search<SenkuBoard> search = new AStar<>(new PegsDifficulty());

        final Optional<Node<SenkuBoard>> result = search.graphSearch(problem);

        System.out.println(result);

        return result.get();
    }

    private List<StateAndAction> getSolutionStates(Node<SenkuBoard> last, boolean isMultiple) {
        LinkedList<Node<SenkuBoard>> path = new LinkedList<>();

        Node<SenkuBoard> node = last;
        while (node.hasParent()) {
            path.addFirst(node);
            node = node.getParent();
        }

        path.addFirst(node);

        LinkedList<StateAndAction> solutions = new LinkedList<>();

        for (int i = 0; i  < path.size() - 1; i++) {
            Node<SenkuBoard> n = path.get(i+1);
            if (isMultiple)
                solutions.add(new StateAndAction(path.get(i).getState(), (SenkuMultipleMovement) n.getRule()));
            else
                solutions.add(new StateAndAction(path.get(i).getState(), simpleToMultiple((SenkuMovement) n.getRule())));
        }

        solutions.add(new StateAndAction(path.getLast().getState(), null));

        return solutions;
    }

    private SenkuMultipleMovement simpleToMultiple(SenkuMovement movement) {
        final List<Coordinate> path = new LinkedList<>();
        path.add(movement.getFrom());
        path.add(movement.getTo());
        return new SenkuMultipleMovement(path);
    }

    private static class StateAndAction {
        SenkuBoard state;
        SenkuMultipleMovement nextAction;

        private StateAndAction (SenkuBoard state, SenkuMultipleMovement nextAction) {
            this.state = state;
            this.nextAction = nextAction;
        }
    }
}
