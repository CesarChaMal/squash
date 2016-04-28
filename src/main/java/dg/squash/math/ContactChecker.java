package dg.squash.math;

import dg.squash.ecs.Entity;
import dg.squash.ecs.components.MassComponent;
import dg.squash.ecs.components.ShapeComponent;
import dg.squash.ecs.components.VelocityComponent;
import dg.squash.exceptions.NoSuchComponentException;
import dg.squash.shape.CircleShape;
import dg.squash.shape.LineShape;
import dg.squash.shape.RectangleShape;
import dg.squash.shape.Shape;

import java.util.HashMap;
import java.util.Map;

public class ContactChecker {

    private static Intersector intersector = new Intersector();

    private static Entity e1, e2;

    private ContactChecker() {

    }

    public static Contact getContact(Entity en1, Entity en2) {

        e1 = en1;
        e2 = en2;

        if (!e1.hasComponent(ShapeComponent.class) || !e2.hasComponent(ShapeComponent.class))
            throw new NoSuchComponentException(ShapeComponent.class);

        Shape shapeE1 = e1.getComponent(ShapeComponent.class).show();
        Shape shapeE2 = e2.getComponent(ShapeComponent.class).show();

        if (shapeE1 instanceof CircleShape && shapeE2 instanceof CircleShape) {

            if (intersector.circleXCircle((CircleShape) shapeE1, (CircleShape) shapeE2)) {
                return circleXCircle();
            }
        } else if (shapeE1 instanceof CircleShape && shapeE2 instanceof RectangleShape) {
            if (intersector.circleXOrientedRectangle((CircleShape) shapeE1, (RectangleShape) shapeE2)) {
                return circleXRectangle((CircleShape) shapeE1, (RectangleShape) shapeE2);
            }
        } else if (shapeE1 instanceof RectangleShape && shapeE2 instanceof CircleShape) {
            if (intersector.circleXOrientedRectangle((CircleShape) shapeE2, (RectangleShape) shapeE1)) {
                return circleXRectangle((CircleShape) shapeE2, (RectangleShape) shapeE1);
            }
        } else if (shapeE1 instanceof RectangleShape && shapeE2 instanceof RectangleShape) {
            if (intersector.orientedRectangleXOrientedRectangle((RectangleShape) shapeE2, (RectangleShape) shapeE1)) {
                return rectangleXRectangle();
            }
        }

        return null;
    }

    private static Contact rectangleXRectangle() {
        Contact contact = new Contact();
        contact.setE1(e1.getComponent(VelocityComponent.class).show(), e1.getComponent(MassComponent.class).showInverseMass(),
                e1.getComponent(ShapeComponent.class).getPosition());
        contact.setE2(e2.getComponent(VelocityComponent.class).show(), e2.getComponent(MassComponent.class).showInverseMass(),
                e2.getComponent(ShapeComponent.class).getPosition());

        // Calculate half sizes.
        RectangleShape rectA = (RectangleShape) e1.getComponent(ShapeComponent.class).show();
        RectangleShape rectB = (RectangleShape) e2.getComponent(ShapeComponent.class).show();
        double halfWidthA = rectA.getWidth() / 2.0;
        double halfHeightA = rectA.getHeight() / 2.0;
        double halfWidthB = rectB.getWidth() / 2.0;
        double halfHeightB = rectB.getHeight() / 2.0;

        // Calculate centers.
        Vector2D centerA = rectA.getCenter();
        Vector2D centerB = rectB.getCenter();

        // Calculate current and minimum-non-intersecting distances between centers.
        double distanceX = centerA.getX() - centerB.getX();
        double distanceY = centerA.getY() - centerB.getY();
        double minDistanceX = halfWidthA + halfWidthB;
        double minDistanceY = halfHeightA + halfHeightB;


        // Calculate and return intersection depths.
        double depthX = distanceX > 0 ? minDistanceX - distanceX : -minDistanceX - distanceX;
        double depthY = distanceY > 0 ? minDistanceY - distanceY : -minDistanceY - distanceY;

        Vector2D intersection = new Vector2D(depthX, depthY);
        contact.setContactNormal(Vector2D.norm(intersection));
        contact.setPenetration(intersection.mag());

        return contact;
    }

