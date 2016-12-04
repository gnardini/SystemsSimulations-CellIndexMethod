package pedestrian_dynamics.simulation;

import pedestrian_dynamics.ExponentialGenerator;
import pedestrian_dynamics.Parameters;
import pedestrian_dynamics.models.HorizontalWall;
import pedestrian_dynamics.models.Particle;
import pedestrian_dynamics.models.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParticleGenerator {

    public static List<Particle> generateParticles(Parameters parameters) {
        List<Particle> particles = new ArrayList<>();
        int id = 0;

        int numberOfControls = parameters.getStaticParticlesPerControl().length;

        double distancePerControl = parameters.getL() / (numberOfControls + 2);

        while (id < parameters.getParticleCount()) {
            double radius = randomRadius(parameters);

            Vector particlePosition = generateValidPosition(
                    particles, parameters.getW(), parameters.getL() - 2 * distancePerControl, parameters.getL(), radius);
            List<PoliceStop> policeStops =
                    createPoliceStops(parameters, distancePerControl);

            particles.add(
                    new Particle(parameters, id, radius, particlePosition,
                            Vector.ZERO, Vector.ZERO, Vector.ZERO, 0, false, policeStops)
                            .calculatingOldPosition(parameters.getDeltaTime())
            );
            id++;
        }
        return particles;
    }

    public static List<Particle> generateStaticParticles(Parameters parameters) {
        int numberOfControls = parameters.getStaticParticlesPerControl().length;
        double distancePerControl = parameters.getL() / Double.valueOf((numberOfControls + 2));

        int id = parameters.getParticleCount();
        List<Particle> particles = new ArrayList<>();

        for (int control = 0; control < numberOfControls; control++) {
            int numberOfParticlesForControl = parameters.getStaticParticlesPerControl()[numberOfControls - 1 - control];
            fillControl(numberOfParticlesForControl, control * distancePerControl, parameters, particles, id);
            id += numberOfParticlesForControl;
        }

        return particles;
    }

    public static List<HorizontalWall> generateHorizontalWalls(Parameters parameters) {
        int numberOfControls = parameters.getStaticParticlesPerControl().length;
        double distancePerControl = parameters.getL() / Double.valueOf((numberOfControls + 2));

        List<HorizontalWall> horizontalWalls = new ArrayList<>(numberOfControls);

        for (int control = 0; control < numberOfControls; control++) {
            horizontalWalls.add(new HorizontalWall(control * distancePerControl));
        }

        return horizontalWalls;
    }


    private static void fillControl(int numberOfParticles, double y, Parameters parameters, List<Particle> particles, int id) {
        double radius = parameters.getMaxParticleRadius();
        double particleDiameter = 2 * radius;

        int maxPossibleStaticParticleCount = (int) Math.floor(parameters.getD() / particleDiameter);

        if (numberOfParticles > maxPossibleStaticParticleCount) {
            throw new RuntimeException("Error! Static particle count has to be less than or equal to " + maxPossibleStaticParticleCount);
        }

        double startingAt = parameters.getW() / 2 - parameters.getD() / 2;
        double distancePerParticle = parameters.getD() / (numberOfParticles + 1);

        for (double x = distancePerParticle; x < parameters.getD(); x += distancePerParticle) {
            particles.add(
                    new Particle(
                            parameters, id++, radius, new Vector(startingAt + x, y), Vector.ZERO, Vector.ZERO,
                            Vector.ZERO, 0, true, new ArrayList<>())
                            .calculatingOldPosition(parameters.getDeltaTime()));
        }

    }

    private static List<PoliceStop> createPoliceStops(Parameters parameters, double distanceBetweenControls) {
        int controlCount = parameters.getStaticParticlesPerControl().length;
        List<PoliceStop> policeStops = new LinkedList<>();
        for (int i = 0; i < controlCount; i++) {
            int delay = parameters.getDelayPerControl()[controlCount - 1 - i];
            double rate = 1 / (double)delay;
            double exponencialDelay = ExponentialGenerator.next(rate);
            policeStops.add(new PoliceStop(i * distanceBetweenControls, exponencialDelay));
        }

        return policeStops;
    }

    private static double randomRadius(Parameters parameters) {
        return parameters.getMinParticleRadius() + Math.random() * (parameters.getMaxParticleRadius() - parameters.getMinParticleRadius());
    }

    private static Vector generateValidPosition(List<Particle> particles, double W, double fromY, double toY, double radius) {
        while (true) {
            double x = radius + Math.random() * (W - 2 * radius);
            double y = fromY + radius + Math.random() * (toY - fromY - 2 * radius);

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
