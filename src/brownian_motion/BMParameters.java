package brownian_motion;

import brownian_motion.model.BMBoard;
import brownian_motion.model.BMParticle;

import java.util.List;

public class BMParameters {

    // Static constants (they don't change).
    public static final double BOARD_SIDE_LENGTH = .5;

    private static final double SMALL_PARTICLE_RADIUS = .005;
    private static final double SMALL_PARTICLE_MASS = .1;
    public static final double SMALL_PARTICLE_INITIAL_SPEED = .01; // this ends up being [-value, value]

    private static final double LARGE_PARTICLE_RADIUS = .05;
    private static final double LARGE_PARTICLE_MASS = 100;

    public static final int ANIMATION_DELAY = 0;

    // Constants that may change.
    public static final int N = 50;

    public static BMBoard getInitialBoard() {
        BMParticle bigParticle = BMParticleGenerator.randomBigParticle(N, BOARD_SIDE_LENGTH, LARGE_PARTICLE_RADIUS, LARGE_PARTICLE_MASS);
        List<BMParticle> particles =
                BMParticleGenerator.randomParticles(N, BOARD_SIDE_LENGTH, SMALL_PARTICLE_RADIUS, SMALL_PARTICLE_MASS, SMALL_PARTICLE_INITIAL_SPEED,
                        bigParticle);
        return new BMBoard(bigParticle, particles, BOARD_SIDE_LENGTH);
    }

}
