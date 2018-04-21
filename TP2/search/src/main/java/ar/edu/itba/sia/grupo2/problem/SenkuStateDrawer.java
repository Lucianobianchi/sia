package ar.edu.itba.sia.grupo2.problem;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

// TODO: hay que ver bien como usar el drawer. No puede ser algo estático por como funciona JavaFX,
// tiene que tener un listener que le diga cuando dibujar de nuevo un tablero.
public class SenkuStateDrawer extends Application {
	private static final int SQUARE_SIZE = 50;

	public static void draw(final SenkuBoard board, final GraphicsContext gc) {
		// TODO: esto esta mal pero no me deja instanciar Image estáticamente.
		Map<SenkuContent, Image> images = new HashMap<>();
		images.put(SenkuContent.INVALID, new Image("file:assets/invalidSquare.png"));
		images.put(SenkuContent.PEG, new Image("file:assets/pegSquare.png"));
		images.put(SenkuContent.EMPTY, new Image("file:assets/emptySquare.png"));


		int dim = board.getDimension();

		for (int row = 0; row < dim; row++) {
			for (int col = 0; col < dim; col++) {
				SenkuContent type = board.getContent(row, col);
				gc.clearRect(SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);
				gc.drawImage(images.get(type),
						SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}

	private static final SenkuBoard board = new SenkuBoard(SenkuBoardParser.parse("boards/board1.txt"));;

	@Override
	public void start (Stage stage) throws Exception {
		Group root = new Group();

		stage.setResizable(false);
		stage.setScene(new Scene(root));

		int pixelSize = board.getDimension() * SQUARE_SIZE;
		Canvas graphicBoard = new Canvas(pixelSize, pixelSize);
		draw(board, graphicBoard.getGraphicsContext2D());

		root.getChildren().add(graphicBoard);
		stage.show();
	}

	public static void main (String[] args) {
		launch(args);
	}
}
