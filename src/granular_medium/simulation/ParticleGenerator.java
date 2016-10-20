package granular_medium.simulation;

import granular_medium.Parameters;
import granular_medium.models.Particle;
import granular_medium.models.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParticleGenerator {

  public static List<Particle> generateParticles(double L, double W, double D, double mass, double time) {
    List<Particle> particles = new ArrayList<>();

    Color redColor = Color.RED;

    double startTime = System.currentTimeMillis();
    int id = 0;

    double minRadius = (D / 7) / 2;
    double maxRadius = (D / 5) / 2;

    while (System.currentTimeMillis() - startTime < time) {
      double radius = minRadius + Math.random() * (maxRadius - minRadius);
      radius = Math.max(.05, radius);
      Vector particlePosition = generateValidPosition(particles, W, L, radius);
      particles.add(new Particle(
              id,
              new Vector(particlePosition.getX(), particlePosition.getY()),
              new Vector(0, 0),
              new Vector(0, -9.8),
              radius,
              redColor,
              mass).calculatingOldPosition(Parameters.DELTA_TIME));

      id++;
    }

    return particles;
  }

  private static Vector generateValidPosition(List<Particle> particles, double W, double L, double radius) {
    while (true) {
      double x = radius + Math.random() * (W - 2 * radius);
      double y = 2 * radius + Math.random() * (L - 3 * radius);

      if (particles.size() == 0 || isValidPosition(particles, x, y, radius)) {
        return new Vector(x, y);
      }
    }
  }

  public static boolean isValidPosition(List<Particle> particles, double x, double y, double radius) {
    return particles.stream().noneMatch(particle -> {
      double x2 = particle.getPosition().getX();
      double y2 = particle.getPosition().getY();
      double r2 = particle.getRadius();

      return isCollision(x, y, radius, x2, y2, r2);
    });
  }

  private static boolean isCollision(double x1, double y1, double r1, double x2, double y2, double r2) {
    return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) < Math.pow(r1 + r2, 2);
  }

}
