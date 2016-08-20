package exercises;

import models.Particle;

import java.util.List;

public class Exercise2and3 extends Exercise {
  public static void main(String[] args) {
    int L = 20;
    int convergenceRadius = 1;
    double radius = 0.25;
    System.out.println("L: " + L + " rc: " + convergenceRadius + " r: " + radius);

    int runs = 10;

    int particleCount = 100;
    int particleCountDelta = 100;
    int M = 1;
    int MDelta = 1;

    List<Particle> particleList;

    for (int i = 0; i < runs; i++) {
      int currentM = M + MDelta * i;
      int currentParticleCount = particleCount;
      particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentM, radius);
      exercise2Run(particleList, currentParticleCount, currentM, L, convergenceRadius);
    }
    for (int i = 0; i < runs; i++) {
      int currentM = M;
      int currentParticleCount = particleCount + particleCountDelta * i;
      particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentM, radius);
      exercise2Run(particleList, currentParticleCount, currentM, L, convergenceRadius);
    }
    for (int i = 0; i < runs; i++) {
      int currentM = M + MDelta * i;
      int currentParticleCount = particleCount + particleCountDelta * i;
      particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentM, radius);
      exercise2Run(particleList, currentParticleCount, currentM, L, convergenceRadius);
    }
  }

  private static void exercise2Run(List<Particle> particleList, int particleCount, int M, int L, int convergenceRadius) {
    // Calculate using cell index method
    long start = System.currentTimeMillis();
    cellIndexMethodWithEdge(particleList, M, L, convergenceRadius);
    long end = System.currentTimeMillis();
    long cellIndexMethodDuration = end - start;

    // Now calculate using brute force method
    start = System.currentTimeMillis();
    bruteForceMethodWithEdge(particleList, M, L, convergenceRadius);
    end = System.currentTimeMillis();
    long bruteForceMethodDuration = end - start;

    System.out.println("M: " + M + " | Particles: " + particleCount + " | N/L^2: " + (particleCount / Math.pow(L, 2)) + "   ->   Cell index: " + cellIndexMethodDuration + " | Brute force: " + bruteForceMethodDuration);
  }
}
