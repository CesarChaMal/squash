package dg.squash.main;

import dg.squash.ecs.Entity;
import dg.squash.ecs.components.InputComponent;
import dg.squash.ecs.components.NodeComponent;
import dg.squash.ecs.components.ShapeComponent;
import dg.squash.ecs.components.VelocityComponent;
import dg.squash.events.InputListener;
import dg.squash.math.Vector2D;
import dg.squash.shape.CircleShape;
import dg.squash.shape.RectangleShape;
import dg.squash.utils.TwoTuple;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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

    private void initVisualDimension() {
        for (Entity e : logicDimension.getEntities()) {
            if (e.hasComponent(NodeComponent.class))
                visualDimension.getChildren().add(e.getComponent(NodeComponent.class).show());
        }
    }

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


    private Entity ball;
    private Entity board;
    private Entity leftWall;
    private Entity rightWall;
    private Entity topWall;
    private Entity bottomWall;

    private void initBall() {
        ball = EntityCreator.createBall(new Vector2D(500, 700), 15);
        logicDimension.getEntities().add(ball);

        CircleShape circleShape = (CircleShape) ball.getComponent(ShapeComponent.class).show();
        RectangleShape rectangleShape = (RectangleShape) board.getComponent(ShapeComponent.class).show();

        circleShape.centerXProperty().bind(rectangleShape.positionXProperty().add(rectangleShape.getWidth() / 2));
        circleShape.centerYProperty().bind(rectangleShape.positionYProperty().subtract(circleShape.getRadius() - 5));
    }

    private void initBoard() {

        double boardWidth = 170;
        board = EntityCreator.createBoard(new Vector2D(width / 2 - boardWidth / 2, height - 30), boardWidth, 20);
        logicDimension.getEntities().add(board);

        InputComponent inputComponent = board.getComponent(InputComponent.class);
        ShapeComponent shapeComponent = board.getComponent(ShapeComponent.class);
        inputComponent.setLeft(new InputListener() {
            @Override
            public void update() {
                if (shapeComponent.getPosition().getX() - 25 < 0)
                    shapeComponent.setPosition(new Vector2D(0, shapeComponent.getPosition().getY()));
                else
                    shapeComponent.moveHorizontal(-25);
            }
        });
        inputComponent.setRight(new InputListener() {
            @Override
            public void update() {
                if (shapeComponent.getPosition().getX() + boardWidth + 25 > scene.getWidth())
                    shapeComponent.setPosition(new Vector2D(scene.getWidth() - boardWidth, shapeComponent.getPosition().getY()));
                else
                    shapeComponent.moveHorizontal(25);
            }
        });

    }

    private void initMenu() {
        Entity background = EntityCreator.createBackground(width, height);
        logicDimension.getEntities().add(background);

        Entity middleRectangle = EntityCreator.createMiddleRectangle(new Vector2D(215, 160), 560, 290);
        logicDimension.getEntities().add(middleRectangle);

        initProgressBar();
        initTotalScore();
    }

    private void initProgressBar() {
        Entity progressBarContainer = EntityCreator.createProgressBarContainer(new Vector2D(240, 10), 270, 60);

        ImageView iv = (ImageView) progressBarContainer.getComponent(NodeComponent.class).show();
        Entity progressBar = EntityCreator.createProgressBar(iv);
        ((ProgressBar) progressBar.getComponent(NodeComponent.class).show()).progressProperty().bind(progress);

        logicDimension.getEntities().add(progressBar);
        logicDimension.getEntities().add(progressBarContainer);
    }

    private void initTotalScore() {
        Entity totalScoreContainer = EntityCreator.createTotalScoreContainer(new Vector2D(20, 10), 180, 70);
        logicDimension.getEntities().add(totalScoreContainer);

        Entity totalScore = EntityCreator.totalScoreText(new Vector2D(95, 55), 26);
        ((Text) totalScore.getComponent(NodeComponent.class).show()).textProperty().bind(totalScoreProperty.asString());
        logicDimension.getEntities().add(totalScore);
    }

    private void initWalls() {
        double wallSize = 50;
        leftWall = EntityCreator.createWall(new Vector2D(-wallSize, 0), wallSize, height);
        logicDimension.getEntities().add(leftWall);
        rightWall = EntityCreator.createWall(new Vector2D(width, 0), wallSize, height);
        logicDimension.getEntities().add(rightWall);
        topWall = EntityCreator.createWall(new Vector2D(0, 0), width, wallSize + 20);
        logicDimension.getEntities().add(topWall);
        bottomWall = EntityCreator.createWall(new Vector2D(0, height), width, wallSize);
        logicDimension.getEntities().add(bottomWall);
    }

    private void createEntities() {

        initMenu();
        initWalls();
        // createBalls();
        initBoard();
        initBall();
        initTomatoes(ball);

        initPauseButton();
        initResumeButton();


       // Entity restartButton = EntityCreator.createRestartButton(150, 50);
       // logicDimension.getEntities().add(restartButton);

     //   Entity optionsButton = EntityCreator.createOptionsButton(150, 50);
      //  logicDimension.getEntities().add(optionsButton);

        initCollisions();
        initInput();
    }

    private ImageView pauseContainer;

    private void initPauseButton() {
        Entity pauseContainer = EntityCreator.createPauseContainer(new Vector2D(scene.getWidth() / 2 - 400 / 2, -410), 400, 400);
        logicDimension.getEntities().add(pauseContainer);

        Entity pauseButton = EntityCreator.createPauseButton(new Vector2D(960, 10), 50, 50);
        logicDimension.getEntities().add(pauseButton);

        Animation animation = AnimationCreator.pauseOnAnimation(pauseContainer.getComponent(NodeComponent.class).show());
        ImageView pause = (ImageView) pauseButton.getComponent(NodeComponent.class).show();
        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isRunning()) {
                    stopTime();
                    animation.play();
                }
            }
        });

        this.pauseContainer = (ImageView) pauseContainer.getComponent(NodeComponent.class).show();
    }

    private void initResumeButton() {
        Entity resumeButton = EntityCreator.createResumeButton(150, 50);
        logicDimension.getEntities().add(resumeButton);

        ImageView resume = (ImageView) resumeButton.getComponent(NodeComponent.class).show();
        resume.layoutXProperty().bind(pauseContainer.layoutXProperty().add(pauseContainer.getFitWidth() / 2 - resume.getFitWidth() / 2));
        resume.layoutYProperty().bind(pauseContainer.translateYProperty().subtract(300));

        Animation animation = AnimationCreator.pauseOffAnimation(pauseContainer);
        resume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isRunning()) {
                    resumeTime();
                    animation.play();
                }
            }
        });
    }

    private void initCollisions() {
        logicDimension.getCollisions().add(new TwoTuple<>(board, ball));
        logicDimension.getCollisions().add(new TwoTuple<>(leftWall, ball));
        logicDimension.getCollisions().add(new TwoTuple<>(rightWall, ball));
        logicDimension.getCollisions().add(new TwoTuple<>(topWall, ball));
        logicDimension.getCollisions().add(new TwoTuple<>(bottomWall, ball));
    }

    private void initTomatoes(Entity ball) {
        int xOffset = 0;
        int yOffset = 0;
        int xGap = 5;
        int yGap = 5;
        int health = 5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Entity tomato = EntityCreator.createTomato(new Vector2D(225 + xOffset, 170 + yOffset), 50, 50, health);
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

    private void initInput() {

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
                        CircleShape circleShape = (CircleShape) ball.getComponent(ShapeComponent.class).show();
                        if (!circleShape.centerYProperty().isBound())
                            return;
                        ((CircleShape) ball.getComponent(ShapeComponent.class).show()).centerXProperty().unbind();
                        ((CircleShape) ball.getComponent(ShapeComponent.class).show()).centerYProperty().unbind();
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
