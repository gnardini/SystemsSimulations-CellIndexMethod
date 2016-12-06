package pedestrian_dynamics;

import pedestrian_dynamics.models.State;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Stats {

    private static final String OUTPUT_PATH = "files/TPF/";
    private static final double DISTRIBUTION_CHECK_DELTA = 0.1;

    private final Parameters parameters;
    private final List<Double> timesToLeave;
    private final double[][] timePerSection;
    private final List<int[]> distributionsOverTime;

    private double nextDistributionCheck = DISTRIBUTION_CHECK_DELTA;

    public Stats(Parameters parameters) {
        this.parameters = parameters;
        timesToLeave = new ArrayList<>(parameters.getParticleCount());
        timePerSection = new double[parameters.getParticleCount()][parameters.getMaxPeoplePerSection().length];
        distributionsOverTime = new LinkedList<>();
    }

    public void update(State state, double totalTime) {
        double distanceBetweenStops =
                parameters.getL() / Double.valueOf(parameters.getStaticParticlesPerControl().length + 2);

        state.getParticles().forEach(particle -> {
            double yPosition = particle.getY() + 0.1;
            double oldYPosition = particle.getOldPosition().getY() + 0.1;
            for (int i = 0; i < timePerSection[0].length; i++) {
                double border = i * distanceBetweenStops;
                if (yPosition <= border && oldYPosition > border) {
                    double savedTime = totalTime;
                    for (int j = i + 1; j < timePerSection[0].length; j++) {
                        savedTime -= timePerSection[particle.getId()][j];
                    }
                    timePerSection[particle.getId()][i] = savedTime;
                }
            }
        });

        state.getParticles().stream()
                .filter(particle -> particle.getY() < -0.1 && particle.getOldPosition().getY() >= -0.1)
                .forEach(particle -> timesToLeave.add(totalTime));

        if (totalTime >= nextDistributionCheck) {
            int[] recordedParticlesPerSection = state.getParticlesPerSection();
            int[] particlesPerSection = new int[parameters.getDelayPerControl().length];
            int i = 0;
            for (; i < particlesPerSection.length; i++) particlesPerSection[i] = recordedParticlesPerSection[i];
            for (; i < recordedParticlesPerSection.length; i++)
                particlesPerSection[particlesPerSection.length - 1] += recordedParticlesPerSection[i];
            distributionsOverTime.add(particlesPerSection);
            nextDistributionCheck += DISTRIBUTION_CHECK_DELTA;
        }
    }

    public List<Double> getTimesToLeave() {
        return timesToLeave;
    }

    public double getTotalTime() {
        if (timesToLeave.isEmpty()) return 0;
        return timesToLeave.get(timesToLeave.size() - 1);
    }

    public void print() {
        StringBuilder output = new StringBuilder();
        System.out.println();
        for (int i = 0; i < timePerSection[0].length; i++) {
            for (int j = 0; j < timePerSection.length; j++) {
                System.out.print(timePerSection[j][i] + ", ");
                output.append(timePerSection[j][i] + "\t");
            }
            output.append('\n');
            System.out.println();
        }

        StringBuilder idBuilder = new StringBuilder();
        idBuilder.append("run_" + parameters.getId() + "_");
        for (int delay : parameters.getDelayPerControl()) {
            idBuilder.append(delay + "_");
        }
//        idBuilder.append(System.currentTimeMillis() + "_");
//        idBuilder.append(parameters.getMaxPeoplePerSection()[0] + "_");
        String id = idBuilder.toString();

        writeTo(OUTPUT_PATH + id + "particle_escape_per_section.txt", Arrays.asList(output.toString().split("\n")));

        StringBuilder distributions = new StringBuilder();
        for (int[] distribution : distributionsOverTime) {
            for (int value : distribution) {
                distributions.append(value).append('\t');
            }
            distributions.append("\n");
        }
        writeTo(OUTPUT_PATH + id + "particle_distribution_over_time.txt", Arrays.asList(distributions.toString().split("\n")));

//        List<String> timesToLeave = this.timesToLeave.stream().map(String::valueOf).collect(Collectors.toList());
//        writeTo(OUTPUT_PATH + id + "times_to_leave.txt", timesToLeave);
    }

    private static void writeTo(String fileName, List<String> lines) {
        Path path = Paths.get(fileName);
        try {
            Files.write(path, lines, Charset.forName("UTF-8"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
