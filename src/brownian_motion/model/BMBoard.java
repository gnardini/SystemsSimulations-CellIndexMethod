package brownian_motion.model;

import brownian_motion.crash.Crash;
import brownian_motion.crash.ParticleCrash;
import brownian_motion.crash.WallCrash;
import brownian_motion.crash.WallCrashTransformer;

import java.util.ArrayList;
import java.util.List;

public class BMBoard {

    private final double size;

    private final BMParticle bigParticle;
    private final List<BMParticle> particles;

    public BMBoard(BMParticle bigParticle, List<BMParticle> particles, double size) {
        if (!(particles instanceof ArrayList)) {
            System.out.println("Warning: Particles list passed to BMBoard is not an ArrayList. " +
                    "This might cause efficiency issues");
        }
        this.bigParticle = bigParticle;
        this.particles = particles;
        particles.add(bigParticle);
        this.size = size;
    }


    public double getSize() {
        return size;
    }

    public List<BMParticle> getParticles() {
        return particles;
    }

    public Crash calculateTimeUntilNextCrash() {
        Crash fastestCrash = null;
        Crash crash;
        BMParticle particle;

        for (int i = 0; i < particles.size(); i++) {
            particle = particles.get(i);
            crash = timeUntilWallCrash(particle);
            fastestCrash = getFastestCrash(fastestCrash, crash);
            for (int j = i + 1; j < particles.size(); j++) {
                crash = timeUntilParticleCrash(particle, particles.get(j));
                fastestCrash = getFastestCrash(fastestCrash, crash);
            }
        }
        return fastestCrash;
    }

    private Crash timeUntilWallCrash(BMParticle particle) {
        double timeToCrashInX = Double.MAX_VALUE;
        double xSpeed = particle.getxSpeed();
        if (xSpeed > 0) {
            timeToCrashInX = (size - particle.getRadius() - particle.getX()) / xSpeed;
        } else if (xSpeed < 0) {
            timeToCrashInX = (particle.getRadius() - particle.getX()) / xSpeed;
        }

        double timeToCrashInY = Double.MAX_VALUE;
        double ySpeed = particle.getySpeed();
        if (ySpeed > 0) {
            timeToCrashInY = (size - particle.getRadius() - particle.getY()) / ySpeed;
        } else if (ySpeed < 0) {
            timeToCrashInY = (particle.getRadius() - particle.getY()) / ySpeed;
        }

        if (timeToCrashInX < timeToCrashInY) {
            return new WallCrash(timeToCrashInX, particle, WallCrashTransformer.VERTICAL);
        } else {
            return new WallCrash(timeToCrashInY, particle, WallCrashTransformer.HORIZONTAL);
        }
    }

    private Crash timeUntilParticleCrash(BMParticle particle1, BMParticle particle2) {
        double xDiff = particle2.getX() - particle1.getX();
        double yDiff = particle2.getY() - particle1.getY();
        double xSpeedDiff = particle2.getxSpeed() - particle1.getxSpeed();
        double ySpeedDiff = particle2.getySpeed() - particle1.getySpeed();

        double positionDiffProduct = xDiff * xDiff + yDiff * yDiff;
        double speedDiffProduct = xSpeedDiff * xSpeedDiff + ySpeedDiff * ySpeedDiff;
        double speedByPosition = xDiff * xSpeedDiff + yDiff * ySpeedDiff;

        double sigma = particle1.getRadius() + particle2.getRadius();
        double d = Math.pow(speedByPosition, 2) - speedDiffProduct * (positionDiffProduct - Math.pow(sigma, 2));

        if (speedByPosition >= 0 || d < 0) {
            return null;
        }

        double timeUntilCrash = (-1) * (speedByPosition + Math.sqrt(d)) / speedDiffProduct;
        return new ParticleCrash(timeUntilCrash, particle1, particle2);
    }

    public void advanceTime(double timeToCrash) {
        particles.forEach(particle -> particle.advance(timeToCrash));
    }

    private Crash getFastestCrash(Crash crash1, Crash crash2) {
        if (crash1 == null) {
            return crash2;
        } else if (crash2 == null) {
            return crash1;
        } else if (crash1.getTimeUntilCrash() < crash2.getTimeUntilCrash()) {
            return crash1;
        } else {
            return crash2;
        }
    }

}
