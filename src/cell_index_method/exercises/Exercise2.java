package cell_index_method.exercises;

import cell_index_method.models.Particle;

import java.util.List;

public class Exercise2 extends Exercise {
  private static final int L = 20;
  private static final int interactionRadius = 1;
  private static final double radius = 0.25;

  public static void main(String[] args) {
    exercise2();
  }

  private static void exercise2() {
    System.out.println("L: " + L + " rc: " + interactionRadius + " r: " + radius);

    int runs = 13;

    int particleCount = 100;
    int particleCountDelta = 100;
    int M = 1;
    int MDelta = 1;

    List<Particle> particleList;

    for (int i = 0; i < runs; i++) {
      int currentM = M + MDelta * i;
      int currentParticleCount = particleCount;
      particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentM, radius);
      exercise2Run(particleList, currentParticleCount, currentM, L, interactionRadius);
    }
    for (int i = 0; i < runs; i++) {
      for (int j = 0; j < runs; j++) {
        int currentM = M + MDelta * i;
        int currentParticleCount = particleCount + particleCountDelta * j;
        particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentM, radius);
        exercise2Run(particleList, currentParticleCount, currentM, L, interactionRadius);
      }
    }
  }

  private static void exercise2Run(List<Particle> particleList, int particleCount, int M, int L, int interactionRadius) {
    // Calculate using cell index method
    long start = System.currentTimeMillis();
    cellIndexMethodWithEdge(particleList, M, L, 0, interactionRadius, 0);
    long end = System.currentTimeMillis();
    long cellIndexMethodDuration = end - start;

    // Now calculate using brute force method
    start = System.currentTimeMillis();
    bruteForceMethodWithEdge(particleList, M, L, 0, interactionRadius, 0);
    end = System.currentTimeMillis();
    long bruteForceMethodDuration = end - start;
    if(particleCount == 1300)
      System.out.println("M: " + M + " | Particles: " + particleCount + " | N/L^2: " + (particleCount / Math.pow(L, 2)) + "   ->   Cell index: " + cellIndexMethodDuration + " | Brute force: " + bruteForceMethodDuration);
  }

}
