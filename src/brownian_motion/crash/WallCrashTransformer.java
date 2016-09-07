package brownian_motion.crash;

import brownian_motion.model.BMParticle;

public enum WallCrashTransformer {

    VERTICAL,
    HORIZONTAL;

    // This is ugly.
    public void applyTransformation(BMParticle particle) {
        switch (this) {
            case VERTICAL:
                particle.invertXSpeed();
                break;
            case HORIZONTAL:
                particle.invertYSpeed();
                break;
        }
    }

}
