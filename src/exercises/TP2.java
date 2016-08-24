package exercises;

import algorithms.CellIndexMethod;
import models.Particle;
import ui.ParticlePrinter;

import java.util.List;

public class TP2 {

    private static final int BOARD_SIZE = 100;
    private static final int BOARD_M = 10;
    private static final int CONVERGENCE_RADIUS = 6;
    private static final int PARTICLES_RADIUS = 1;

    public TP2() {
        Exercise.generateRandomInput(Exercise.RANDOM_INPUT_PATH, 1000, 100);

        List<Particle> particles = Exercise.getParticlesFromRandomInput(Exercise.RANDOM_INPUT_PATH, PARTICLES_RADIUS);

        CellIndexMethod cellIndexMethod = new CellIndexMethod(particles, BOARD_M, BOARD_SIZE, CONVERGENCE_RADIUS);
        cellIndexMethod.calculateDistanceWithEdge();

        ParticlePrinter pp = new ParticlePrinter();
        while (true) {
            cellIndexMethod.calculateDistanceWithEdge();
            cellIndexMethod.nextStep();
            pp.setBoard(cellIndexMethod.getBoard());
        }

    }

    public static void main(String[] args) {
        new TP2();
    }

}
