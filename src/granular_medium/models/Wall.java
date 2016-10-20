package granular_medium.models;

import granular_medium.Parameters;
import granular_medium.Simulation;

public enum Wall {
    //TOP(new Vector(0, 1), particle -> 1 + particle.getRadius()),
    BOTTOM(new Vector(0, -1), particle -> -particle.getPosition().getY() + particle.getRadius()),
    LEFT(new Vector(-1, 0), particle -> particle.getRadius() - particle.getX()),
    RIGHT(new Vector(1, 0), particle -> particle.getX() - Parameters.W + particle.getRadius());

    private final Vector normalVector;
    private final OverlapCalculator overlapCalculator;

    Wall(Vector normalVector, OverlapCalculator overlapCalculator) {
        this.normalVector = normalVector;
        this.overlapCalculator = overlapCalculator;
    }

    public Vector getForce(Particle particle, double kn, double kt) {
        double overlap = overlapCalculator.calculateOverlap(particle);
        if (overlap < 0) {
            return Vector.ZERO;
        }
        return Simulation.getForce(
                normalVector,
                new Vector(-normalVector.getY(), normalVector.getX()),
                particle.getSpeed(),
                overlap,
                kn,
                kt);
    }

    interface OverlapCalculator {
        double calculateOverlap(Particle particle);
    }

}
