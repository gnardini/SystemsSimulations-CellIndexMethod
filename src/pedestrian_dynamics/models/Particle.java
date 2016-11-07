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

    private Parameters parameters;

    public Particle(Parameters parameters, int id, double radius, Vector position, Vector speed, Vector acceleration) {
        this.parameters = parameters;
        this.id = id;
        this.radius = radius;
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
        this.mass = parameters.getMass();
    }

    public Vector getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public Color getColor() {
        int color = (int) (speed.norm() * 255 / parameters.getDesiredSpeed());
        if (color > 255) {
            color = 255;
        }
        return new Color(color, 0, 0);
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
