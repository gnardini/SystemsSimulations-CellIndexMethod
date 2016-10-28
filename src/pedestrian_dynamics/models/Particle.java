package pedestrian_dynamics.models;

import pedestrian_dynamics.Parameters;

import java.awt.*;

public class Particle {
    private int id;
    private Vector position;
    private Vector speed;
    private Vector acceleration;
    private double radius;
    private double mass;
    private Color color;

    public Particle(Parameters parameters, int id, double radius, Vector position, Vector speed, Vector acceleration) {
        this.id = id;
        this.radius = radius;
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
        this.mass = parameters.getMass();
        this.color = Color.RED;
    }

    public Vector getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                ", position=" + position +
                ", radius=" + radius +
                '}';
    }
}
