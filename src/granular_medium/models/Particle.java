package granular_medium.models;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Particle {

    private final int id;
    private final Vector position;
    private final Vector speed;
    private final Vector force;
    private final Vector acceleration;
    private final double radius;
    private final double mass;
    private final Color color;

    private final Vector oldPosition;
    private final Set<Particle> neighbours;

    public Particle(int id, Vector position, Vector speed, Vector acceleration, double radius, Color color,
                    double mass) {
        this(id, position, speed, acceleration, radius, color, mass, new HashSet<>());
    }

    public Particle(int id, Vector position, Vector speed, Vector acceleration, double radius, Color color,
                    double mass, Vector oldPosition) {
        this(id, position, speed, acceleration, radius, color, mass, new HashSet<>(), oldPosition, Vector.ZERO);
    }

    public Particle(int id, Vector position, Vector speed, Vector acceleration, double radius, Color color,
                    double mass, Vector oldPosition, Vector force) {
        this(id, position, speed, acceleration, radius, color, mass, new HashSet<>(), oldPosition, force);
    }

    public Particle(int id, Vector position, Vector speed, Vector acceleration, double radius, Color color,
                    double mass, Set<Particle> neighbours) {
        this(id, position, speed, acceleration, radius, color, mass, neighbours, Vector.ZERO, Vector.ZERO);
    }

    public Particle(int id, Vector position, Vector speed, Vector acceleration, double radius, Color color,
                    double mass, Set<Particle> neighbours, Vector oldPosition, Vector force) {
        this.id = id;
        this.position = position;
        this.speed = speed;
        this.force = force;
        this.acceleration = acceleration;
        this.radius = radius;
        this.color = color;
        this.mass = mass;
        this.neighbours = neighbours;
        this.oldPosition = oldPosition;
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

    public int getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public Color getColor() {
        double multiplier = Math.min(1, Math.max(0, 1 - getPreassure() / 150));
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return new Color(
                (int) (red * multiplier),
                (int) (green * multiplier),
                (int) (blue * multiplier));
    }

    public Vector getSpeed() {
        return speed;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public double getMass() {
        return mass;
    }

    public double getPreassure() {
        return force.norm();
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    public Vector getOldPosition() {
        return oldPosition;
    }

    public boolean isInRadius(Particle otherParticle, int interactionRadius) {
        double xDiff = Math.abs(getX() - otherParticle.getX());
        double yDiff = Math.abs(getY() - otherParticle.getY());
        double totalDiff = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        totalDiff -= radius;
        totalDiff -= otherParticle.radius;
        return totalDiff <= interactionRadius;
    }

    public boolean isNeighbourOf(int particleId) {
        for (Particle neighbour : neighbours) {
            if (neighbour.id == particleId) {
                return true;
            }
        }
        return false;
    }

    public void addNeighbour(Particle particle) {
        neighbours.add(particle);
    }

    public void addNeighbours(Set<Particle> particles) {
        neighbours.addAll(particles);
    }

    public void clearNeighbours() {
        neighbours.clear();
    }

    public String getTwoDecimalRadius() {
        return String.format("%.02f", radius);
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((Particle) obj).getId();
    }

    @Override
    public String toString() {
        return "id: " + id + ", x: " + getX() + ", y: " + getY() + ", radius: " + radius;
    }

    public String toStringWithNeighbours() {
        StringBuilder ans = new StringBuilder();
        ans.append("[ " + id);
        ans.append(": ");
        for (Particle particle: neighbours) {
            ans.append(particle.getId() + " ");
        }
        ans.append("]");
        return ans.toString();
    }

    public Particle clone() {
        Particle particle = new Particle(id, position, speed, acceleration, radius, color, mass, oldPosition);
        neighbours.forEach(particle::addNeighbour);
        return particle;
    }

    public Particle withNewData(Vector newPosition, Vector newSpeed) {
        return new Particle(id, newPosition, newSpeed, acceleration, radius, color, mass, position);
    }

    public Particle withPositions(int id, double x, double y, double radius) {
        return new Particle(id, new Vector(x, y), speed, acceleration, radius, color, mass, position);
    }

    public Particle withForce(Vector force) {
        return new Particle(id, position, speed, acceleration, radius, color, mass, oldPosition, force);
    }

    public Particle calculatingOldPosition(double deltaTime) {
        Vector calculatedOldPosition = position
                .sum(speed.scale(-deltaTime))
                .sum(acceleration.scale(deltaTime * deltaTime / 2 * mass));
        return new Particle(id, position, speed, acceleration, radius, color, mass, calculatedOldPosition);
    }

}
