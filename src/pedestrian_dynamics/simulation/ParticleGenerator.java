package pedestrian_dynamics.simulation;

import pedestrian_dynamics.Parameters;
import pedestrian_dynamics.models.Particle;
import pedestrian_dynamics.models.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleGenerator {

    public static List<Particle> generateParticles(Parameters parameters) {
        List<Particle> particles = new ArrayList<>();
        int id = 0;

        while (id < parameters.getParticleCount()) {
            double radius = randomRadius(parameters);
            Vector particlePosition = generateValidPosition(particles, parameters.getW(), parameters.getL(), radius);
            particles.add(
                    new Particle(parameters, id, radius, particlePosition, Vector.ZERO, Vector.ZERO, Vector.ZERO, 0, false)
                            .calculatingOldPosition(parameters.getDeltaTime())
            );
            id++;
        }
        double startingPosition = parameters.getW() / 2 - parameters.getD() / 2 + 1;
        double radius = parameters.getMaxParticleRadius();
        for (double xValue = startingPosition; xValue < startingPosition + 3; xValue += 1) {
            particles.add(
                new Particle(parameters, id++, radius, new Vector(xValue, 3), Vector.ZERO, Vector.ZERO, Vector.ZERO, 0, true)
                        .calculatingOldPosition(parameters.getDeltaTime()));
            particles.add(
                new Particle(parameters, id++, radius, new Vector(xValue, 6), Vector.ZERO, Vector.ZERO, Vector.ZERO, 0, true)
                        .calculatingOldPosition(parameters.getDeltaTime()));
        }
        return particles;
    }

    private static double randomRadius(Parameters parameters) {
        return parameters.getMinParticleRadius() + Math.random() * (parameters.getMaxParticleRadius() - parameters.getMinParticleRadius());
    }

    private static Vector generateValidPosition(List<Particle> particles, double W, double L, double radius) {
        while (true) {
            double x = radius + Math.random() * (W - 2 * radius);
            double y = radius + Math.random() * (L - 2 * radius - 6) + 6;

            if (particles.size() == 0 || isValidPosition(particles, x, y, radius)) {
                return new Vector(x, y);
            }
        }
    }

    public static boolean isValidPosition(List<Particle> particles, double x, double y, double radius) {
        return particles.stream().noneMatch(particle -> {
            double x2 = particle.getPosition().getX();
            double y2 = particle.getPosition().getY();
            double r2 = particle.getRadius();

            return isCollision(x, y, radius, x2, y2, r2);
        });
    }

    private static boolean isCollision(double x1, double y1, double r1, double x2, double y2, double r2) {
        return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) < Math.pow(r1 + r2, 2);
    }

}
