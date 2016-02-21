package dg.squash.main;

import dg.squash.ecs.components.InputComponent;
import dg.squash.ecs.components.VelocityComponent;
import dg.squash.ecs.components.NodeComponent;
import dg.squash.ecs.components.ShapeComponent;
import dg.squash.ecs.Entity;
import dg.squash.math.Vector2D;
import dg.squash.utils.TwoTuple;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Scene scene;
    private final Group visualDimension;
    private final GameWorld logicDimension;
    private final GameWorldLaws gameWorldLaws;
    private List<Entity> entityBin = new ArrayList<>();
    private AnimationTimer worldTime;
    private boolean isRunning = false;
    private final static int TOTAL_ENEMIES = 50;
    private int destroyedEnemies;
    private final double width;
    private final double height;
    private SimpleIntegerProperty totalScoreProperty;
    private SimpleDoubleProperty progress;

    public void increaseScore(int increaseBy) {
        int totalScore = totalScoreProperty.get();
        totalScoreProperty.set(totalScore + increaseBy);
    }

    public void destroyEnemy() {
        destroyedEnemies++;
        progress.set((double) destroyedEnemies / TOTAL_ENEMIES);

    }

    public void init() {
        destroyedEnemies = 0;
        totalScoreProperty = new SimpleIntegerProperty(this, "totalScore", 0);
        progress = new SimpleDoubleProperty(this, "progress", 0);
        createEntities();
        initVisualDimension();
    }

    public Game(double width, double height) {
        this.visualDimension = new Group();
        this.scene = new Scene(visualDimension, width, height);
        this.width = width;
        this.height = height;
        this.logicDimension = new GameWorld();
        this.gameWorldLaws = new GameWorldLaws(this);
        init();
    }

    public List<Entity> getEntityBin() {
        return entityBin;
    }

    private void cleanBin() {
        for (Entity entity : entityBin) {
            logicDimension.removeEntity(entity);
            visualDimension.getChildren().remove(entity.getComponent(NodeComponent.class).show());

        }
    }

    public Group getVisualDimension() {
        return visualDimension;
    }

    public void bigBang() {

        this.worldTime = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                gameWorldLaws.applyRules(logicDimension);
                cleanBin();
            }
        };
        worldTime.start();
        isRunning = true;
    }

    public void stopTime() {
        worldTime.stop();
        isRunning = false;
    }

    public void reset() {
        logicDimension.getEntities().clear();
        visualDimension.getChildren().clear();
        getEntityBin().clear();
        init();
        AnimationCreator.timerAnimation(this).play();
    }

    public void resumeTime() {
        worldTime.start();
        isRunning = true;
    }

    public Scene getScene() {
        return scene;
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void createEntities() {

        Entity background = EntityCreator.createBackground(width, height);
        logicDimension.getEntities().add(background);

        Entity middleRectangle = EntityCreator.createMiddleRectangle(new Vector2D(215, 160), 560, 290);
        logicDimension.getEntities().add(middleRectangle);

        Entity totalScoreContainer = EntityCreator.createTotalScoreContainer(new Vector2D(20, 10), 180, 70);
        logicDimension.getEntities().add(totalScoreContainer);

        Entity totalScore = EntityCreator.totalScoreText(new Vector2D(95, 55), 26, totalScoreProperty);
        logicDimension.getEntities().add(totalScore);


        Entity progressBar = EntityCreator.createProgressBar(new Vector2D(300, 35), 205, 25, progress);
        logicDimension.getEntities().add(progressBar);

        Entity progressBarContainer = EntityCreator.createProgressBarContainer(new Vector2D(240, 10), 270, 60);
        logicDimension.getEntities().add(progressBarContainer);

        createBalls();

        double wallSize = 50;
        Entity leftWall = EntityCreator.createWall(new Vector2D(-wallSize, 0), wallSize, height);
        logicDimension.getEntities().add(leftWall);
        Entity rightWall = EntityCreator.createWall(new Vector2D(width, 0), wallSize, height);
        logicDimension.getEntities().add(rightWall);
        Entity topWall = EntityCreator.createWall(new Vector2D(0, 0), width, wallSize + 20);
        logicDimension.getEntities().add(topWall);
        Entity bottomWall = EntityCreator.createWall(new Vector2D(0, height), width, wallSize);
        logicDimension.getEntities().add(bottomWall);

        double boardWidth = 170;
        Entity board = EntityCreator.createBoard(new Vector2D(width / 2 - boardWidth / 2, height - 30), boardWidth, 20, this);
        logicDimension.getEntities().add(board);

        Entity ball = EntityCreator.createBall(new Vector2D(500, 700), 15, new Vector2D(0, 0), board);
        logicDimension.getEntities().add(ball);

        createTomatoes(ball);

        Entity pauseContainer = EntityCreator.createPauseContainer(new Vector2D(scene.getWidth() / 2 - 400 / 2, -410), 400, 400);
        logicDimension.getEntities().add(pauseContainer);

        Entity pauseButton = EntityCreator.createPauseButton(new Vector2D(960, 10), 50, 50, this, pauseContainer);
        logicDimension.getEntities().add(pauseButton);

        Entity resumeButton = EntityCreator.createResumeButton(150, 50, this, pauseContainer);
        logicDimension.getEntities().add(resumeButton);

        Entity restartButton = EntityCreator.createRestartButton(150, 50, this, pauseContainer);
        logicDimension.getEntities().add(restartButton);

        Entity optionsButton = EntityCreator.createOptionsButton(150, 50, this, pauseContainer);
        logicDimension.getEntities().add(optionsButton);


        logicDimension.getCollisions().add(new TwoTuple<>(board, ball));
        logicDimension.getCollisions().add(new TwoTuple<>(leftWall, ball));
        logicDimension.getCollisions().add(new TwoTuple<>(rightWall, ball));
        logicDimension.getCollisions().add(new TwoTuple<>(topWall, ball));
        logicDimension.getCollisions().add(new TwoTuple<>(bottomWall, ball));


        initInput(board, ball);
    }


    private void initVisualDimension() {
        for (Entity e : logicDimension.getEntities()) {
            if (e.hasComponent(NodeComponent.class))
                visualDimension.getChildren().add(e.getComponent(NodeComponent.class).show());
        }
    }

    private void createTomatoes(Entity ball) {
        int xOffset = 0;
        int yOffset = 0;
        int xGap = 5;
        int yGap = 5;
        int health = 5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Entity tomato = EntityCreator.createTomato(new Vector2D(225 + xOffset, 170 + yOffset), 50, 50, health, this);
                xOffset += 50 + xGap;
                logicDimension.getEntities().add(tomato);
                logicDimension.getCollisions().add(new TwoTuple<>(tomato, ball));
            }
            health--;
            xOffset = 0;
            yOffset += 50 + yGap;
        }
    }

    private void createBalls() {
        int xOffset = 0;
        for (int i = 0; i < 3; i++) {
            // Entity ball = EntityCreator.createBall(new Vector2D(570 + xOffset, 42), 20, new Vector2D(0, 0));
            // ball.removeComponent(ShapeComponent.class);
            // ball.removeComponent(VelocityComponent.class);
            // ball.removeComponent(MassComponent.class);
            // ball.removeComponent(DamageComponent.class);

            //logicDimension.getEntities().add(ball);
            xOffset += 45;
        }
    }

    private void initInput(Entity board, Entity ball) {

        Node rectangle = board.getComponent(NodeComponent.class).show();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        board.getComponent(InputComponent.class).setLeftPressed(true);
                        break;
                    case RIGHT:
                        board.getComponent(InputComponent.class).setRightPressed(true);
                        break;
                    case SPACE:
                        if (!ball.getComponent(ShapeComponent.class).getPositionXProperty().isBound())
                            return;
                        ball.getComponent(ShapeComponent.class).getPositionXProperty().unbind();
                        ball.getComponent(ShapeComponent.class).getPositionYProperty().unbind();
                        ball.getComponent(VelocityComponent.class).modify(new Vector2D(15, -15));
                        break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        board.getComponent(InputComponent.class).setLeftPressed(false);
                        break;
                    case RIGHT:
                        board.getComponent(InputComponent.class).setRightPressed(false);
                        break;
                }
            }
        });
    }
}
