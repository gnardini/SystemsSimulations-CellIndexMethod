package brownian_motion;

import brownian_motion.model.BMBoard;
import brownian_motion.model.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BMStats {

    private final BMBoard board;

    private List<Point> bigParticlePath;

    public List<Integer> collissionsPerSecond = new LinkedList<>();
    public List<Double> timePerCollision = new LinkedList<>();

    private int collissionsInSecond = 0;
    private int nextSecond = 1;

    private double totalTime = 0;

    public BMStats(BMBoard board) {
        this.board = board;
        bigParticlePath = new LinkedList<>();
    }

    public void onCollision(double timeToCollide) {
        totalTime += timeToCollide;

        synchronized (timePerCollision) {
            timePerCollision.add(timeToCollide);
        }

        while (totalTime > nextSecond) {
            synchronized (collissionsPerSecond) {
                collissionsPerSecond.add(collissionsInSecond);
            }
            collissionsInSecond = 0;
            nextSecond++;
        }
        collissionsInSecond++;

        synchronized (bigParticlePath) {
            bigParticlePath.add(Point.forParticle(board.getBigParticle()));
        }
    }

    public double getCollisionsPerSecond() {
        if (collissionsPerSecond.isEmpty()) {
            return 0;
        }
        synchronized (collissionsPerSecond) {
            return collissionsPerSecond.stream().mapToInt(i -> i).average().getAsDouble();
        }
    }

    public double getAverageTimeToCollision() {
        if (timePerCollision.isEmpty()) {
            return 0;
        }
        synchronized (timePerCollision) {
            return timePerCollision.stream().mapToDouble(i -> i).average().getAsDouble();
        }
    }

    public List<Point> getBigParticlePath() {
        synchronized (bigParticlePath) {
            return new ArrayList<>(bigParticlePath);
        }
    }

}
