package pedestrian_dynamics.models;

import pedestrian_dynamics.Parameters;

import java.util.LinkedList;
import java.util.List;

public class State {

    private Board board;
    private final List<Particle> staticParticles;
    private final Parameters parameters;
    private final int[] particlesPerSection;
    private double interactionRadius;
    public int particleCount;
    private double currentTime = 0.0;

    public State(Board board, Parameters parameters, List<Particle> staticParticles, double interactionRadius) {
        this.parameters = parameters;
        if (Double.valueOf(board.getL()) / board.getM() <= interactionRadius) {
            throw new IllegalArgumentException("Invalid M");
        }
        this.board = board;
        this.staticParticles = staticParticles;
        this.particleCount = board.getParticles().size();
        this.interactionRadius = interactionRadius;
        this.particlesPerSection = new int[parameters.getStaticParticlesPerControl().length + 3];
        calculateParticlesPerSection();
    }

    public State(Board board, Parameters parameters, List<Particle> staticParticles, double interactionRadius, double time) {
        this(board, parameters, staticParticles, interactionRadius);
        this.currentTime = time;
    }

    public State(Parameters parameters, List<HorizontalWall> horizontalWalls, List<Particle> particles, List<Particle> staticParticles, double interactionRadius) {
        this(
                new Board(horizontalWalls, (int) (parameters.getL() / interactionRadius), parameters.getL() + 1, particles),
                parameters,
                staticParticles,
                interactionRadius);
    }

    public double calculateKineticEnergy() {
        return getParticles().stream()
                .mapToDouble(particle -> .5 * particle.getMass() * Math.pow(particle.getSpeed().norm(), 2))
                .sum()
                / getParticles().size();
    }

    private void calculateParticlesPerSection() {
        double distanceBetweenStops =
                parameters.getL() / Double.valueOf(parameters.getStaticParticlesPerControl().length + 2);
        board.getParticles().forEach(particle -> {
            int stopIndex = (int) ((particle.getY() + 0.1) / distanceBetweenStops);
            particlesPerSection[stopIndex]++;
        });
//        for (int i = 0; i < particlesPerSection.length; i++) {
//            System.out.print(particlesPerSection[i] + ", ");
//        }
//        System.out.println();
    }

    public int[] getParticlesPerSection() {
        return particlesPerSection;
    }

    public List<Particle> getStaticParticles() {
        return staticParticles;
    }

    public int getM() {
        return board.getM();
    }

    public double getL() {
        return parameters.getL();
    }

    public double getW() {
        return parameters.getW();
    }

    public double getD() {
        return parameters.getD();
    }

    public double getInteractionRadius() {
        return interactionRadius;
    }

    public Board getBoard() {
        return board;
    }

    public List<Particle> getParticles() {
        return board.getParticles();
    }

    public Parameters getParameters() {
        return parameters;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public State copy() {
        return new State(board.copy(), parameters, staticParticles, interactionRadius);
    }

    public State withNewParticles(List<Particle> particles) {
        return new State(board.withNewParticles(particles), parameters, staticParticles, interactionRadius, currentTime + parameters.getDeltaTime());
    }

    public double getDensity() {
        return particleCount / Math.pow(board.getL(), 2);
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public State updatingStaticParticlesRadius() {
        double newRadius = staticParticles.get(0).getRadius() > parameters.getMaxParticleRadius() - .01
                ? parameters.getMinParticleRadius() : parameters.getMaxParticleRadius();
        List<Particle> newStaticParticles = new LinkedList<>();
        for (Particle staticParticle : staticParticles) {
            newStaticParticles.add(staticParticle.withNewRadius(newRadius));
        }
        return new State(board, parameters, newStaticParticles, interactionRadius, currentTime + parameters.getDeltaTime());
    }

}
