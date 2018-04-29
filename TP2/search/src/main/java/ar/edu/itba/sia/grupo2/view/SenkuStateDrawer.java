package ar.edu.itba.sia.grupo2.view;

import ar.edu.itba.sia.grupo2.problem.*;
import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class SenkuStateDrawer {
	private static final int SQUARE_SIZE = 50;
	private static final int PEG_SIZE = 40;
	private static final Color LINE_COLOR = Color.RED;
	private static final double LINE_WIDTH = 5;

	private final Map<SenkuContent, Image> images;
	private final Canvas boardCanvas;
	private final Canvas pegCanvas;
	private final Pane pane;
	private final Timeline timeline = new Timeline();
	private AnimationTimer timer;

    public SenkuStateDrawer() {
		images = new HashMap<>();
		images.put(SenkuContent.INVALID, new Image("file:assets/invalidSquare.png"));
		images.put(SenkuContent.PEG, new Image("file:assets/peq.png"));
		images.put(SenkuContent.EMPTY, new Image("file:assets/emptySquare.png"));
		boardCanvas = new Canvas();
		pegCanvas = new Canvas();
		pane = new Pane();
		pane.getChildren().add(boardCanvas);
		pane.getChildren().add(pegCanvas);
		pegCanvas.toFront();
	}

	public Node getNode() {
		return pane;
	}

	private void drawAtBoard(final int row, final int col, SenkuContent type) {
        final GraphicsContext gc = boardCanvas.getGraphicsContext2D();

        if (type == SenkuContent.PEG)
            type = SenkuContent.EMPTY;

        gc.clearRect(SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);
        gc.drawImage(images.get(type), SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawPeg(final int row, final int col) {
        final GraphicsContext gc = pegCanvas.getGraphicsContext2D();
        gc.drawImage(images.get(SenkuContent.PEG), SQUARE_SIZE * col + 5, SQUARE_SIZE * row + 5, PEG_SIZE, PEG_SIZE);
    }

	public void draw(final SenkuBoard board) {
		int pixelSize = board.getDimension() * SQUARE_SIZE;
		boardCanvas.setHeight(pixelSize);
		boardCanvas.setWidth(pixelSize);
        pegCanvas.setHeight(pixelSize);
        pegCanvas.setWidth(pixelSize);

		int dim = board.getDimension();

		for (int row = 0; row < dim; row++) {
			for (int col = 0; col < dim; col++) {
			    SenkuContent type = board.getContent(row, col);
			    drawAtBoard(row, col, type);
			    pegCanvas.getGraphicsContext2D().clearRect(SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);

			    if (type == SenkuContent.PEG)
			        drawPeg(row, col);
			}
		}
	}

	public void draw(final SenkuBoard board, final SenkuMultipleMovement movement, final int duration) {
		draw(board);
		if (movement == null)
			return;

		List<Coordinate> path = movement.getPath();
		int durationForEachMovement = duration / path.size();

		GraphicsContext gc = boardCanvas.getGraphicsContext2D();
		gc.setStroke(LINE_COLOR);
		gc.setLineWidth(LINE_WIDTH);
		gc.setLineJoin(StrokeLineJoin.ROUND);
		gc.setLineCap(StrokeLineCap.ROUND);

		for (int i = 0; i < path.size() - 1; i++) {
		    int runAt = i * durationForEachMovement;
			Coordinate from = path.get(i);
			Coordinate to = path.get(i+1);
			drawLine(from, to);
			animateAt(from, to, Duration.millis(durationForEachMovement), Duration.millis(runAt));
		}
	}

	private void animateAt(Coordinate from, Coordinate to, Duration duration, Duration delay) {
        PauseTransition pause = new PauseTransition(delay);
        pause.setOnFinished(e -> animate(from, to, duration));
        pause.play();
    }

    private void animate(Coordinate from, Coordinate to, Duration duration) {
		timeline.stop();
		timeline.getKeyFrames().clear();

		if (timer != null)
		    timer.stop();

		final GraphicsContext gc = pegCanvas.getGraphicsContext2D();
        final IntegerProperty x = new SimpleIntegerProperty(from.getColumn() * SQUARE_SIZE);
        final IntegerProperty y = new SimpleIntegerProperty(from.getRow() * SQUARE_SIZE);
        final KeyValue kvX = new KeyValue(x, to.getColumn() * SQUARE_SIZE);
        final KeyValue kvY = new KeyValue(y, to.getRow() * SQUARE_SIZE);
        final KeyFrame kf = new KeyFrame(duration, kvX, kvY);

		timeline.getKeyFrames().add(kf);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(x.get(), y.get(), SQUARE_SIZE, SQUARE_SIZE);
                gc.drawImage(images.get(SenkuContent.PEG), x.get() + 5, y.get() + 5, PEG_SIZE, PEG_SIZE);
            }
        };

		timeline.play();
		timer.start();
    }

    public void draw(final SenkuBoard board, final SenkuMovement movement, final int duration) {
		final List<Coordinate> path = new LinkedList<>();
		path.add(movement.getFrom());
		path.add(movement.getTo());
		draw(board, new SenkuMultipleMovement(path), duration);
	}

	private void drawLine(Coordinate from, Coordinate to) {
		GraphicsContext gc = boardCanvas.getGraphicsContext2D();
		gc.strokeLine(toPixel(from.getColumn()), toPixel(from.getRow()),
				toPixel(to.getColumn()), toPixel(to.getRow()));
	}

	private static int toPixel (int coord) {
		return coord * SQUARE_SIZE + SQUARE_SIZE / 2;
	}
}
