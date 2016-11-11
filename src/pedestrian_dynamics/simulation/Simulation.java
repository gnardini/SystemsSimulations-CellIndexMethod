package pedestrian_dynamics.simulation;

import com.sun.tools.javac.util.Pair;
import pedestrian_dynamics.Parameters;
import pedestrian_dynamics.models.Particle;
import pedestrian_dynamics.models.State;
import pedestrian_dynamics.models.Vector;
import pedestrian_dynamics.models.Wall;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Simulation {

    public static State updateState(State state, Parameters parameters) {
        CellIndexMethod cellIndexMethod = new CellIndexMethod();
        cellIndexMethod.calculateDistance(state);
        List<Particle> newParticles = new LinkedList<>();
        double deltaTime = parameters.getDeltaTime();
        for (Particle particle : state.getParticles()) {
            Pair<Vector, Double> forces = getForces(parameters, particle, parameters.getKn(), parameters.getKt());
            Vector force = forces.fst;

            Vector currentAcceleration = force.scale(1/particle.getMass());
            Vector newPosition = particle.getPosition().sum(particle.getSpeed().scale(deltaTime))
                    .sum(currentAcceleration.scale(2/3.0).scale(Math.pow(deltaTime, 2)))
                    .sub(particle.getAcceleration().scale(1/6.0).scale(Math.pow(deltaTime, 2)));
            Vector newSpeed = particle.getSpeed().sum(currentAcceleration.scale(deltaTime).scale(5/6.0))
                    .sub(particle.getAcceleration().scale(deltaTime).scale(1/6.0));

//            Vector newPosition = particle.getPosition().scale(2.0)
//                    .sub(particle.getOldPosition())
//                    .sum(force.scale(deltaTime * deltaTime / particle.getMass()));
//            Vector newSpeed = particle.getPosition().sub(particle.getOldPosition()).scale(1.0 / (2.0 * deltaTime));

            Particle newParticle = particle.withNewData(newPosition, newSpeed).withForce(forces.snd);
            if (newParticle.getY() > -1) {
                newParticles.add(newParticle);
            }
        }
        return state.withNewParticles(newParticles);
    }

    private static Pair<Vector, Double> getForces(Parameters parameters, Particle particle, double kn, double kt) {
        Vector totalForce = getTargetForce(particle, parameters);
        // System.out.println(totalForce);
        double totalForceModule = 0;
        for (Particle neighbor : particle.getNeighbours()) {
            double overlap = getOverlap(particle, neighbor);
            Vector normalVersor = getNormalVersor(particle, neighbor);
            if (overlap > 0) {
                Vector tangencialVersor = new Vector(-normalVersor.getY(), normalVersor.getX());

                Vector relativeSpeed = particle.getSpeed().sub(neighbor.getSpeed());

                Vector force = getForce(normalVersor, tangencialVersor, relativeSpeed, overlap, kn, kt);
                totalForce = totalForce.sum(force);
                totalForceModule += force.norm();
            } else {
                totalForce = totalForce.sum(socialForce(parameters, normalVersor, overlap));
            }
        }
        Vector wallForce = wallCollision(parameters, particle, kn, kt);
        totalForce = totalForce.sum(wallForce);
        totalForceModule += wallForce.norm();
        return new Pair<>(totalForce, totalForceModule);
    }

    private static Vector socialForce(Parameters parameters, Vector normalVersor, double overlap) {
        Vector socialForce = normalVersor.scale(parameters.getA() * Math.exp(overlap / parameters.getB()));
        return socialForce.scale(-1);
    }

    private static Vector getTargetForce(Particle particle, Parameters parameters) {
        Vector target = new Vector(getDestinationX(particle, parameters), getDestinationY(particle));

        Vector targetVersor = target.sub(particle.getPosition()).normalize();
        Vector targetForce = targetVersor.scale(parameters.getTargetSpeed()).sub(particle.getSpeed())
                .scale(particle.getMass() / parameters.getTao());
        return targetForce.scale(-1);
    }

    private static double getDestinationX(Particle particle, Parameters parameters) {
        if (particle.getY() < 0) {
            return particle.getX();
        }
        double x = particle.getX();
        double radius = particle.getRadius();

        double targetXLeft = (parameters.getW() - parameters.getD()) / 2;
        double targetXRight = parameters.getW() - targetXLeft;

        if (x <= targetXLeft + radius) {
            return targetXLeft + radius;
        } else if (x >= targetXRight - radius) {
            return targetXRight - radius;
        } else {
            return x;
        }
    }

    private static double getDestinationY(Particle particle) {
        return particle.getY() <= 0 ? -1 : 0;
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

    private static Vector wallCollision(Parameters parameters, Particle particle, double kn, double kt) {
        return Stream.of(Wall.values())
                .map(wall -> wall.getForce(parameters, particle, kn, kt))
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
