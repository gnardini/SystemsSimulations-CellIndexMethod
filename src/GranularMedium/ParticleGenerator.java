package GranularMedium;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FranDepascuali on 10/9/16.
 */
public class ParticleGenerator {

  public static List<Particle> generateParticles(double L, double W, double radius, double mass, double time) {
    List<Particle> particles = new ArrayList<Particle>();

    double startTime = System.currentTimeMillis();
    int particle_number = 0;

    while (System.currentTimeMillis() - startTime < time) {
      System.out.println("Generating particle position");
      Point particlePosition = generateValidPosition(particles, W, L, radius);
      System.out.println("Particle position: " + particlePosition.getX() + ", "+ particlePosition.getY());
      particles.add(new Particle(
              particle_number,
              new Vector2D(particlePosition.getX(), particlePosition.getY()),
              new Vector2D(0, 0),
              new Vector2D(0, -9.8),
              radius,
              mass,
              1.0));

      particle_number++;
    }

    return particles;
  }

  private static boolean isValidPosition(double x1, double y1, double r1, double x2, double y2, double r2) {
    return Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) > Math.pow(r1 + r2, 2);
  }

  private static Point generateValidPosition(List<Particle> particles, double W, double L, double radius) {
    while (true) {

      double x = Math.random() * (W - 2 * radius) + radius;
      double y = Math.random() * (L - 2 * radius) + radius + 1;

      if (particles.size() == 0) {
        return new Point(x, y);
      }

      for (int j = 0; j < particles.size(); j++) {
        double x2 = particles.get(j).getPosition().getX();
        double y2 = particles.get(j).getPosition().getY();
        double r2 = particles.get(j).getRadius();

        if (isValidPosition(x, y, radius, x2, y2, r2)) {
          return new Point(x, y);
        }
      }
    }
  }
}
