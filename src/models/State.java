package models;

import java.util.List;

public class State {

    private Board board;
    private double speed;
    private double eta;

    private int interactionRadius;

    public int particleCount;

    public State(Board board, double speed, int interactionRadius, double eta) {
        if (Double.valueOf(board.getL()) / board.getM() <= interactionRadius) {
            throw new IllegalArgumentException("Invalid M");
        }
        this.board = board;
        this.particleCount = board.getParticles().size();
        this.speed = speed;
        this.interactionRadius = interactionRadius;
        this.eta = eta;
    }

    public State(List<Particle> particles, int M, int L, double speed, int interactionRadius, double eta) {
        this(new Board(M, L, particles), speed, interactionRadius, eta);
    }

    public double polarization() {
        double xSpeedSum = 0;
        double ySpeedSum = 0;

        List<Particle> particles = board.getParticles();

        for (Particle particle: particles) {
            xSpeedSum += particle.getSpeed() * Math.cos(particle.getAngle());
            ySpeedSum += particle.getSpeed() * Math.sin(particle.getAngle());
        }

        return Math.sqrt(Math.pow(xSpeedSum, 2) + Math.pow(ySpeedSum, 2)) / (speed * particles.size());
    }

    public int getM() {
        return board.getM();
    }

    public int getL() {
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

    public double getSpeed() {
        return speed;
    }

    public double getEta() {
        return eta;
    }

    public State copy() {
        return new State(board.copy(), speed, interactionRadius, eta);
    }

    public double getDensity() {
        return particleCount / Math.pow(board.getL(), 2);
    }

}
