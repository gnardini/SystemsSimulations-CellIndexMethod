package pedestrian_dynamics;

import pedestrian_dynamics.models.Particle;
import pedestrian_dynamics.models.State;
import pedestrian_dynamics.simulation.ParticleGenerator;
import pedestrian_dynamics.simulation.Simulation;
import pedestrian_dynamics.ui.NullPrinter;
import pedestrian_dynamics.ui.ParticlesLeftPrinter;
import pedestrian_dynamics.ui.Printer;
import pedestrian_dynamics.ui.UiPrinter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static final int PARTICLE_COUNT = 200;
    private static final double DESIRED_SPEED = 1.2;

    private static final int FRAMES_PER_SECOND = 60;
    private static final double DELTA_TIME = 1e-4;

    private static final boolean VISUAL = false;
    private static final boolean MULTIPLE = true;

    public static void main(String[] args) {
        try {
            if (MULTIPLE) {
                runMultiple();
            } else {
                Parameters parameters = new Parameters(PARTICLE_COUNT, DESIRED_SPEED, DELTA_TIME);
                List<Particle> particles = ParticleGenerator.generateParticles(parameters);
                State initialState = new State(parameters, particles, parameters.getD());

                if (VISUAL) {
                    Printer printer = new UiPrinter();
                    run(initialState, printer, parameters);
                } else {
                    Printer printer = new ParticlesLeftPrinter();
                    run(initialState, printer, parameters);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Stats run(State initialState, Printer printer, Parameters parameters) {
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
//                for (Double aDouble : stats.getTimesToLeave()) {
//                    System.out.print(aDouble + " ");
//                }
//                System.out.println();
//                System.out.println(stats.getTotalTime());
            }

            steps++;
            time += parameters.getDeltaTime();
        }
        System.out.println(stats.getTimesToLeave());
        return stats;
    }

    private static void runMultiple() throws InterruptedException {
        int timesPerVelocity = 5;

        double startingVelocity = 0.8;
        double finalVelocity = 6.1;
        double deltaVelocity = 0.2;

        double currentVelocity = startingVelocity;

        while (currentVelocity < finalVelocity) {
            runMultiThread(currentVelocity, timesPerVelocity);
            currentVelocity += deltaVelocity;
        }
    }

    private static void runMultiThread(double speed, int times) throws InterruptedException {
        System.out.println(String.format("---------- Calculating for desired speed: %s ----------", speed));
        ExecutorService executors = Executors.newFixedThreadPool(times);
        Printer printer = new NullPrinter();
        Parameters parameters = new Parameters(PARTICLE_COUNT, speed, DELTA_TIME);

        IntStream.rangeClosed(1, times).forEach(t -> {
            executors.submit(() -> {
                State initialState = new State(parameters, ParticleGenerator.generateParticles(parameters), parameters.getD());
                Stats stats = run(initialState, printer, parameters);
                write(speed, t, stats.getTimesToLeave());
                System.out.println(String.format("Run number %d: ", t));
            });
        });

        executors.shutdown();
        executors.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    private static int stepsToPrint() {
        // How many steps we need to get the desired fps with the delta time given
        return (int) (1 / DELTA_TIME) / FRAMES_PER_SECOND;
    }

    private static void write(double velocity, int sample, List<Double> particlesTime) {

        List<String> lines = particlesTime.stream().map(String::valueOf).collect(Collectors.toList());
        writeTo(OUTPUT_PATH + String.format("%.2f", velocity) + "_" + sample + ".txt", lines);
    }

    private static void writeTo(String fileName, List<String> lines) {
        Path path = Paths.get(fileName);
        try {
            Files.write(path, lines, Charset.forName("UTF-8"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static final String OUTPUT_PATH = "octave/TP6/files/";
}
