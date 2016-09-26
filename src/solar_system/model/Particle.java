package solar_system.model;

import java.awt.*;

public class Particle {

    private int id;
    private Vector position;
    private Vector speed;
    private double mass;
    private double radius;
    private Color color;

    private Vector oldPosition;

    private boolean isLaunched;

    public Particle(int id, Vector position, Vector speed, double mass, double radius, Color color) {
        this(id, position, speed, mass, radius, color, new Vector(0, 0), true);
    }

    public Particle(int id, Vector position, Vector speed, double mass, double radius, Color color, boolean hasLaunched) {
        this(id, position, speed, mass, radius, color, new Vector(0, 0), hasLaunched);
    }

    public Particle(int id, Vector position, Vector speed, double mass, double radius, Color color, Vector oldPosition,
                    boolean hasLaunched) {
        this.id = id;
        this.position = position;
        this.speed = speed;
        this.mass = mass;
        this.radius = radius;
        this.color = color;

        this.oldPosition = oldPosition;
        this.isLaunched = hasLaunched;
    }

    public int getId() {
        return id;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getSpeed() {
        return speed;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public Vector getOldPosition() {
        return oldPosition;
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public Particle launch() {
        return new Particle(id, position, speed, mass, radius, color, oldPosition, true);
    }

    public Particle withOldPosition(Vector oldPosition) {
        return new Particle(id, position, speed, mass, radius, color, oldPosition, isLaunched);
    }

    public Particle withNewData(Vector position, Vector speed) {
        return new Particle(id, position, speed, mass, radius, color, this.position, isLaunched);
    }

    public boolean collidesWith(Particle other) {
        return position.subtract(other.getPosition()).norm() < radius + other.radius;
    }

    @Override
    public String toString() {
        return "x: " + position.getX() + ", y: " + position.getY();
    }

}
