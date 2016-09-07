package models;

import com.sun.tools.javac.util.Pair;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Particle {
    private int id;
    private double x, y;
    private double radius;
    private double mass;
    private double color;
    private double speed = 0.03;
    private double angle;

    private Set<Particle> neighbours;

    public Particle(int id, double x, double y, double radius, double color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.neighbours = new HashSet<>();
    }

    public Particle(int id, double x, double y, double radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbours = new HashSet<>();
    }

    public Particle(int id, double x, double y, double radius, double speed, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbours = new HashSet<>();

        this.speed = speed;
        this.angle = angle;
    }

    public Particle(int id, double x, double y, double radius, double speed, double angle, double mass) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.neighbours = new HashSet<>();

        this.speed = speed;
        this.angle = angle;
        this.mass = mass;
    }

    public static Particle random(int id, double boardSize) {
        Pair<Double, Double> coordinates = randomPoint(boardSize);
        return new Particle(id, coordinates.fst, coordinates.snd, 0);
    }

    public static Particle random(int id, double boardSize, double radius, double speed) {
        Pair<Double, Double> coordinates = randomPoint(boardSize);
        return new Particle(id, coordinates.fst, coordinates.snd, radius, speed, Math.random() * 2 * Math.PI);
    }

    private static Pair<Double, Double> randomPoint(double boardSize) {
        double randomX = 0, randomY = 0;
        while (randomX == 0 || randomY == 0) {
            randomX = Math.random();
            randomY = Math.random();
        }
        double x = (randomX * boardSize);
        double y = (randomY * boardSize);
        return new Pair<>(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getColor() {
        return color;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getSpeed() {
        return speed;
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isInRadius(Particle otherParticle, int interactionRadius) {
        double xDiff = Math.abs(x - otherParticle.getX());
        double yDiff = Math.abs(y - otherParticle.getY());
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

    public void move(int boardSize) {
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
        if (x >= boardSize - 0.01) {
            x -= boardSize;
        } else if (x < 0) {
            x += boardSize;
        }
        if (y >= boardSize - 0.01) {
            y -= boardSize;
        } else if (y < 0) {
            y += boardSize;
        }
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
        return "id: " + id + ", x: " + x + ", y: " + y + ", radius: " + radius;
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
        Particle particle = new Particle(id, x, y, radius, speed, angle);
        neighbours.forEach(particle::addNeighbour);
        return particle;
    }

    public Color getColorForAngle() {
        if (angle < Math.PI * .5f) return Color.red;
        if (angle < Math.PI) return Color.green.darker();
        if (angle < Math.PI * 1.5f) return Color.cyan.darker();
        return Color.magenta;
    }

}
