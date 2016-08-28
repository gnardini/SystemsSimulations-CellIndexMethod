package off_lattice;

import exercises.Exercise;
import models.Particle;
import models.State;

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
        for (int i = 1; i <= particlesCount; i++) {
            Scanner lineScanner = new Scanner(lines.get(i));
            int id = lineScanner.nextInt();
            double x = lineScanner.nextDouble();
            double y = lineScanner.nextDouble();
            double angle = lineScanner.nextDouble();
            particles.add(new Particle(id, x, y,
                    OffLatticeParameters.PARTICLES_RADIUS, OffLatticeParameters.SPEED, angle));
        }
        return particles;
    }

    private void addParticleLine(List<String> lines, Particle particle) {
        lines.add(particle.getId() + " "
                + particle.getX() + " "
                + particle.getY() + " "
                + particle.getAngle());
    }

}
