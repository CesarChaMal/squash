package dg.squash.shape;


import dg.squash.math.Vector2D;

/**
 * Defines shape.
 */
public interface Shape {

    /**
     * @return position of a shape
     */
    Vector2D getPosition();

    /**
     * @param position of a shape
     */
    void setPosition(Vector2D position);
}
