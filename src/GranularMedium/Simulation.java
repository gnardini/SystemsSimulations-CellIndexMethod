package GranularMedium;

import GranularMedium.models.Particle;
import GranularMedium.models.State;
import GranularMedium.models.Vector;
import GranularMedium.models.Wall;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Simulation {

    public static State simulateStep(State state, Parameters parameters, double deltaTime) {
        CellIndexMethod cellIndexMethod = new CellIndexMethod();
        cellIndexMethod.calculateDistance(state);
        List<Particle> newParticles = new LinkedList<>();
        for (Particle particle : state.getParticles()) {
            Vector force = getForces(particle, parameters.getKn(), parameters.getKt());
            Vector newPosition = particle.getPosition().scale(2.0)
                    .sub(particle.getOldPosition())
                    .sum(force.scale(deltaTime * deltaTime / particle.getMass()));
            Vector newSpeed = particle.getPosition().sub(particle.getOldPosition()).scale(1.0 / (2.0 * deltaTime));
            newParticles.add(particle.withNewData(newPosition, newSpeed));
        }
        return state.withNewParticles(newParticles);
    }

    private static Vector getForces(Particle particle, double kn, double kt) {
        Vector totalForce = particle.getAcceleration().scale(particle.getMass());
        for (Particle neighbor : particle.getNeighbours()) {
            double overlap = getOverlap(particle, neighbor);

            if (overlap > 0) {
                Vector normalVersor = getNormalVersor(particle, neighbor);
                Vector tangencialVersor = new Vector(-normalVersor.getY(), normalVersor.getX());

                Vector relativeSpeed = particle.getSpeed().sub(neighbor.getSpeed());

                Vector force = getForce(normalVersor, tangencialVersor, relativeSpeed, overlap, kn, kt);
                totalForce = totalForce.sum(force);
            }
        }
        totalForce = totalForce.sum(wallCollision(particle, kn, kt));
        return totalForce;
    }

    private static Vector getNormalVersor(Particle particle, Particle neighbour) {
        Vector vector = neighbour.getPosition().sub(particle.getPosition());
        return vector.normalize();
    }

    public static Vector getForce(Vector normalVersor, Vector tangencialVersor, Vector relativeVelocity,
                                   double overlap, double kn, double kt) {
        Vector normalForce = normalVersor.scale(-1 * kn * overlap);
        double prod = tangencialVersor.scalarProduct(relativeVelocity);
        Vector tangencialForce = tangencialVersor.scale(-1 * kt * overlap * prod);
        return normalForce.sum(tangencialForce);
    }

    private static Vector wallCollision(Particle particle, double kn, double kt) {
        return Stream.of(Wall.values())
                .map(wall -> wall.getForce(particle, kn, kt))
                .reduce(Vector.ZERO, Vector::sum);
    }

    private static double getOverlap(Particle particle, Particle neighbour) {
        if (particle.getId() == neighbour.getId()) {
            return 0;
        }
        return particle.getRadius() + neighbour.getRadius()
                - neighbour.getPosition().sub(particle.getPosition()).norm();
    }

}
