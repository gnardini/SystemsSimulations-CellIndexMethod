package pedestrian_dynamics.models;

import pedestrian_dynamics.Parameters;

import java.util.List;

public class State {
    private List<Particle> particles;
    private final Parameters parameters;

    public State(Parameters parameters, List<Particle> particles) {
        this.parameters = parameters;
        this.particles = particles;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public List<Particle> getParticles() {
        return particles;
    }
}
