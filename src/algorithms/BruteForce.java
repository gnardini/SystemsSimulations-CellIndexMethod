package algorithms;

import models.Particle;

import java.util.List;

public class BruteForce extends DistanceCalculator{

  public BruteForce(List<Particle> particles, int boardSize, int cellSize, int convergenceRadius) {
    super(particles, boardSize, cellSize, convergenceRadius);
  }

  @Override
  public void calculateDistanceWithEdge() {
    for (int i = 0; i < particles.size(); i++) {
      for (int j = i; j < particles.size(); j++) {
        Particle particle1 = particles.get(i);
        Particle particle2 = particles.get(j);
        if(particle1.getId() != particle2.getId()) {
          if(particle1.isInRadius(particle2, convergenceRadius)) {
            particle1.addNeighbour(particle2);
            particle2.addNeighbour(particle1);
          }
        }
      }
    }
  }

  @Override
  public void calculateDistanceWithoutEdge() {
    // TODO: WRITE METHOD
  }
}
