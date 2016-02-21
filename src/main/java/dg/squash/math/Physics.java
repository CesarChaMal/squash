package dg.squash.math;

import dg.squash.shape.Shape;

public class Physics {

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    private Vector2D velocity;
    private Shape shape;
    private double mass;

    public Physics(Vector2D velocity, Shape shape, double mass) {
        this.velocity = velocity;
        this.shape = shape;
        this.mass = mass;
    }

    public Physics(Shape shape) {
        this(new Vector2D(0, 0), shape, Double.POSITIVE_INFINITY);
    }
    public double getInverseMass(){
        return 1.0/ mass;
    }
}
