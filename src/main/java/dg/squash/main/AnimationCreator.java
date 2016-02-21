package dg.squash.main;

import dg.squash.math.Vector2D;
import dg.squash.ecs.components.NodeComponent;
import dg.squash.ecs.Entity;
import javafx.animation.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimationCreator {

    private AnimationCreator() {

    }

    public static Animation scoreAnimation(Vector2D position, Group group, SimpleIntegerProperty property) {
        Text scoreText = new Text();
        scoreText.setFill(Color.YELLOW);
        scoreText.textProperty().bind(property.asString());
        scoreText.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 30));
        scoreText.setX(position.getX() + 5);
        scoreText.setY(position.getY() + 40);

        group.getChildren().add(scoreText);

        FadeTransition ft = new FadeTransition(Duration.millis(1000), scoreText);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);

        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                group.getChildren().remove(scoreText);
            }
        });

        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), scoreText);
        tt.setByY(-20);
        tt.setCycleCount(1);

        tt.play();
        return ft;
    }

    public static Animation shakeAnimation(Node rectangle) {
        Timeline shake = new Timeline();
        shake.getKeyFrames().addAll(new KeyFrame(Duration.millis(0), new KeyValue(rectangle.translateXProperty(), 0)),

                new KeyFrame(Duration.millis(200), new KeyValue(rectangle.translateXProperty(), 3)),
                new KeyFrame(Duration.millis(300), new KeyValue(rectangle.translateXProperty(), -3)),
                new KeyFrame(Duration.millis(400), new KeyValue(rectangle.translateXProperty(), 3)),
                new KeyFrame(Duration.millis(500), new KeyValue(rectangle.translateXProperty(), -3)),
                new KeyFrame(Duration.millis(600), new KeyValue(rectangle.translateXProperty(), 3)),
                new KeyFrame(Duration.millis(700), new KeyValue(rectangle.translateXProperty(), -3)),
                new KeyFrame(Duration.millis(800), new KeyValue(rectangle.translateXProperty(), 3)),
                new KeyFrame(Duration.millis(900), new KeyValue(rectangle.translateXProperty(), -3)),
                new KeyFrame(Duration.millis(1000), new KeyValue(rectangle.translateXProperty(), 0)));
        shake.setCycleCount(1);
        shake.setRate(10);
        return shake;
    }

    public static Animation blowUpAnimation(Entity entity, Game game) {
        Rectangle rectangle = (Rectangle) entity.getComponent(NodeComponent.class).show();
        List<Image> images = new ArrayList<>();
        images.add(AssetManager.TOMATO_SPLAT_1);
        images.add(AssetManager.TOMATO_SPLAT_2);
        images.add(AssetManager.TOMATO_SPLAT_3);
        images.add(AssetManager.TOMATO_SPLAT_4);
        images.add(AssetManager.TOMATO_SPLAT_5);

        Timeline timeline = new Timeline();
        int dur = 100;
        for (Image image : images) {
            KeyFrame keyFrame = new KeyFrame(Duration.millis(dur), new KeyValue(rectangle.fillProperty(), new ImagePattern(image)));
            timeline.getKeyFrames().add(keyFrame);
            dur += 100;
        }
        timeline.setRate(2);

        ScaleTransition st = new ScaleTransition(Duration.millis(100), rectangle);
        st.setDuration(Duration.millis(100));
        st.setByX(0.4f);
        st.setByY(0.4f);
        st.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.play();
            }
        });


        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.getEntityBin().add(entity);
            }
        });
        return st;
    }


    public static Animation pauseOnAnimation(Node node) {
        TranslateTransition pauseAnimation = new TranslateTransition(Duration.millis(500), node);
        pauseAnimation.setByY(550);
        pauseAnimation.setCycleCount(1);
        pauseAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // pauseAnimation.setByY(0);
            }
        });
        return pauseAnimation;
    }

    public static Animation pauseOffAnimation(Node node) {
        TranslateTransition pauseAnimation = new TranslateTransition(Duration.millis(500), node);
        pauseAnimation.setByY(-550);
        pauseAnimation.setCycleCount(1);
        pauseAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // pauseAnimation.setByY(0);
            }
        });
        return pauseAnimation;
    }

    public static Animation timerAnimation(Game game) {

        Text createTimerText = new Text();
        createTimerText.setX(game.getScene().getWidth() / 2 - 50);
        createTimerText.setY(game.getScene().getHeight() / 2 - createTimerText.getWrappingWidth() / 2);
        createTimerText.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 150));
        createTimerText.setFill(Color.WHITE);
        createTimerText.setText(String.valueOf(3));
        createTimerText.setStroke(Color.YELLOW);
        createTimerText.setStrokeWidth(4);

        if (!game.getVisualDimension().getChildren().contains(createTimerText))
            game.getVisualDimension().getChildren().add(createTimerText);

        Timeline startAnimation = new Timeline();
        int dur = 0;
        for (int i = 3; i >= 0; i--) {
            KeyFrame keyFrame;
            keyFrame = new KeyFrame(Duration.millis(dur), new KeyValue(createTimerText.textProperty(), String.valueOf(i)));
            startAnimation.getKeyFrames().add(keyFrame);
            dur += 1000;
        }


        startAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createTimerText.setVisible(false);
                game.bigBang();
            }
        });
        return startAnimation;
    }
}
