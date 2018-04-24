package ar.edu.itba.sia.grupo2.view;

import ar.edu.itba.sia.grupo2.problem.Coordinate;
import ar.edu.itba.sia.grupo2.problem.SenkuBoard;
import ar.edu.itba.sia.grupo2.problem.SenkuContent;
import ar.edu.itba.sia.grupo2.problem.SenkuMultipleMovement;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SenkuStateDrawer {
	private static final int SQUARE_SIZE = 50;
	private static final Color LINE_COLOR = Color.RED;
	private static final double LINE_WIDTH = 5;

	private final Map<SenkuContent, Image> images;
	private final Canvas canvas;

	public SenkuStateDrawer () {
		images = new HashMap<>();
		images.put(SenkuContent.INVALID, new Image("file:assets/invalidSquare.png"));
		images.put(SenkuContent.PEG, new Image("file:assets/pegSquare.png"));
		images.put(SenkuContent.EMPTY, new Image("file:assets/emptySquare.png"));
		canvas = new Canvas();
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void draw(final SenkuBoard board) {
		int pixelSize = board.getDimension() * SQUARE_SIZE;
		canvas.setHeight(pixelSize);
		canvas.setWidth(pixelSize);
		GraphicsContext gc = canvas.getGraphicsContext2D();

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

	// TODO: SimpleMovement
	public void draw(final SenkuBoard board, final SenkuMultipleMovement movement) {
		draw(board);
		List<Coordinate> path = movement.getPath();

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(LINE_COLOR);
		gc.setLineWidth(LINE_WIDTH);
		gc.setLineJoin(StrokeLineJoin.ROUND);
		gc.setLineCap(StrokeLineCap.ROUND);

		for (int i = 0; i < path.size() - 1; i++) {
			Coordinate from = path.get(i);
			Coordinate to = path.get(i+1);
			drawLine(from, to);
		}
	}

	private void drawLine(Coordinate from, Coordinate to) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.strokeLine(toPixel(from.getColumn()), toPixel(from.getRow()),
				toPixel(to.getColumn()), toPixel(to.getRow()));
	}

	private static int toPixel (int coord) {
		return coord * SQUARE_SIZE + SQUARE_SIZE / 2;
	}
}
