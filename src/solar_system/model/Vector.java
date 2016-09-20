package solar_system.model;

public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector scale(double factor) {
        return new Vector(x * factor, y * factor);
    }

    public Vector add(Vector other) {
        return new Vector(x + other.getX(), y + other.getY());
    }

    public Vector subtract(Vector other) {
        return new Vector(x - other.getX(), y - other.getY());
    }

    public Vector normalize() {
        return scale(1 / (norm()));
    }

    public double norm() {
        return Math.sqrt(x * x + y * y);
    }

    public double distanceTo(Vector other) {
        return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y));
    }

}
