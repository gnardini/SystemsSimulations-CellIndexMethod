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
    public List<Double> speedsInFirstPeriod = new LinkedList<>();
    public List<Double> speedsInLastPeriod = new LinkedList<>();
    public List<Double> temperatures = new LinkedList<>();

    private int collissionsInSecond = 0;
    private int nextSecond = 1;

    private double totalTime = 0;

    private boolean storeInitialSpeeds;
    private boolean storeLastSpeeds;

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

        if (storeInitialSpeeds) {
            board.getParticles().forEach(particle -> speedsInFirstPeriod.add(particle.getSpeed()));
        }
        if (storeLastSpeeds) {
            board.getParticles().forEach(particle -> speedsInLastPeriod.add(particle.getSpeed()));
        }
        double totalTemperature = board.getParticles().stream()
                .mapToDouble(particle -> .5 * particle.getMass() * particle.getSpeed() * particle.getSpeed())
                .sum();
        temperatures.add(totalTemperature);
    }

    public void setStoringInitialSpeeds(boolean storingInitialSpeeds) {
        storeInitialSpeeds = storingInitialSpeeds;
    }

    public void setStoringLastSpeeds(boolean storingLastSpeeds) {
        storeLastSpeeds = storingLastSpeeds;
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

    public List<Double> getSpeedsInFirstPeriod() {
        return speedsInFirstPeriod;
    }

    public List<Double> getSpeedsInLastPeriod() {
        return speedsInLastPeriod;
    }

    public List<Double> getTemperatures() {
        return temperatures;
    }

}
