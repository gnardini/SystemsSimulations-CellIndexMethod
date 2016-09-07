package brownian_motion.model;

import com.sun.tools.javac.util.Pair;

public class BMParticle {

    private int id;
    private double x, y;
    private double radius;
    private double mass;
    private double xSpeed;
    private double ySpeed;

    public BMParticle(int id, double x, double y, double radius, double mass, double xSpeed, double ySpeed) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public boolean collidesWith(BMParticle particle) {
        double xDiff = Math.abs(x - particle.getX());
        double yDiff = Math.abs(y - particle.getY());
        double totalDiff = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        totalDiff -= radius;
        totalDiff -= particle.radius;
        return totalDiff <= 0;
    }

    public boolean isInBoard(double boardSize) {
        if (x - radius <= 0 || x + radius >= boardSize) {
            return false;
        }
        if (y - radius <= 0 || y + radius >= boardSize) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void invertXSpeed() {
        xSpeed *= -1;
    }

    public void invertYSpeed() {
        ySpeed *= -1;
    }

    public static BMParticle random(int id, double boardSize, double radius, double mass, double maxAxisAbsValue) {
        Pair<Double, Double> coordinates = randomPoint(boardSize);
        double xSpeed = (2 * Math.random() - 1) * maxAxisAbsValue;
        double ySpeed = (2 * Math.random() - 1) * maxAxisAbsValue;
        return new BMParticle(id, coordinates.fst, coordinates.snd, radius, mass, xSpeed, ySpeed);
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

    public void advance(double timeToCrash) {
        x += xSpeed * timeToCrash;
        y += ySpeed * timeToCrash;
    }

    public void modifySpeed(double xDiff, double yDiff) {
        xSpeed += xDiff;
        ySpeed += yDiff;
    }
}
