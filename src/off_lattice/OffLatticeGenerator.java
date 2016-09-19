package off_lattice;

import cell_index_method.algorithms.CellIndexMethod;
import helper.CompletionTracker;
import cell_index_method.input.RandomInputGenerator;
import cell_index_method.models.Particle;
import cell_index_method.models.State;
import cell_index_method.ui.ParticlePrinter;

import java.util.List;

public class OffLatticeGenerator {

    public OffLatticeGenerator() {
        // Generate a random starting state.
        RandomInputGenerator randomInputGenerator = new RandomInputGenerator();
        InitialParams initialParams = OffLatticeParameters.getInitialParameters();
        List<Particle> particles = randomInputGenerator.generateRandomParticles(
                initialParams.N,
                initialParams.L,
                OffLatticeParameters.PARTICLES_RADIUS,
                OffLatticeParameters.SPEED);
        State state = OffLatticeParameters.createState(particles, initialParams);
        CellIndexMethod cellIndexMethod = new CellIndexMethod();

        // Start the simulation.
        if (OffLatticeParameters.PLAY_LOCAL) {
            playLocal(state, cellIndexMethod);
        } else {
            generateSimulation(state, cellIndexMethod);
        }
    }

    /**
     * Iterates indefinitely through the simulation, printing the result on the screen.
     */
    private void playLocal(State state, CellIndexMethod cellIndexMethod) {
        ParticlePrinter particlePrinter = new ParticlePrinter();
        while (true) {
            cellIndexMethod.nextStep(state);
            particlePrinter.setState(state);
            state = state.copy();
        }
    }

    /**
     * Iterates a set amount of steps through the simulation, and saves each state to a different file.
     */
    private void generateSimulation(State state, CellIndexMethod cellIndexMethod) {
        OffLatticeFileManager offLatticeFileManager = new OffLatticeFileManager();
        CompletionTracker completionTracker = new CompletionTracker();

        int generations = OffLatticeParameters.GENERATIONS;
        for (int i = 0; i < generations; i++) {
            completionTracker.updateCompletedPercentage((double) i / generations);
            cellIndexMethod.nextStep(state);
            offLatticeFileManager.saveDataForState(state);
            offLatticeFileManager.generateFrame(state, OffLatticeParameters.LATTICE_FORMAT);
            state = state.copy();
        }
        offLatticeFileManager.printGraphDataSet(state, OffLatticeParameters.GRAPH_DATA_SET);
    }

    public static void main(String[] args) {
        new OffLatticeGenerator();
    }

}
