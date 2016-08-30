package off_lattice;

import helper.CompletionTracker;
import models.Particle;
import models.State;
import ui.ParticlePrinter;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OffLatticeVisualizer {

    public OffLatticeVisualizer() {

        OffLatticeFileManager offLatticeFileManager = new OffLatticeFileManager();
        CompletionTracker completionTracker = new CompletionTracker(1);
        Queue<State> states = new LinkedList<>();

        // First, we read all files related to this run and add the states to a queue.
        int generations = OffLatticeParameters.GENERATIONS;
        for (int i = 1; i <= generations; i++) {
            completionTracker.updateCompletedPercentage((double) i / generations);
            List<Particle> particles = offLatticeFileManager.getParticles(
                    String.format(OffLatticeParameters.LATTICE_FORMAT, i));
            states.add(OffLatticeParameters.createState(particles, OffLatticeParameters.defaultParams()));
        }

        // After that, we just iterate through the queue and show each state with a set delay.
        ParticlePrinter particlePrinter = new ParticlePrinter();
        while (!states.isEmpty()) {
            particlePrinter.setState(states.remove());
            delay(OffLatticeParameters.VISUALIZATION_DELAY_MILLIS);
        }

    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new OffLatticeVisualizer();
    }
}
