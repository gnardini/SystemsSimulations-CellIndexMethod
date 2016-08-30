package off_lattice;

import exercises.Exercise;
import models.Particle;
import models.State;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class OffLatticeFileManager {

    private int frameNumber;

    public OffLatticeFileManager() {
        frameNumber = 1;
    }

    public void generateFrame(State state, String fileNameFormat) {
        List<String> lines = new LinkedList<>();
        lines.add(String.valueOf(state.getParticleCount()));
        lines.add("");
        state.getParticles().forEach(particle -> addParticleLine(lines, particle));
        Exercise.writeTo(String.format(fileNameFormat, frameNumber), lines);
        frameNumber++;
    }

    public List<Particle> getParticles(String fileName) {
        List<String> lines = Exercise.readFrom(fileName);
        List<Particle> particles = new LinkedList<>();
        if (lines == null) {
            return particles;
        }
        int particlesCount = Integer.valueOf(lines.get(0));
        // Starts in 2 because first line is a comment (XYZ format)
        for (int i = 2; i < particlesCount + 2; i++) {
            Scanner lineScanner = new Scanner(lines.get(i));
            int id = lineScanner.nextInt();
            double x = lineScanner.nextDouble();
            double y = lineScanner.nextDouble();
            double angle = lineScanner.nextDouble();
            int red = lineScanner.nextInt();
            int green = lineScanner.nextInt();
            int blue = lineScanner.nextInt();
            particles.add(new Particle(id, x, y,
                    OffLatticeParameters.PARTICLES_RADIUS, OffLatticeParameters.SPEED, angle));
        }
        return particles;
    }

    private void addParticleLine(List<String> lines, Particle particle) {
        Color color = particle.getColorForAngle();
        lines.add(particle.getId() + " "
                + particle.getX() + " "
                + particle.getY() + " "
                + particle.getAngle() + " "
                + color.getRed() + " "
                + color.getGreen() + " "
                + color.getBlue());
    }

}
