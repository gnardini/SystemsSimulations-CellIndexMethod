package brownian_motion.crash;

import brownian_motion.model.BMParticle;

public class WallCrash implements Crash {

    private final double timeUntilCrash;
    private final BMParticle particle;
    private final WallCrashTransformer transformer;

    public WallCrash(double timeUntilCrash, BMParticle particle, WallCrashTransformer transformer) {
        this.timeUntilCrash = timeUntilCrash;
        this.particle = particle;
        this.transformer = transformer;
    }

    @Override
    public void applyCrash() {
        transformer.applyTransformation(particle);
    }

    @Override
    public double getTimeUntilCrash() {
        return timeUntilCrash;
    }

}
