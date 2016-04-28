package dg.squash.main;

import dg.squash.ecs.Entity;
import dg.squash.ecs.EntityEngine;
import dg.squash.ecs.SystemEngine;
import dg.squash.ecs.components.EntityComponent;
import dg.squash.ecs.components.NodeComponent;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.net.URLConnection;

public class Squash extends Application {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private Initiator initiator = new Initiator();
    private SystemEngine systemEngine;
    private EntityEngine entityEngine;
    private boolean isRunning = false;
    private Stage stage;
    private SquashSplash squashSplash = new SquashSplash();

    private final AnimationTimer time = new AnimationTimer() {
        @Override
        public void handle(long now) {
            isRunning = true;
            systemEngine.start();
            for (Entity e : entityEngine.getEntity("BIN").getComponent(EntityComponent.class).show()) {
                entityEngine.removeEntity(e);
                initiator.getGroup().getChildren().remove(e.getComponent(NodeComponent.class).show());
            }
            if (entityEngine.hasEntity("RESTART")) {
                initiator = new Initiator();
                stage.setScene(initiator.getScene());
                initAll();
            }
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Squash");
        stage.sizeToScene();
        stage.setResizable(false);

        URL url = new URL("http://localhost/test.php?print=success");
        URLConnection connection = url.openConnection();
        connection.connect();

        initSplash();
        AssetManager.BE_HAPPY.play();

    }

    private void initSplash() {

        stage.setScene(squashSplash.getScene());
        squashSplash.initExitButton();
        squashSplash.initPlayButton(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                initAll();
                stage.setScene(initiator.getScene());
            }
        });
        stage.show();
    }

    private void initAll() {

        entityEngine = initiator.getEntityEngine();
        systemEngine = new SystemEngine(entityEngine);
        initPauseButton();
        initResumeButton();
        initRestartButton();
        time.start();
        stage.show();
    }

    private void initResumeButton() {

        Node container = entityEngine.getEntity("PAUSE_CONTAINER").getComponent(NodeComponent.class).show();
        TranslateTransition pauseAnimation = new TranslateTransition(Duration.millis(500), container);
        pauseAnimation.setByY(-550);
        pauseAnimation.setCycleCount(1);

        Node resume = entityEngine.getEntity("RESUME_BUTTON").getComponent(NodeComponent.class).show();
        resume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isRunning) {
                    isRunning = true;
                    pauseAnimation.play();
                    time.start();
                }
            }
        });
    }

    private void initRestartButton() {
        Node restart = entityEngine.getEntity("RESTART_BUTTON").getComponent(NodeComponent.class).show();

        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                initiator = new Initiator();
                stage.setScene(initiator.getScene());
                initAll();
            }
        });

    }

    private void initPauseButton() {

        Node container = entityEngine.getEntity("PAUSE_CONTAINER").getComponent(NodeComponent.class).show();
        TranslateTransition pauseAnimation = new TranslateTransition(Duration.millis(500), container);
        pauseAnimation.setByY(550);
        pauseAnimation.setCycleCount(1);

        Node pause = entityEngine.getEntity("PAUSE_BUTTON").getComponent(NodeComponent.class).show();
        pause.setOnMouseClicked(event -> {
            if (isRunning) {
                pauseAnimation.play();
                time.stop();
                isRunning = false;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
