package pedestrian_dynamics.models;

import pedestrian_dynamics.Parameters;
import pedestrian_dynamics.simulation.Simulation;

public enum Wall {
    TOP(new Vector(0, 1), Wall::topWallOverlap),
    BOTTOM(new Vector(0, -1), Wall::bottomWallOverlap),
    LEFT(new Vector(-1, 0), (parameters, particle) -> particle.getRadius() - particle.getX()),
    RIGHT(new Vector(1, 0), (parameters, particle) -> particle.getX() - parameters.getW() + particle.getRadius());

    private final Vector normalVector;
    private final OverlapCalculator overlapCalculator;

    Wall(Vector normalVector, OverlapCalculator overlapCalculator) {
        this.normalVector = normalVector;
        this.overlapCalculator = overlapCalculator;
    }

    public Vector getForce(Parameters parameters, Particle particle, double kn, double kt) {
        double overlap = overlapCalculator.calculateOverlap(parameters, particle);
        if (overlap < 0) {
            return Simulation.socialForce(parameters, normalVector, overlap);
        }
        return Simulation.getForce(
                normalVector,
                new Vector(-normalVector.getY(), normalVector.getX()),
                particle.getSpeed(),
                overlap,
                kn,
                kt);
    }

    public interface OverlapCalculator {
        double calculateOverlap(Parameters parameters, Particle particle);
    }

    private static double bottomWallOverlap(Parameters parameters, Particle particle) {
        double x = particle.getX();
        if (isInRange(x, parameters.getW() / 2, parameters.getD() / 2)
                || particle.getY() < 0) {
            return 0;
        }
        return -particle.getPosition().getY() + particle.getRadius();
    }

    private static double topWallOverlap(Parameters parameters, Particle particle) {
        // TODO: fill
        return 0;
    }

    private static boolean isInRange(double value, double center, double difference) {
        double min = center - difference;
        double max = center + difference;
        return value >= min && value <= max;
    }

}
