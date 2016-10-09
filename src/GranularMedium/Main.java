package GranularMedium;

import com.sun.xml.internal.rngom.digested.DDataPattern;

import java.util.List;

/**
 * Created by FranDepascuali on 10/8/16.
 */
public class Main {

  private static int L = 3;
  private static int W = 2;
  private static double D = 0;

  private static double particlesRadius = D / 10 / 2;
  private static double particleMass = 0.01;

  public static void main(String[] args) {

    Parameters parameters = new Parameters(L, W, D, particlesRadius);

    particlesRadius = particlesRadius == 0 ? 0.05 : particlesRadius;
    List<Particle> particles = ParticleGenerator.generateParticles(L, W, particlesRadius, particleMass, 20);



  }

}