    private static Contact circleXCircle() {

        Shape shapeE1 = e1.getComponent(ShapeComponent.class).show();
        Shape shapeE2 = e2.getComponent(ShapeComponent.class).show();

        Vector2D positionE1 = shapeE1.getPosition();
        Vector2D positionE2 = shapeE2.getPosition();

        double length = Vector2D.subtract(positionE1, positionE2).mag();
        Contact contact = new Contact();
        contact.setE1(e1.getComponent(VelocityComponent.class).show(), e1.getComponent(MassComponent.class).showInverseMass(),
                e1.getComponent(ShapeComponent.class).getPosition());
        contact.setE2(e2.getComponent(VelocityComponent.class).show(), e2.getComponent(MassComponent.class).showInverseMass(),
                e2.getComponent(ShapeComponent.class).getPosition());

        Vector2D normal = Vector2D.subtract(positionE2, positionE1);
        normal.normalize();

        contact.setContactNormal(normal);
        contact.setPenetration(length - (((CircleShape) shapeE2).getRadius() + ((CircleShape) shapeE1).getRadius()));

        return contact;
    }

    private static Contact circleXRectangle(CircleShape c, RectangleShape r) {
        Contact contact = new Contact();
        contact.setE1(e1.getComponent(VelocityComponent.class).show(), e1.getComponent(MassComponent.class).showInverseMass(),
                e1.getComponent(ShapeComponent.class).getPosition());
        contact.setE2(e2.getComponent(VelocityComponent.class).show(), e2.getComponent(MassComponent.class).showInverseMass(),
                e2.getComponent(ShapeComponent.class).getPosition());

        getCorners(r);
        boolean isAboveAC = isOnUpperSideOfLine(corners.get("bottomRight"), corners.get("topLeft"), c.getPosition());
        boolean isAboveDB = isOnUpperSideOfLine(corners.get("topRight"), corners.get("bottomLeft"), c.getPosition());

        if (isAboveAC) {
            if (isAboveDB) {
                // top edge has intersected
                contact.setContactNormal(Vector2D.rotate(r.getTopEdge().getDirection(), -90));
                setPenetration(r.getTopEdge(), c, contact);
            } else {
                //right edge intersected
                contact.setContactNormal(Vector2D.rotate(r.getRightEdge().getDirection(), -90));
                setPenetration(r.getRightEdge(), c, contact);
            }
        } else {
            if (isAboveDB) {
                //left edge has intersected
                contact.setContactNormal(Vector2D.rotate(r.getLeftEdge().getDirection(), -90));
                setPenetration(r.getLeftEdge(), c, contact);

            } else {
                //bottom edge intersected
                contact.setContactNormal(Vector2D.rotate(r.getBottomEdge().getDirection(), -90));
                setPenetration(r.getBottomEdge(), c, contact);
            }
        }
        return contact;
    }

    private static void setPenetration(LineShape edge, CircleShape c, Contact contact) {
        Vector2D d = edge.getDirection();
        Vector2D lc = Vector2D.subtract(c.getPosition(), edge.getPosition());
        Vector2D p = Vector2D.projection(lc, d);
        Vector2D nearest = Vector2D.add(edge.getPosition(), p);
        Vector2D dis = Vector2D.subtract(c.getPosition(), nearest);
        contact.setPenetration(c.getRadius() - dis.mag());
    }

    private static boolean isOnUpperSideOfLine(Vector2D corner1, Vector2D oppositeCorner, Vector2D ballCenter) {
        return ((oppositeCorner.getX() - corner1.getX()) * (ballCenter.getY()
                - corner1.getY()) - (oppositeCorner.getY() - corner1.getY()) * (ballCenter.getX() - corner1.getX())) > 0;
    }

    private static Map<String, Vector2D> corners = new HashMap<>();

    private static void getCorners(RectangleShape boxToGetFrom) {
        corners.clear();
        Vector2D tl = new Vector2D(boxToGetFrom.getPosition().getX(), boxToGetFrom.getPosition().getY());
        Vector2D tr = new Vector2D(boxToGetFrom.getPosition().getX() + boxToGetFrom.getWidth(), boxToGetFrom.getPosition().getY());
        Vector2D br = new Vector2D(boxToGetFrom.getPosition().getX() + boxToGetFrom.getWidth(), boxToGetFrom.getPosition().getY() + boxToGetFrom.getHeight());
        Vector2D bl = new Vector2D(boxToGetFrom.getPosition().getX(), boxToGetFrom.getPosition().getY() + boxToGetFrom.getHeight());
        corners.put("topLeft", tl);
        corners.put("topRight", tr);
        corners.put("bottomRight", br);
        corners.put("bottomLeft", bl);
    }
}
