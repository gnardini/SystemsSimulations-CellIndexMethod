package brownian_motion;

import brownian_motion.model.BMBoard;
import brownian_motion.model.BMParticle;

import java.util.List;

public class BMParameters {

    // Static constants (they don't change).
    public static final double SIZE = .5;
    private static final double SMALL_RADIUS = .005;
    private static final double SMALL_MASS = .1;
    private static final double LARGE_RADIUS = .05;
    private static final double LARGE_MASS = 100;
    public static final double MAX_ABSOLUTE_INITIAL_SPEED = .01;
    public static final int ANIMATION_DELAY = 0;

    // Constants that may change.
    public static final int N = 300;

    public static BMBoard getInitialBoard() {
        BMParticle bigParticle = BMParticleGenerator.randomBigParticle(N, SIZE, LARGE_RADIUS, LARGE_MASS);
        List<BMParticle> particles =
                BMParticleGenerator.randomParticles(N, SIZE, SMALL_RADIUS, SMALL_MASS, MAX_ABSOLUTE_INITIAL_SPEED,
                        bigParticle);
        return new BMBoard(bigParticle, particles, SIZE);
    }

}
