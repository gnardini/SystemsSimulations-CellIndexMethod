package algorithms;

import models.Particle;

import java.util.List;

public class BruteForce extends DistanceCalculator{

  public BruteForce(List<Particle> particles, int boardSize, int cellSize, int convergenceRadius) {
    super(particles, boardSize, cellSize, convergenceRadius);
  }

  @Override
  public void calculateDistanceWithEdge() {
    // TODO: WRITE METHOD
  }

  @Override
  public void calculateDistanceWithoutEdge() {
    // TODO: WRITE METHOD
  }
}
