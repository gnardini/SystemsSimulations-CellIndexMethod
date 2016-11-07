package pedestrian_dynamics.ui;

import pedestrian_dynamics.models.State;

public class ParticlesLeftPrinter implements Printer {
    private Integer lastPrinted = Integer.MAX_VALUE;

    public void updateState(State state) {
        if (state.getParticleCount() % 10 == 0) {
            if (state.getParticleCount() != lastPrinted) {
                String out = String.format("%d particles left", state.getParticleCount());
                System.out.println(out);
                lastPrinted = state.getParticleCount();
            }
        }
    }
}
