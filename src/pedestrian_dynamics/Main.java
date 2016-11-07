package pedestrian_dynamics;

import pedestrian_dynamics.models.Particle;
import pedestrian_dynamics.models.State;
import pedestrian_dynamics.simulation.ParticleGenerator;
import pedestrian_dynamics.simulation.Simulation;
import pedestrian_dynamics.ui.Printer;
import pedestrian_dynamics.ui.UiPrinter;

import java.util.List;

public class Main {
    private static final int PARTICLE_COUNT = 200;
    private static final double DESIRED_SPEED = 1.2;

    private static final double SIMULATION_TIME = 40;
    private static final int FRAMES_PER_SECOND = 30;
    private static final double DELTA_TIME = 1e-5;

    private static final Parameters parameters = new Parameters(PARTICLE_COUNT, DESIRED_SPEED, DELTA_TIME);

    public static void main(String[] args) {
        List<Particle> particles = ParticleGenerator.generateParticles(parameters);
        State initialState = new State(parameters, particles);

        makeVisualRun(initialState);
    }

    private static void makeVisualRun(State initialState) {
        Printer printer = new UiPrinter();
        run(initialState, printer);
    }

    private static void run(State initialState, Printer printer) {
        double time = 0;
        int steps = 0;
        int printStep = stepsToPrint();
        State currentState = initialState;

        while (time < SIMULATION_TIME) {

            currentState = Simulation.updateState(currentState, parameters);

            if (steps % printStep == 0) {
                printer.updateState(currentState);
            }

            steps++;
            time += parameters.getDeltaTime();
        }
    }

    private static int stepsToPrint() {
        // How many steps we need to get the desired fps with the delta time given
        return (int) (1 / DELTA_TIME) / FRAMES_PER_SECOND;
    }
}
