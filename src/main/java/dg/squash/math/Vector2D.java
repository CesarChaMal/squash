package dg.squash.math;

public class Vector2D {

    public static final Vector2D ZERO = new Vector2D(0, 0);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double x;
    private double y;

    public void set(Vector2D vector2D) {
        this.x = vector2D.getX();
        this.y = vector2D.getY();
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void reset() {
        this.x = 0;
        this.y = 0;
    }

    public void multiply(double s) {
        this.x *= s;
        this.y *= s;
    }

    public static Vector2D rotate(Vector2D v, double degrees) {
        double radian = Math.toRadians(degrees);
        double sine = Math.sin(radian);
        double cosine = Math.cos(radian);

        double x = v.getX() * cosine - v.getY() * sine;
        double y = v.getX() * sine + v.getY() * cosine;
        return new Vector2D(x, y);
    }

    public static Vector2D rotate90(Vector2D v) {
        return new Vector2D(v.getY() * -1, v.getX());
    }

    public static boolean isParallel(Vector2D a, Vector2D b) {
        Vector2D na = rotate90(a);
        return equalDouble(0, dotProduct(na, b));
    }

    public void invert() {
        multiply(-1);
    }

    public static boolean equalDouble(double a, double b) {
        double threshold = 1.0 / 8192.0;
        return Math.abs(a - b) < threshold;
    }

    public void invertY() {
        this.y *= -1;
    }

    public void invertX() {
        this.x *= -1;
    }

    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    public double sqrdMag() {
        return x * x + y * y;
    }

    public void normalize() {
        Vector2D v = norm(this);
        this.x = v.getX();
        this.y = v.getY();
    }


    public void add(Vector2D v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    public void subtract(Vector2D v) {
        this.x -= v.getX();
        this.y -= v.getY();
    }

    public static Vector2D projection(Vector2D project, Vector2D onto) {
        double d = dotProduct(onto, onto);
        if (0 < d) {
            double dp = dotProduct(project, onto);
            return mult(onto, dp / d);
        }
        return onto;
    }

    public static double angle(Vector2D v1, Vector2D v2) {
        Vector2D v1n = Vector2D.norm(v1);
        Vector2D v2n = Vector2D.norm(v2);
        return Math.toDegrees(Math.acos(dotProduct(v1n, v2n)));
    }

    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    public static double dotProduct(Vector2D v1, Vector2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public static Vector2D norm(Vector2D v) {
        return div(v, v.mag());
    }

    public static Vector2D div(Vector2D v, double s) {
        if (s != 0)
            return new Vector2D(v.getX() / s, v.getY() / s);
        return ZERO;
    }

    public static Vector2D mult(Vector2D v, double s) {
        return new Vector2D(v.getX() * s, v.getY() * s);
    }

    public static boolean isEqual(Vector2D v1, Vector2D v2) {
        Vector2D n1 = norm(v1);
        Vector2D n2 = norm(v2);
        return equalDouble(dotProduct(n1, n2), 1.0) && equalDouble(v1.mag(), v2.mag());
    }

    @Override
    public String toString() {
        return String.format("(%.1f, %.1f)", x, y);
    }
}
