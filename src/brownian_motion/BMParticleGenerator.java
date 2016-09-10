package brownian_motion;

import brownian_motion.model.BMParticle;

import java.util.ArrayList;
import java.util.List;

public class BMParticleGenerator {

    public static List<BMParticle> randomParticles(
            int particleCount, double boardSize, double radius, double mass, double speedMaxAbs,
            BMParticle bigParticle) {
        final List<BMParticle> particles = new ArrayList<>();
        particles.add(bigParticle);
        for (int i = 0; i < particleCount; i++) {
            BMParticle particle;
            do {
                particle = BMParticle.random(i, boardSize, radius, mass, speedMaxAbs);
            } while (!isValidParticle(boardSize, particles, particle));
            particles.add(particle);
        }
        return particles;
    }

    public static BMParticle randomBigParticle(int id, double boardSize, double radius, double mass) {
        BMParticle bigParticle;
        do {
            bigParticle = BMParticle.random(id, boardSize, radius, mass, 0);
        } while (!bigParticle.isInBoard(boardSize));
        return bigParticle;
    }

    private static boolean isValidParticle(double boardSize, List<BMParticle> existingParticles,
                                           BMParticle newParticle) {
        if (!newParticle.isInBoard(boardSize)) {
            return false;
        }
        for (BMParticle existingParticle : existingParticles) {
            if (existingParticle.collidesWith(newParticle)) {
                return false;
            }
        }
        return true;
    }

}
