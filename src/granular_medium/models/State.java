package granular_medium.models;

import java.util.List;

public class State {

    private Board board;
    private int interactionRadius;
    public int particleCount;

    public State(Board board, int interactionRadius) {
        if (Double.valueOf(board.getL()) / board.getM() <= interactionRadius) {
            throw new IllegalArgumentException("Invalid M");
        }
        this.board = board;
        this.particleCount = board.getParticles().size();
        this.interactionRadius = interactionRadius;
    }

    public State(List<Particle> particles, int M, double L, int interactionRadius) {
        this(new Board(M, L, particles), interactionRadius);
    }

    public int getM() {
        return board.getM();
    }

    public double getL() {
        return board.getL();
    }

    public int getInteractionRadius() {
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
        return new State(board.copy(), interactionRadius);
    }

    public State withNewParticles(List<Particle> particles) {
        return new State(board.withNewParticles(particles), interactionRadius);
    }

    public double getDensity() {
        return particleCount / Math.pow(board.getL(), 2);
    }

}
