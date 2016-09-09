package brownian_motion.model;

import brownian_motion.BMParameters;
import com.sun.tools.javac.util.Pair;

import java.awt.*;

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

    public Color getColor() {
      double speed = calculateColor(Math.sqrt(Math.pow(xSpeed, 2) + Math.pow(ySpeed, 2)));
      if (id == BMParameters.N) {
        return new Color((int) speed, 0, 0);
      }
      return new Color(0, (int) speed, (int) speed);
    }

    private int calculateColor(final double speed) {
      // Assuming max speeds
      final double maxSpeed = 0.02;
      final double minSpeed = -0.02;
      final int minColor = 0;
      final int maxColor = 255;

      // Use a linear scale
      int scaledSpeed = (int) (((maxColor - minColor) * (Math.abs(speed) - minSpeed)) / (maxSpeed - minSpeed)) + minColor;

      if (scaledSpeed > 255) { return 255; }
      if (scaledSpeed < 0) { return 0; }
      return scaledSpeed;
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
