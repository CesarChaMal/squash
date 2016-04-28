package dg.squash.main;

import dg.squash.math.Vector2D;
import dg.squash.shape.RectangleShape;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NodeCreator {

    private NodeCreator() {

    }

    public static ImageView createBoundView(Image image, DoubleBinding x, DoubleBinding y,
                                            DoubleBinding width, DoubleBinding height) {
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(width);
        imageView.fitHeightProperty().bind(height);
        imageView.layoutXProperty().bind(x);
        imageView.layoutYProperty().bind(y);
        return imageView;
    }

    public static ImageView createBoundView(Image image, RectangleShape shape) {
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(shape.widthProperty());
        imageView.fitHeightProperty().bind(shape.heightProperty());
        imageView.layoutXProperty().bind(shape.positionXProperty());
        imageView.layoutYProperty().bind(shape.positionYProperty());
        return imageView;
    }

    public static ImageView createView(Vector2D position, double width, double height, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(position.getX());
        imageView.setLayoutY(position.getY());
        return imageView;
    }

    public static Rectangle createRectangle(Vector2D position, double width, double height, Paint paint) {
        Rectangle rectangle = new Rectangle(width, height, paint);
        rectangle.setY(position.getY());
        rectangle.setX(position.getX());
        return rectangle;
    }

    public static Text createText(Vector2D position, Font font, Paint fill) {
        Text text = new Text();
        text.setX(position.getX());
        text.setY(position.getY());
        text.setFont(font);
        text.setFill(fill);
        return text;
    }

    public static ProgressBar createBoundProgressBar(DoubleBinding x, DoubleBinding y,
                                                     DoubleBinding width, DoubleBinding height) {
        ProgressBar progressBar = new ProgressBar();
        progressBar.layoutXProperty().bind(x);
        progressBar.layoutYProperty().bind(y);
        progressBar.minWidthProperty().bind(width);
        progressBar.minHeightProperty().bind(height);
        return progressBar;
    }
}

