package pedestrian_dynamics;

import pedestrian_dynamics.models.Particle;
import pedestrian_dynamics.models.State;
import pedestrian_dynamics.simulation.ParticleGenerator;
import pedestrian_dynamics.ui.Printer;
import pedestrian_dynamics.ui.UiPrinter;

import java.util.List;

public class Main {
    private static final int PARTICLE_COUNT = 200;
    private static final double DESIRED_SPEED = 1.2;

    public static void main(String[] args) {
        Parameters parameters = new Parameters(PARTICLE_COUNT, DESIRED_SPEED);
        List<Particle> particles = ParticleGenerator.generateParticles(parameters);
        State initialState = new State(parameters, particles);

        makeVisualRun(initialState);
    }

    private static void makeVisualRun(State initialState) {
        Printer printer = new UiPrinter();
        printer.updateState(initialState);
    }
}
