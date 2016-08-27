package exercises;

import algorithms.CellIndexMethod;
import models.Particle;
import models.State;
import ui.ParticlePrinter;

import java.util.List;

public class OffLatice {

    private static final int N = 300; // Number of particles
    private static final int L = 25; // Length of side
    private static final int M = 5; // Number of cells (M * M)
    private static final int INTERACTION_RADIUS = 1; // Particles will only take into account those others in its radious
    private static final int PARTICLES_RADIUS = 0; // They have no mass nor radius.
    private static final double SPEED = 0.003; // They all move at the same speed (but not same direction).

    protected static final String OUTPUT_DIRECTORY_PATH = "filesToVisualize";

    public OffLatice() {
        Exercise.generateRandomInput(Exercise.RANDOM_INPUT_PATH, N, L);
        List<Particle> particles = Exercise.getParticlesFromRandomInput(Exercise.RANDOM_INPUT_PATH, PARTICLES_RADIUS);

        for (Particle p: particles) {
            p.setSpeed(SPEED);
            //System.out.println("x: " + p.getX() + ", y: " + p.getY() + ", angle(radians): " + p.getAngle());

        }



        State state = new State(particles, M, L, SPEED, INTERACTION_RADIUS);
        CellIndexMethod cellIndexMethod = new CellIndexMethod();
        ParticlePrinter particlePrinter = new ParticlePrinter();

        while(true) {
            cellIndexMethod.nextStep(state);
            particlePrinter.setState(state);
        }
    }

    public static void main(String[] args) {
        new OffLatice();
    }

}
