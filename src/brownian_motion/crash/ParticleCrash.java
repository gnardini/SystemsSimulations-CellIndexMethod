package brownian_motion.crash;

import brownian_motion.BMParameters;
import brownian_motion.model.BMParticle;

import java.awt.*;

public class ParticleCrash implements Crash {

    private final double timeUntilCrash;
    private final BMParticle particle1;
    private final BMParticle particle2;

    public ParticleCrash(double timeUntilCrash, BMParticle particle1, BMParticle particle2) {
        this.timeUntilCrash = timeUntilCrash;
        this.particle1 = particle1;
        this.particle2 = particle2;
    }

    @Override
    public void applyCrash() {
        double xDiff = particle2.getX() - particle1.getX();
        double yDiff = particle2.getY() - particle1.getY();
        double xSpeedDiff = particle2.getxSpeed() - particle1.getxSpeed();
        double ySpeedDiff = particle2.getySpeed() - particle1.getySpeed();
        double speedByPosition = xDiff * xSpeedDiff + yDiff * ySpeedDiff;
        double particle1Mass = particle1.getMass();
        double particle2Mass = particle2.getMass();

        double sigma = particle1.getRadius() + particle2.getRadius();

        double J = (2 * particle1Mass * particle2Mass * speedByPosition) / (sigma * (particle1Mass + particle2Mass));
        double Jx = J * (particle2.getX() - particle1.getX()) / sigma;
        double Jy = J * (particle2.getY() - particle1.getY()) / sigma;

        particle1.modifySpeed(Jx / particle1Mass, Jy / particle1Mass);
        particle2.modifySpeed(- Jx / particle2Mass, - Jy / particle2Mass);
    }

    @Override
    public double getTimeUntilCrash() {
        return timeUntilCrash;
    }

}
