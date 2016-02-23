package dg.squash.main;

import dg.squash.ecs.Entity;
import dg.squash.ecs.EntityEngine;
import dg.squash.ecs.components.*;
import dg.squash.math.Vector2D;
import dg.squash.shape.CircleShape;
import dg.squash.shape.RectangleShape;
import dg.squash.utils.TwoTuple;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Initiator {

    private static final double WIDTH = 1024;
    private static final double HEIGHT = 768;
    private EntityEngine entityEngine = new EntityEngine();
    private Group group = new Group();
    private Scene scene = new Scene(group, WIDTH, HEIGHT);
    private Entity game;

    public Initiator() {
        init();
    }

    public Scene getScene() {
        return scene;
    }

    public Group getGroup() {
        return group;
    }

    public EntityEngine getEntityEngine() {
        return entityEngine;
    }

    private void init() {
        initGame();
        initBin();
        initContainers();
        initTotalScoreText();
        initProgressBar();


        initBoard();
        initBall();
        initWalls();
        initTomatoes();


        initPauseContainer();
        initPauseButton();
        initResumeButton();
        initRestartButton();

        initCollisions();
        initInput();
    }

    public void initCollisions() {
        Entity ball = entityEngine.getEntity("BALL");
        entityEngine.getCollisions().add(new TwoTuple<>(entityEngine.getEntity("BOARD"), ball));
        entityEngine.getCollisions().add(new TwoTuple<>(entityEngine.getEntity("LEFT_WALL"), ball));
        entityEngine.getCollisions().add(new TwoTuple<>(entityEngine.getEntity("RIGHT_WALL"), ball));
        entityEngine.getCollisions().add(new TwoTuple<>(entityEngine.getEntity("TOP_WALL"), ball));
        entityEngine.getCollisions().add(new TwoTuple<>(entityEngine.getEntity("BOTTOM_WALL"), ball));
    }

    private void initPauseContainer() {
        addEntity(entityWithViewNode("PAUSE_CONTAINER", new Vector2D(scene.getWidth() / 2 - 400 / 2, -410), 400, 400, AssetManager.PAUSED_CONTAINER));
    }

    private void initContainers() {

        addEntity(entityWithViewNode("BACKGROUND", new Vector2D(0, 0), WIDTH, HEIGHT, AssetManager.SPLASH_SCREEN_BACKGROUND));
        addEntity(entityWithViewNode("PROGRESS_BAR_CONTAINER", new Vector2D(240, 10), 270, 60, AssetManager.PROGRESS));
        addEntity(entityWithViewNode("TOTAL_SCORE_CONTAINER", new Vector2D(20, 10), 180, 70, AssetManager.SCORE));

        Entity middleRectangle = new Entity();
        middleRectangle.addComponent(new NodeComponent(middleRectangle, NodeCreator.createRectangle(new Vector2D(215, 160), 560, 290, Color.rgb(125, 125, 125, 0.5))));
        addEntity(middleRectangle);
    }

    private void initTotalScoreText() {
        ImageView textContainer = (ImageView) entityEngine.getEntity("TOTAL_SCORE_CONTAINER").getComponent(NodeComponent.class).show();

        Entity totalScore = new Entity();
        Font font = Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 26);
        Text score = new Text();
        score.setFont(font);
        score.setFill(Color.WHITE);
        score.xProperty().bind(textContainer.xProperty().add(90));
        score.yProperty().bind(textContainer.yProperty().add(55));
        score.textProperty().bind(game.getComponent(GameComponent.class).show().totalScoreProperty().asString());
        totalScore.addComponent(new NameComponent(totalScore, "TOTAL_SCORE"));
        totalScore.addComponent(new NodeComponent(totalScore, score));

        addEntity(totalScore);
    }

    private void initGame() {
        game = new Entity();
        game.addComponent(new NameComponent(game, "GAME"));
        GameLogic gameLogic = new GameLogic();
        gameLogic.setTotalEnemies(50);

        game.addComponent(new GameComponent(game, gameLogic));
        addEntity(game);
    }

    private void initTomatoes() {
        Entity ball = entityEngine.getEntity("BALL");
        int xOffset = 0;
        int yOffset = 0;
        int xGap = 5;
        int yGap = 5;
        int health = 5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                Entity tomato = createTomato(new Vector2D(225 + xOffset, 170 + yOffset), 50, 50, health);
                xOffset += 50 + xGap;
                addEntity(tomato);
                entityEngine.getCollisions().add(new TwoTuple<>(tomato, ball));
            }
            health--;
            xOffset = 0;
            yOffset += 50 + yGap;
        }
    }

    private void addEntity(Entity entity) {
        entityEngine.getEntities().add(entity);
        if (entity.hasComponent(NodeComponent.class))
            group.getChildren().add(entity.getComponent(NodeComponent.class).show());
    }

    private void initBoard() {
        int width = 170;
        int height = 20;
        Vector2D position = new Vector2D(WIDTH / 2 - width / 2, HEIGHT - 30);

        Entity board = new Entity();
        RectangleShape rectangleShape = new RectangleShape(position, width, height);
        ImageView rectangle = NodeCreator.createBoundView(AssetManager.BOARD, rectangleShape);

        InputComponent input = new InputComponent(board);
        input.show().setLeft(() -> board.getComponent(ShapeComponent.class).moveHorizontal(-15));
        input.show().setRight(() -> board.getComponent(ShapeComponent.class).moveHorizontal(15));

        board.addComponent(input);
        board.addComponent(new NameComponent(board, "BOARD"));
        board.addComponent(new ShapeComponent(board, rectangleShape));
        board.addComponent(new NodeComponent(board, rectangle));
        board.addComponent(new VelocityComponent(board));
        board.addComponent(new MassComponent(board));
        addEntity(board);
    }

    private void initBin() {
        Entity bin = new Entity();
        bin.addComponent(new NameComponent(bin, "BIN"));
        bin.addComponent(new EntityComponent(bin));
        addEntity(bin);
    }

    private void initBall() {

        double radius = 15;
        Vector2D position = new Vector2D(50, 50);

        RectangleShape board = (RectangleShape) entityEngine.getEntity("BOARD").getComponent(ShapeComponent.class).show();
        Entity ball = new Entity();
        CircleShape circleShape = new CircleShape(position, radius);
        circleShape.centerXProperty().bind(board.positionXProperty().add(board.getWidth() / 2));
        circleShape.centerYProperty().bind(board.positionYProperty().subtract(radius));

        ImageView circle = NodeCreator.createBoundView(AssetManager.BALL, circleShape.centerXProperty().subtract(radius),
                circleShape.centerYProperty().subtract(radius), circleShape.radiusProperty().multiply(2),
                circleShape.radiusProperty().multiply(2));

        ball.addComponent(new NameComponent(ball, "BALL"));
        ball.addComponent(new ShapeComponent(ball, circleShape));
        ball.addComponent(new NodeComponent(ball, circle));
        ball.addComponent(new VelocityComponent(ball));
        ball.addComponent(new MassComponent(ball, 1));
        ball.addComponent(new DamageComponent(ball, 1));

        addEntity(ball);
    }

    private void initWalls() {
        double wallSize = 50;
        addEntity(createWall("LEFT_WALL", new Vector2D(-wallSize, 0), wallSize, HEIGHT));
        addEntity(createWall("RIGHT_WALL", new Vector2D(WIDTH, 0), wallSize, HEIGHT));
        addEntity(createWall("TOP_WALL", new Vector2D(0, 0), WIDTH, wallSize + 20));
        addEntity(createWall("BOTTOM_WALL", new Vector2D(0, HEIGHT), WIDTH, wallSize));

    }

    private Entity createWall(String name, Vector2D position, double width, double height) {

        Entity wall = new Entity();
        RectangleShape rectangle = new RectangleShape(position, width, height);
        wall.addComponent(new NameComponent(wall, name));
        wall.addComponent(new ShapeComponent(wall, rectangle));
        wall.addComponent(new VelocityComponent(wall));
        wall.addComponent(new MassComponent(wall));
        return wall;
    }

    private Entity createTomato(Vector2D position, double width, double height, int health) {

        Entity tomato = new Entity();
        RectangleShape rectangleShape = new RectangleShape(position, width, height);
        ImageView rectangle = NodeCreator.createBoundView(AssetManager.TOMATO_CRYING, rectangleShape);
        switch (health) {
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
        tomato.addComponent(new VelocityComponent(tomato, new Vector2D(0, 0)));
        tomato.addComponent(new MassComponent(tomato));
        tomato.addComponent(new ShapeComponent(tomato, rectangleShape));
        tomato.addComponent(new NodeComponent(tomato, rectangle));
        tomato.addComponent(new ScoreComponent(tomato));
        tomato.addComponent(new HealthComponent(tomato, health));
        return tomato;
    }

    private void initPauseButton() {
        Entity pauseButton = new Entity();
        ImageView pause = NodeCreator.createView(new Vector2D(960, 10), 50, 50, AssetManager.PAUSE_BUTTON);
        pause.setOnMouseEntered(event -> pause.setImage(AssetManager.PAUSE_BUTTON_HOVER));
        pause.setOnMouseExited(event -> pause.setImage(AssetManager.PAUSE_BUTTON));

        pauseButton.addComponent(new NameComponent(pauseButton, "PAUSE_BUTTON"));
        pauseButton.addComponent(new NodeComponent(pauseButton, pause));

        addEntity(pauseButton);
    }

    private void initResumeButton() {
        Entity resumeButton = new Entity();
        ImageView resume = NodeCreator.createView(new Vector2D(0, 0), 150, 50, AssetManager.RESUME_BUTTON);

        ImageView pauseContainer = (ImageView) entityEngine.getEntity("PAUSE_CONTAINER").getComponent(NodeComponent.class).show();
        resume.layoutXProperty().bind(pauseContainer.layoutXProperty().add(pauseContainer.getFitWidth() / 2 - resume.getFitWidth() / 2));
        resume.layoutYProperty().bind(pauseContainer.translateYProperty().subtract(300));

        resume.setOnMouseEntered(event -> resume.setImage(AssetManager.RESUME_BUTTON_HOVER));
        resume.setOnMouseExited(event -> resume.setImage(AssetManager.RESUME_BUTTON));
        resumeButton.addComponent(new NameComponent(resumeButton, "RESUME_BUTTON"));
        resumeButton.addComponent(new NodeComponent(resumeButton, resume));

        addEntity(resumeButton);
    }

    private void initRestartButton() {
        Entity restartButton = new Entity();

        ImageView restart = new ImageView(AssetManager.RESTART_BUTTON);
        restart.setFitWidth(150);
        restart.setFitHeight(50);

        ImageView container = (ImageView) entityEngine.getEntity("PAUSE_CONTAINER").getComponent(NodeComponent.class).show();
        restart.layoutXProperty().bind(container.layoutXProperty().add(container.getFitWidth() / 2 - restart.getFitWidth() / 2));
        restart.layoutYProperty().bind(container.translateYProperty().subtract(215));

        restart.setOnMouseEntered(event -> restart.setImage(AssetManager.RESTART_BUTTON_HOVER));
        restart.setOnMouseExited(event -> restart.setImage(AssetManager.RESTART_BUTTON));

        restartButton.addComponent(new NameComponent(restartButton, "RESTART_BUTTON"));
        restartButton.addComponent(new NodeComponent(restartButton, restart));
        addEntity(restartButton);
    }
/*
    public static Entity createOptionsButton(double width, double height) {

        Entity pauseButton = new Entity();

        ImageView container = (ImageView) entity.getComponent(NodeComponent.class).show();
        ImageView restart = new ImageView(AssetManager.OPTIONS_BUTTON);
        restart.setFitWidth(width);
        restart.setFitHeight(height);
        restart.layoutXProperty().bind(container.layoutXProperty().add(container.getFitWidth() / 2 - restart.getFitWidth() / 2));
        restart.layoutYProperty().bind(container.translateYProperty().subtract(130));
        restart.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                restart.setImage(AssetManager.OPTIONS_BUTTON_HOVER);
            }
        });
        restart.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                restart.setImage(AssetManager.OPTIONS_BUTTON);
            }
        });

        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            }
        });

        pauseButton.addComponent(new NodeComponent(pauseButton, restart));
        return pauseButton;
    }

    */

    private void initProgressBar() {

        ImageView barContainer = (ImageView) entityEngine.getEntity("PROGRESS_BAR_CONTAINER").getComponent(NodeComponent.class).show();
        Entity progress = new Entity();
        ProgressBar completed = NodeCreator.createBoundProgressBar(
                barContainer.layoutXProperty().add(60), barContainer.layoutYProperty().add(22),
                barContainer.fitWidthProperty().subtract(67), barContainer.fitHeightProperty().subtract(265));

        completed.progressProperty().bind(game.getComponent(GameComponent.class).show().progressProperty());
        progress.addComponent(new NameComponent(progress, "PROGRESS_BAR"));
        progress.addComponent(new NodeComponent(progress, completed));

        addEntity(progress);
    }

    private Entity entityWithViewNode(String name, Vector2D position, double width, double height, Image image) {
        Entity entity = new Entity();
        entity.addComponent(new NodeComponent(entity, NodeCreator.createView(position, width, height, image)));
        entity.addComponent(new NameComponent(entity, name));
        return entity;
    }

    private void initInput() {
        Entity board = entityEngine.getEntity("BOARD");
        Entity ball = entityEngine.getEntity("BALL");
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT:
                        board.getComponent(InputComponent.class).show().setLeftPressed(true);
                        break;
                    case RIGHT:
                        board.getComponent(InputComponent.class).show().setRightPressed(true);
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
                        board.getComponent(InputComponent.class).show().setLeftPressed(false);
                        break;
                    case RIGHT:
                        board.getComponent(InputComponent.class).show().setRightPressed(false);
                        break;
                }
            }
        });
    }
}
