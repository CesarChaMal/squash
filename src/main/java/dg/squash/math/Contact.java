package dg.squash.math;

public class Contact {

    public void setE2(Vector2D velocity, double inverseMass, Vector2D position) {
        velocityE2 = velocity;
        physicsE2 = inverseMass;
        positionE2 = position;
    }

    public void setE1(Vector2D velocity, double inverseMass, Vector2D position) {
        velocityE1 = velocity;
        physicsE1 = inverseMass;
        positionE1 = position;
    }

    public void setPenetration(double penetration) {
        this.penetration = penetration;
    }

    public void setContactNormal(Vector2D contactNormal) {
        this.contactNormal = contactNormal;
        contactNormal.normalize();
    }

    private Vector2D velocityE1;
    private Vector2D velocityE2;
    private double physicsE1;
    private double physicsE2;
    private Vector2D positionE1;
    private Vector2D positionE2;
    private Vector2D contactNormal;
    private double penetration;

    public void resolve() {
        resolveVelocity();
        resolvePenetration();
    }

    public void resolvePenetration() {
        double percent = 0.4; // usually 20% to 80%
        double slop = 0.04;
        // Vector2D correction = Vector2D.mult(contactNormal, Math.max(penetration - slop, 0) / (physicsE1.getInverseMass() + physicsE2.getInverseMass()) * percent);

        //  positionE1.setPosition(Vector2D.subtract(positionE1.getPosition(), Vector2D.mult(correction, physicsE1.getInverseMass())));
        // positionE2.setPosition(Vector2D.add(positionE2.getPosition(), Vector2D.mult(correction, physicsE2.getInverseMass())));

    }

    public void resolveVelocity() {
        // Calculate relative velocity
        Vector2D rv = Vector2D.subtract(velocityE2, velocityE1);
        // Calculate relative velocity in terms of the normal direction
        double velAlongNormal = Vector2D.dotProduct(rv, contactNormal);
        // Do not resolve if velocities are separating
        if (velAlongNormal > 0)
            return;

        // Calculate restitution
        double e = 1;// (physicsE1.getElasticity() + physicsE2.getElasticity()) / 2;
        // Calculate impulse scalar
        double j = -(1 + e) * velAlongNormal;


        j /= Vector2D.dotProduct(contactNormal, contactNormal) * (physicsE1 + physicsE2);

        Vector2D impulse = Vector2D.mult(contactNormal, j);

        // Apply impulse
        velocityE1.set(Vector2D.subtract(velocityE1, Vector2D.mult(impulse, physicsE1)));
        velocityE2.set(Vector2D.add(velocityE2, Vector2D.mult(impulse, physicsE2)));
    }
}
