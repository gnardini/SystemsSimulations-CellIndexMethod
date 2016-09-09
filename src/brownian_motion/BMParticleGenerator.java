package brownian_motion;

import brownian_motion.model.BMParticle;
import models.Particle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BMParticleGenerator {

    public static List<BMParticle> randomParticles(
            int particleCount, double boardSize, double radius, double mass, double speedMaxAbs, Color color) {
        final List<BMParticle> particles = new ArrayList<>();
        for (int i = 0; i < particleCount; i++) {
            BMParticle particle;
            do {
                particle = BMParticle.random(i, boardSize, radius, mass, speedMaxAbs, color);
            } while (!isValidParticle(boardSize, particles, particle));
            particles.add(particle);
        }
        return particles;
    }

    private static boolean isValidParticle(double boardSize, List<BMParticle> existingParticles, BMParticle newParticle) {
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
