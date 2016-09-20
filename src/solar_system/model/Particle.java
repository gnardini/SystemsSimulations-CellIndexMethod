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

    public Particle(int id, Vector position, Vector speed, double mass, double radius, Color color) {
        this(id, position, speed, mass, radius, color, new Vector(0, 0));
    }

    public Particle(int id, Vector position, Vector speed, double mass, double radius, Color color, Vector oldPosition) {
        this.id = id;
        this.position = position;
        this.speed = speed;
        this.mass = mass;
        this.radius = radius;
        this.color = color;

        this.oldPosition = oldPosition;
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

    public Particle withOldPosition(Vector oldPosition) {
        return new Particle(id, position, speed, mass, radius, color, oldPosition);
    }

    public Particle withNewData(Vector position, Vector speed) {
        return new Particle(id, position, speed, mass, radius, color, this.position);
    }

    @Override
    public String toString() {
        return "x: " + position.getX() + ", y: " + position.getY() + ", radius: " + radius;
    }
}
