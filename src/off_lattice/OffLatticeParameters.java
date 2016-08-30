package off_lattice;

import models.Particle;
import models.State;

import java.util.List;

public class OffLatticeParameters {

    static final String LATTICE_FORMAT = "files/off_lattice/frame_%d";
    static final int GENERATIONS = 500;

    private static final int N = 300; // Number of particles
    private static final int L = 25; // Length of side
    static final int INTERACTION_RADIUS = 1; // Particles will only take into account those others in its radious
    static final int PARTICLES_RADIUS = 0; // They have no mass nor radius.
    static final double SPEED = 0.03; // They all move at the same speed (but not same direction).
    private static final float AMPLITUDE = .1f;

    // If true, the simulation will be displayed real-time instead of saved to a file.
    static final boolean PLAY_LOCAL = false;

    // Delay in millis between each frame when playing from saved files.
    static final long VISUALIZATION_DELAY_MILLIS = 5;

    static State createState(List<Particle> particleList) {
        return createState(particleList, getInitialParameters());
    }

    static State createState(List<Particle> particleList, InitialParams initialParams) {
        return new State(particleList, M(initialParams.L), initialParams.L, SPEED, INTERACTION_RADIUS, initialParams.ETA);
    }

    private static int M(int L) {
        return L / INTERACTION_RADIUS - 1;
    }

    static InitialParams getInitialParameters() {
        return defaultParams();
    }

    static InitialParams defaultParams() {
        return new InitialParams(N, L, AMPLITUDE);
    }

    private static InitialParams chaosParams() {
        return new InitialParams(300, 7, 2);
    }

    private static InitialParams polarizedParams() {
        return new InitialParams(300, 5, .1f);
    }

    private static InitialParams groupsParams() {
        return new InitialParams(300, 25, .1f);
    }

}
