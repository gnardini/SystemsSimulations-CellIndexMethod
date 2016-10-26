package granular_medium.models;

import granular_medium.Parameters;

import java.util.List;

public class State {

    private Board board;
    private final Parameters parameters;
    private double interactionRadius;
    public int particleCount;

    public State(Board board, Parameters parameters, double interactionRadius) {
        this.parameters = parameters;
        if (Double.valueOf(board.getL()) / board.getM() <= interactionRadius) {
            throw new IllegalArgumentException("Invalid M");
        }
        this.board = board;
        this.particleCount = board.getParticles().size();
        this.interactionRadius = interactionRadius;
    }

    public State(List<Particle> particles, int M, Parameters parameters, double interactionRadius) {
        this(
                new Board((int) (parameters.getL() / (2*interactionRadius)), parameters.getL() + 1, particles),
                parameters,
                interactionRadius);
    }

    public double calculateKineticEnergy() {
        return getParticles().stream()
                .mapToDouble(particle -> .5 * particle.getMass() * Math.pow(particle.getSpeed().norm(), 2))
                .sum()
                / getParticles().size();
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


    public int getParticleCount() {
        return particleCount;
    }

    public State copy() {
        return new State(board.copy(), parameters, interactionRadius);
    }

    public State withNewParticles(List<Particle> particles) {
        return new State(board.withNewParticles(particles), parameters, interactionRadius);
    }

    public double getDensity() {
        return particleCount / Math.pow(board.getL(), 2);
    }

}
