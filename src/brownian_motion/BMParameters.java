package brownian_motion;

import brownian_motion.model.BMBoard;
import brownian_motion.model.BMParticle;

import java.util.List;

public class BMParameters {

    // Static constants (they don't change).
    private static final double SIZE = .5;
    private static final double SMALL_RADIUS = .005;
    private static final double SMALL_MASS = .1;
    private static final double LARGE_RADIUS = .05;
    private static final double LARGE_MASS = 100;
    private static final double MAX_ABSOLUTE_INITIAL_SPEED = .1;

    // Constants that may change.
    private static final int N = 100;

    public static BMBoard getInitialBoard() {
        List<BMParticle> particles =
                BMParticleGenerator.randomParticles(N, SIZE, SMALL_RADIUS, SMALL_MASS, MAX_ABSOLUTE_INITIAL_SPEED);
        BMParticle bigParticle = BMParticle.random(N, SIZE, LARGE_RADIUS, LARGE_MASS, 0);
        return new BMBoard(bigParticle, particles, SIZE);
    }

}
