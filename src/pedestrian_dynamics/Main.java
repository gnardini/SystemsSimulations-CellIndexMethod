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
    private static final double DELTA_TIME = 1e-4;

    private static final Parameters parameters = new Parameters(PARTICLE_COUNT, DESIRED_SPEED, DELTA_TIME);

    public static void main(String[] args) {
        List<Particle> particles = ParticleGenerator.generateParticles(parameters);
        State initialState = new State(parameters, particles, parameters.getD());

        makeVisualRun(initialState);
    }

    private static void makeVisualRun(State initialState) {
        Printer printer = new UiPrinter();
        run(initialState, printer);
    }

    private static void run(State initialState, Printer printer) {
        Stats stats = new Stats(parameters);
        double time = 0;
        int steps = 0;
        int printStep = stepsToPrint();
        State currentState = initialState;

        while (currentState.getParticleCount() > 0) {
            currentState = Simulation.updateState(currentState, parameters);

            stats.update(currentState, time);

            if (steps % printStep == 0) {
                printer.updateState(currentState);
                for (Double aDouble : stats.getTimesToLeave()) {
                    System.out.print(aDouble + " ");
                }
                System.out.println();
                System.out.println(stats.getTotalTime());
            }

            steps++;
            time += parameters.getDeltaTime();
        }
        System.out.println(stats.getTotalTime());
    }

    private static int stepsToPrint() {
        // How many steps we need to get the desired fps with the delta time given
        return (int) (1 / DELTA_TIME) / FRAMES_PER_SECOND;
    }
}
