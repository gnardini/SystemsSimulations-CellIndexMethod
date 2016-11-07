package pedestrian_dynamics;

import pedestrian_dynamics.models.Particle;
import pedestrian_dynamics.models.State;
import pedestrian_dynamics.simulation.ParticleGenerator;
import pedestrian_dynamics.simulation.Simulation;
import pedestrian_dynamics.ui.NullPrinter;
import pedestrian_dynamics.ui.ParticlesLeftPrinter;
import pedestrian_dynamics.ui.Printer;
import pedestrian_dynamics.ui.UiPrinter;

import java.util.List;
import java.util.stream.IntStream;

public class Main {
    private static final int PARTICLE_COUNT = 200;
    private static final double DESIRED_SPEED = 1.2;

    private static final int FRAMES_PER_SECOND = 60;
    private static final double DELTA_TIME = 1e-4;

    private static final Parameters PARAMETERS = new Parameters(PARTICLE_COUNT, DESIRED_SPEED, DELTA_TIME);
    private static final boolean VISUAL = false;
    private static final boolean MULTIPLE = true;

    public static void main(String[] args) {
        if (MULTIPLE) {
            runMultiple();
        } else {
            List<Particle> particles = ParticleGenerator.generateParticles(PARAMETERS);
            State initialState = new State(PARAMETERS, particles, PARAMETERS.getD());

            if (VISUAL) {
                Printer printer = new UiPrinter();
                run(initialState, printer);
            } else {
                Printer printer = new ParticlesLeftPrinter();
                run(initialState, printer);
            }
        }
    }

    private static void run(State initialState, Printer printer) {
        Stats stats = new Stats(PARAMETERS);
        double time = 0;
        int steps = 0;
        int printStep = stepsToPrint();
        State currentState = initialState;

        while (currentState.getParticleCount() > 0) {
            currentState = Simulation.updateState(currentState, PARAMETERS);

            stats.update(currentState, time);

//            if (steps % printStep == 0) {
//                printer.updateState(currentState);
//                for (Double aDouble : stats.getTimesToLeave()) {
//                    System.out.print(aDouble + " ");
//                }
//                System.out.println();
//                System.out.println(stats.getTotalTime());
//            }

            steps++;
            time += PARAMETERS.getDeltaTime();
        }
        System.out.println(stats.getTimesToLeave());
//        System.out.println(String.format("Total time: %s", stats.getTotalTime()));
    }

    private static void runMultiple() {
        int timesPerVelocity = 10;

        double startingVelocity = 0.8;
        double finalVelocity = 6.1;
        double deltaVelocity = 0.2;

        double currentVelocity = startingVelocity;
        Printer printer = new NullPrinter();

        while (currentVelocity < finalVelocity) {
            System.out.println(String.format("---------- Calculating for desired speed: %s ----------", currentVelocity));
            IntStream.range(1, timesPerVelocity).forEach(t -> {
                System.out.println(String.format("Run number %d: ", t));
                State initialState = new State(PARAMETERS, ParticleGenerator.generateParticles(PARAMETERS), PARAMETERS.getD());
                run(initialState, printer);
            });
            currentVelocity += deltaVelocity;
        }
    }

    private static int stepsToPrint() {
        // How many steps we need to get the desired fps with the delta time given
        return (int) (1 / DELTA_TIME) / FRAMES_PER_SECOND;
    }
}
