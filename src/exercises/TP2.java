package exercises;

import algorithms.CellIndexMethod;
import models.Board;
import models.Particle;
import models.State;
import output.Formatter;
import output.JavaVisualizerFormatter;
import output.XYZFormatter;
import ui.ParticlePrinter;
import ui.ParticleVisualizer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TP2 {

    private static final int L = 100;
    private static final int M = 10;
    private static final int INTERACTION_RADIUS = 2;
    private static final int PARTICLES_RADIUS = 0;
    private static final double speed = 0.003;

    protected static final String OUTPUT_DIRECTORY_PATH = "filesToVisualize";

    public TP2() {
        Exercise.generateRandomInput(Exercise.RANDOM_INPUT_PATH, 300, 100);
        List<Particle> particles = Exercise.getParticlesFromRandomInput(Exercise.RANDOM_INPUT_PATH, PARTICLES_RADIUS);

        State state = new State(particles, M, L, speed, INTERACTION_RADIUS);
        CellIndexMethod cellIndexMethod = new CellIndexMethod();
        ParticlePrinter particlePrinter = new ParticlePrinter();

        while(true) {
            cellIndexMethod.nextStep(state);
            particlePrinter.setState(state);
        }
    }

    public static void main(String[] args) {
        new TP2();
    }

}
