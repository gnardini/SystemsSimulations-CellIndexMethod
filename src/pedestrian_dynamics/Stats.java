package pedestrian_dynamics;

import pedestrian_dynamics.models.State;

import java.util.ArrayList;
import java.util.List;

public class Stats {

    List<Double> timesToLeave;

    public Stats(Parameters parameters) {
        timesToLeave = new ArrayList<>(parameters.getParticleCount());
    }

    public void update(State state, double totalTime) {
        state.getParticles().stream()
                .filter(particle -> particle.getY() < 0 && particle.getOldPosition().getY() >= 0)
                .forEach(particle -> timesToLeave.add(totalTime));
    }

    public List<Double> getTimesToLeave() {
        return timesToLeave;
    }

    public double getTotalTime() {
        if (timesToLeave.isEmpty()) return 0;
        return timesToLeave.get(timesToLeave.size() - 1);
    }
}
