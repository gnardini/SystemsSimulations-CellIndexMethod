package pedestrian_dynamics;

import pedestrian_dynamics.models.State;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Stats {

    private static final String OUTPUT_PATH = "files/TPF/";

    private final Parameters parameters;
    private final List<Double> timesToLeave;
    private final double[][] timePerSection;

    public Stats(Parameters parameters) {
        this.parameters = parameters;
        timesToLeave = new ArrayList<>(parameters.getParticleCount());
        timePerSection = new double[parameters.getParticleCount()][parameters.getMaxPeoplePerSection().length];
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
        writeTo(OUTPUT_PATH + "particle_escape_per_section.txt", Arrays.asList(output.toString().split("\n")));

        List<String> timesToLeave = this.timesToLeave.stream().map(String::valueOf).collect(Collectors.toList());
        writeTo(OUTPUT_PATH + "times_to_leave.txt", timesToLeave);
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
