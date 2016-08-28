package off_lattice;

import models.Particle;
import models.State;

import java.util.List;

public class OffLatticeParameters {

    static final String LATTICE_FORMAT = "files/off_lattice/frame_%d";
    static final int GENERATIONS = 1000;

    static final int N = 300; // Number of particles
    static final int L = 25; // Length of side
    static final int M = 5; // Number of cells (M * M)
    static final int INTERACTION_RADIUS = 1; // Particles will only take into account those others in its radious
    static final int PARTICLES_RADIUS = 0; // They have no mass nor radius.
    static final double SPEED = 0.03; // They all move at the same speed (but not same direction).
    static final float AMPLITUDE = .1f;

    // If true, the simulation will be displayed real-time instead of saved to a file.
    static final boolean PLAY_LOCAL = false;

    // Delay in millis between each frame when playing from saved files.
    static final long VISUALIZATION_DELAY_MILLIS = 5;

    static State createState(List<Particle> particleList) {
        return new State(particleList, M, L, SPEED, INTERACTION_RADIUS);
    }

}
