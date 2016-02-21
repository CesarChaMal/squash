package dg.squash.ecs.systems;

import dg.squash.ecs.components.HealthComponent;
import dg.squash.ecs.components.ScoreComponent;
import dg.squash.ecs.components.ShapeComponent;
import dg.squash.events.HealthListener;
import dg.squash.events.ScoreListener;
import dg.squash.main.AnimationCreator;
import dg.squash.main.AssetManager;
import dg.squash.main.Game;
import dg.squash.math.Vector2D;
import dg.squash.ecs.components.NodeComponent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class RenderLaw implements HealthListener, ScoreListener {

    private Game game;

    public RenderLaw(Game game) {
        this.game = game;
    }

    @Override
    public void update(HealthComponent component) {
        int health = component.show();
        Rectangle rectangle = (Rectangle) component.belongsTo().getComponent(NodeComponent.class).show();
        AnimationCreator.shakeAnimation(rectangle).play();
        switch (health) {
            case 0:
                AnimationCreator.blowUpAnimation(component.belongsTo(), game).play();
                break;
            case 1:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_CRYING));
                break;
            case 2:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_SAD));
                break;
            case 3:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_STUPID));
                break;
            case 4:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_ARE_YOU_JOKING));
                break;
            case 5:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_LAUGHING));
                break;
        }
    }

    @Override
    public void update(ScoreComponent component) {
        Vector2D position = component.belongsTo().getComponent(ShapeComponent.class).show().getPosition();
        SimpleIntegerProperty scoreText = component.getScoreProperty();
        Group group = (Group) component.belongsTo().getComponent(NodeComponent.class).show().getParent();
        AnimationCreator.scoreAnimation(position, group, scoreText).play();
    }


}
