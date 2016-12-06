package pedestrian_dynamics.simulation;

import com.sun.tools.javac.util.Pair;
import pedestrian_dynamics.Parameters;
import pedestrian_dynamics.models.*;

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
            Particle newParticle;
            if (particle.isStatic()) {
                newParticle = particle.clone();
            } else {
                Pair<Vector, Double> forces =
                        getForces(parameters, state.getBoard().getHorizontalWalls(), particle, parameters.getKn(), parameters.getKt(), state.getStaticParticles());
                Vector force = forces.fst.scale(1);
                Vector newPosition = particle.getPosition().scale(2.0)
                        .sub(particle.getOldPosition())
                        .sum(force.scale(deltaTime * deltaTime / particle.getMass()));
                Vector newSpeed = particle.getPosition().sub(particle.getOldPosition()).scale(1.0 / (2.0 * deltaTime));
                newParticle = particle.withNewData(newPosition, newSpeed).withForce(forces.snd);
            }
            if (newParticle.checkPoliceControls()) {
                newParticle = newParticle.beingStatic(true).withNewData(newParticle.getPosition(), Vector.ZERO);
            } else if (newParticle.checkPoliceControlFinished(state.getParticlesPerSection())) {
                newParticle = newParticle.beingStatic(false).calculatingOldPosition(parameters.getDeltaTime());
            }
            if (newParticle.getY() > -1) {
                newParticles.add(newParticle);
            }
        }
        return state.withNewParticles(newParticles);
    }

    private static Pair<Vector, Double> getForces(Parameters parameters, List<HorizontalWall> horizontalWalls, Particle particle, double kn, double kt,
                                                  List<Particle> staticParticles) {
        Vector totalForce = getTargetForce(particle, parameters, horizontalWalls);
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
        for (Particle neighbor : staticParticles) {
            double overlap = getOverlap(particle, neighbor);
            Vector normalVersor = getNormalVersor(particle, neighbor);
            if (overlap > 0) {
                Vector tangencialVersor = new Vector(-normalVersor.getY(), normalVersor.getX());

                Vector relativeSpeed = particle.getSpeed().sub(neighbor.getSpeed());

                Vector force = getForce(normalVersor, tangencialVersor, relativeSpeed, overlap, kn, kt);
                totalForce = totalForce.sum(force);
                totalForceModule += force.norm();
            }
        }
        Vector wallForce = wallCollision(parameters, horizontalWalls, particle, kn, kt);
        totalForce = totalForce.sum(wallForce);
        totalForceModule += wallForce.norm();
        return new Pair<>(totalForce, totalForceModule);
    }

    public static Vector socialForce(Parameters parameters, Vector normalVersor, double overlap) {
        Vector socialForce = normalVersor.scale(parameters.getA() * Math.exp(overlap / parameters.getB()));
        return socialForce.scale(-1);
    }

    private static Vector getTargetForce(Particle particle, Parameters parameters, List<HorizontalWall> horizontalWalls) {
        Vector target = new Vector(getDestinationX(particle, parameters), getDestinationY(particle, horizontalWalls));

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

    private static double getDestinationY(Particle particle, List<HorizontalWall> horizontalWalls) {
        // Order matters!
        for (int i = horizontalWalls.size() - 1; i >= 0; i --) {
            double wallPosition = horizontalWalls.get(i).getPosition();
            if (particle.getY() > wallPosition) {
                return wallPosition;
            }
        }
        return -1;
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

    private static Vector wallCollision(Parameters parameters, List<HorizontalWall> horizontalWalls, Particle particle, double kn, double kt) {
        Stream<Vector> fixedWallsStream = Stream.of(Wall.values())
                .map(wall -> wall.getForce(parameters, particle, kn, kt));

        Stream<Vector> horizontalWallsStream = horizontalWalls.stream()
                                            .map(wall -> wall.getForce(parameters, particle, kn, kt));

        return Stream
                .concat(fixedWallsStream, horizontalWallsStream)
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
