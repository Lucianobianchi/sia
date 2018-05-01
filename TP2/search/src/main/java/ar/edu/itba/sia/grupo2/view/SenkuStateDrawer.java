package ar.edu.itba.sia.grupo2.view;

import ar.edu.itba.sia.grupo2.problem.*;
import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    private static final Color LINE_COLOR = Color.RED;
    private static final double LINE_WIDTH = 5;

    private final Map<SenkuContent, Image> images;
    private final Canvas canvas;
    private final Timeline timeline = new Timeline();
    private AnimationTimer timer;

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

    private void drawAt(final int row, final int col, final SenkuContent type) {
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);
        gc.drawImage(images.get(type), SQUARE_SIZE * col, SQUARE_SIZE * row, SQUARE_SIZE, SQUARE_SIZE);
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
                drawAt(row, col, type);
            }
        }
    }

    public void draw(final SenkuBoard board, final SenkuMultipleMovement movement, int duration) {
        draw(board);
        if (movement == null)
            return;

        List<Coordinate> path = movement.getPath();
        int durationForEachMovement = duration / path.size();

        GraphicsContext gc = canvas.getGraphicsContext2D();
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

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        final IntegerProperty x = new SimpleIntegerProperty(from.getColumn() * SQUARE_SIZE);
        final IntegerProperty y = new SimpleIntegerProperty(from.getRow() * SQUARE_SIZE);
        final KeyValue kvX = new KeyValue(x, to.getColumn() * SQUARE_SIZE);
        final KeyValue kvY = new KeyValue(y, to.getRow() * SQUARE_SIZE);
        final KeyFrame kf = new KeyFrame(duration, kvX, kvY);

        timeline.getKeyFrames().add(kf);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawAt(from.getRow(), from.getColumn(), SenkuContent.EMPTY);
                gc.drawImage(images.get(SenkuContent.PEG), x.get(), y.get(), SQUARE_SIZE, SQUARE_SIZE);
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
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(toPixel(from.getColumn()), toPixel(from.getRow()),
                toPixel(to.getColumn()), toPixel(to.getRow()));
    }

    private static int toPixel (int coord) {
        return coord * SQUARE_SIZE + SQUARE_SIZE / 2;
    }
}
