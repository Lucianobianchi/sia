package ar.edu.itba.sia.grupo2.view;

import ar.com.itba.sia.Heuristic;
import ar.com.itba.sia.Problem;
import ar.edu.itba.sia.grupo2.engine.Node;
import ar.edu.itba.sia.grupo2.engine.Search;
import ar.edu.itba.sia.grupo2.engine.informed.AStar;
import ar.edu.itba.sia.grupo2.problem.*;
import ar.edu.itba.sia.grupo2.problem.heuristic.DistanceAllPegs;
import ar.edu.itba.sia.grupo2.problem.heuristic.DistanceToTarget;
import ar.edu.itba.sia.grupo2.problem.heuristic.IsolatedPegs;
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
    public void start (final Stage primaryStage) throws Exception {
        Node<SenkuBoard> rootNode0 = runSearch(new AStar<>(new IsolatedPegs()));
        Node<SenkuBoard> rootNode1 = runSearch(new AStar<>(new PegsDifficulty()));
        Node<SenkuBoard> rootNode2 = runSearch(new AStar<>(new DistanceToTarget()));
        Node<SenkuBoard> rootNode3 = runSearch(new AStar<>(new DistanceAllPegs()));

        String[] titles = { "Isolated Pegs", "Pegs Difficulty", "Distance to Target", "Distance all pegs"};

        while(true) {
            List<Stage> stages = renderMultiple(rootNode0, rootNode1, rootNode2, rootNode3);

            Stage prevStage = null;
            for(int i = 0 ; i < stages.size() ; i++) {
                Stage s = stages.get(i);
                if (prevStage != null) {
                    s.setX(prevStage.getX() + prevStage.getWidth() + 20);
                    s.setY(prevStage.getY());
                } else {
                    s.setX(50);
                }
                s.setTitle(titles[i]);
                prevStage = s;
                s.show();
            }

            Stage holder = new Stage();
            holder.setTitle("Close to reset");
            holder.setWidth(400);
            holder.setHeight(10);
            holder.setY(50);
            holder.toBack();
            holder.showAndWait();

            stages.forEach(Stage::close);
        }
    }


    @SafeVarargs
    private final List<Stage> renderMultiple(Node<SenkuBoard>... roots)  {
        List<Stage> stages = new ArrayList<>();
        for (Node<SenkuBoard> root : roots) {
            Stage stage = new Stage();
            render(stage, root);
            stages.add(stage);
        }

        return stages;
    }

    private void render(final Stage stage, final Node<SenkuBoard> rootNode) {
        final int period = 2000;

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
    }


    private Node<SenkuBoard> runSearch(Search<SenkuBoard> search) {
        final Problem<SenkuBoard> problem = new SenkuProblem(SenkuBoardLoader.load("boards/board4.txt"));
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
