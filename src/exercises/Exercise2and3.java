package exercises;

import models.Particle;

import java.util.List;

public class Exercise2and3 extends Exercise {
  public static void main(String[] args) {
    int cellSize = 20;
    int convergenceRadius = 1;
    double radius = 0.25;
    System.out.println("L: " + cellSize + " rc: " + convergenceRadius + " r: " + radius);

    int runs = 10;

    int particleCount = 100;
    int particleCountDelta = 100;
    int boardM = 1;
    int boardMDelta = 1;

    List<Particle> particleList;

    for (int i = 0; i < runs; i++) {
      int currentBoardM = boardM + boardMDelta * i;
      int currentParticleCount = particleCount;
      particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentBoardM);
      for (Particle particle: particleList) {
        particle.setRadius(radius);
      }
      exercise2Run(particleList, currentParticleCount, currentBoardM, cellSize, convergenceRadius);
    }
    for (int i = 0; i < runs; i++) {
      int currentBoardM = boardM;
      int currentParticleCount = particleCount + particleCountDelta * i;
      particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentBoardM);
      for (Particle particle: particleList) {
        particle.setRadius(radius);
      }
      exercise2Run(particleList, currentParticleCount, currentBoardM, cellSize, convergenceRadius);
    }
    for (int i = 0; i < runs; i++) {
      int currentBoardM = boardM + boardMDelta * i;
      int currentParticleCount = particleCount + particleCountDelta * i;
      particleList = getParticlesFromNewRandomInput(RANDOM_INPUT_PATH, currentParticleCount, currentBoardM);
      for (Particle particle: particleList) {
        particle.setRadius(radius);
      }
      exercise2Run(particleList, currentParticleCount, currentBoardM, cellSize, convergenceRadius);
    }
  }

  private static void exercise2Run(List<Particle> particleList, int particleCount, int boardM, int cellSize, int convergenceRadius) {
    // Calculate using cell index method
    long start = System.currentTimeMillis();
    cellIndexMethodWithEdge(particleList, boardM, cellSize, convergenceRadius);
    long end = System.currentTimeMillis();
    long cellIndexMethodDuration = end - start;

    // Now calculate using brute force method
    start = System.currentTimeMillis();
    bruteForceMethodWithEdge(particleList, boardM, cellSize, convergenceRadius);
    end = System.currentTimeMillis();
    long bruteForceMethodDuration = end - start;

    System.out.println("M: " + boardM + " | Particles: " + particleCount + " | N/L^2: " + (particleCount / Math.pow(cellSize, 2)) + "   ->   Cell index: " + cellIndexMethodDuration + " | Brute force: " + bruteForceMethodDuration);
  }
}
