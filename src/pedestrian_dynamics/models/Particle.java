package pedestrian_dynamics.models;

import pedestrian_dynamics.simulation.PoliceStop;
import pedestrian_dynamics.Parameters;

import java.awt.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Particle {

    private final int id;
    private final Vector position;
    private final Vector speed;
    private final Vector acceleration;
    private final double radius;
    private final double mass;

    private final Vector oldPosition;
    private final double force;
    private final Set<Particle> neighbours;
    private final List<PoliceStop> policeStops;
    private final boolean isStatic;

    private Parameters parameters;

    public Particle(Parameters parameters, int id, double radius, Vector position, Vector speed, Vector acceleration,
                    Vector oldPosition, double force, boolean isStatic, List<PoliceStop> policeStops) {
        this(parameters, id, radius, position, speed, acceleration, oldPosition, force, new HashSet<>(), isStatic,
                policeStops);
    }

    public Particle(Parameters parameters, int id, double radius, Vector position, Vector speed, Vector acceleration,
                    Vector oldPosition, double force, Set<Particle> neighbours, boolean isStatic,
                    List<PoliceStop> policeStops) {
        this.parameters = parameters;
        this.id = id;
        this.radius = radius;
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
        this.mass = parameters.getMass();
        this.oldPosition = oldPosition;
        this.force = force;
        this.neighbours = neighbours;
        this.isStatic = isStatic;
        this.policeStops = policeStops;
    }

    public int getId() {
        return id;
    }

    public Vector getPosition() {
        return position;
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public Vector getSpeed() {
        return speed;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    public Vector getOldPosition() {
        return oldPosition;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void addNeighbour(Particle particle) {
        neighbours.add(particle);
    }

    public Color getColor() {
        int color = (int) (speed.norm() * 255 / parameters.getTargetSpeed());
        if (color > 255) {
            color = 255;
        }
        return new Color(color, 0, 0);
    }

    public boolean isInRadius(Particle otherParticle, double interactionRadius) {
        double xDiff = Math.abs(getX() - otherParticle.getX());
        double yDiff = Math.abs(getY() - otherParticle.getY());
        double totalDiff = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        totalDiff -= radius;
        totalDiff -= otherParticle.radius;
        return totalDiff <= interactionRadius;
    }

    public boolean checkPoliceControls() {
        return policeStops.stream()
                .map(policeStop -> policeStop.shouldStop(position))
                .reduce(false, (b1, b2) -> b1 || b2);
    }

    public boolean checkPoliceControlFinished() {
        return policeStops.stream()
                .map(policeStop -> policeStop.shouldContinue(parameters.getDeltaTime()))
                .reduce(false, (b1, b2) -> b1 || b2);
    }

    public Particle withPositions(int id, double x, double y, double radius) {
        return new Particle(
                parameters, id, radius, new Vector(x, y), speed, acceleration, position, force, isStatic, policeStops);
    }

    public Particle withNewData(Vector newPosition, Vector newSpeed) {
        return new Particle(
                parameters, id, radius, newPosition, newSpeed, acceleration, position, force, isStatic, policeStops);
    }

    public Particle withForce(double force) {
        return new Particle(
                parameters, id, radius, position, speed, acceleration, oldPosition, force, isStatic, policeStops);
    }

    public Particle withNewRadius(double newRadius) {
        return new Particle(
                parameters, id, newRadius, position, speed, acceleration, oldPosition, force, isStatic, policeStops);
    }

    public Particle beingStatic(boolean isStatic) {
        return new Particle(
                parameters, id, radius, position, speed, acceleration, oldPosition, force, isStatic, policeStops);
    }

    public Particle calculatingOldPosition(double deltaTime) {
        Vector calculatedOldPosition = position
                .sum(speed.scale(-deltaTime))
                .sum(acceleration.scale(deltaTime * deltaTime / 2 * mass));
        return new Particle(parameters, id,
                radius, position, speed, acceleration, calculatedOldPosition, force, isStatic, policeStops);
    }

    @Override
    public Particle clone() {
        return new Particle(
                parameters, id, radius, position, speed, acceleration, oldPosition, force, isStatic, policeStops);
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
