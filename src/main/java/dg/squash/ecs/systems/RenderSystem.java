package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.SystemEngine;
import dg.squash.ecs.components.*;
import dg.squash.main.AssetManager;
import dg.squash.shape.RectangleShape;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class RenderSystem extends AbstractECSystem {

    public RenderSystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    public void update(Entity bin, Entity entity) {
        int health = entity.getComponent(HealthComponent.class).show();
        ImageView rectangle = (ImageView) entity.getComponent(NodeComponent.class).show();
        shake(rectangle);
        switch (health) {
            case 0:
                blowUp(entity, bin);
                break;
            case 1:
                rectangle.setImage(AssetManager.TOMATO_CRYING);
                break;
            case 2:
                rectangle.setImage(AssetManager.TOMATO_SAD);
                break;
            case 3:
                rectangle.setImage(AssetManager.TOMATO_STUPID);
                break;
            case 4:
                rectangle.setImage(AssetManager.TOMATO_ARE_YOU_JOKING);
                break;
            case 5:
                rectangle.setImage(AssetManager.TOMATO_LAUGHING);
                break;
        }
    }

    private void shake(ImageView rectangle) {
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
        shake.play();
    }

    private void blowUp(Entity entity, Entity bin) {
        ImageView rectangle = (ImageView) entity.getComponent(NodeComponent.class).show();
        List<Image> images = new ArrayList<>();
        images.add(AssetManager.TOMATO_SPLAT_1);
        images.add(AssetManager.TOMATO_SPLAT_2);
        images.add(AssetManager.TOMATO_SPLAT_3);
        images.add(AssetManager.TOMATO_SPLAT_4);
        images.add(AssetManager.TOMATO_SPLAT_5);

        Timeline timeline = new Timeline();
        int dur = 100;
        for (Image image : images) {
            KeyFrame keyFrame = new KeyFrame(Duration.millis(dur), new KeyValue(rectangle.imageProperty(), image));
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
                bin.getComponent(EntityComponent.class).show().add(entity);
            }
        });
        st.play();
    }

    public void playScoreAnimation(Entity entity) {
        RectangleShape rectangleShape = (RectangleShape) entity.getComponent(ShapeComponent.class).show();
        int score = entity.getComponent(ScoreComponent.class).show();
        Node node = entity.getComponent(NodeComponent.class).show();
        Text scoreText = new Text();
        scoreText.setText(String.valueOf(score));
        scoreText.setFill(Color.YELLOW);
        scoreText.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 30));
        scoreText.setX(rectangleShape.getPosition().getX() + 5);
        scoreText.setY(rectangleShape.getPosition().getY() + 40);

        if (!((Group) node.getParent()).getChildren().contains(scoreText))
            ((Group) node.getParent()).getChildren().add(scoreText);

        FadeTransition ft = new FadeTransition(Duration.millis(1000), scoreText);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);

        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), scoreText);
        tt.setByY(-20);
        tt.setCycleCount(1);

        tt.play();
        ft.play();

        tt.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scoreText.setVisible(false);
            }
        });

    }
}
