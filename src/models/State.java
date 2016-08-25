package models;

import java.util.List;

/**
 * Created by FranDepascuali on 8/25/16.
 */
public class State {

    private Board board;
    private double speed;

    private int interactionRadius;

    public State(Board board, double speed, int interactionRadius) {
        if (Double.valueOf(board.getL()) / board.getM() <= interactionRadius) {
            throw new IllegalArgumentException("Invalid M");
        }
        this.board = board;
        this.speed = speed;
        this.interactionRadius = interactionRadius;
    }

    public State(List<Particle> particles, int M, int L, double speed, int interactionRadius) {
        this(new Board(M, L, particles), speed, interactionRadius);
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


}
