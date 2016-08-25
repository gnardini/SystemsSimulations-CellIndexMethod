package algorithms;

import models.Particle;
import models.State;

import java.util.List;

public class BruteForce implements DistanceCalculator{

  @Override
  public void calculateDistanceWithEdges(State state) {
    List<Particle> particles = state.getParticles();

    for (int i = 0; i < particles.size(); i++) {
      for (int j = i; j < particles.size(); j++) {
        Particle particle1 = particles.get(i);
        Particle particle2 = particles.get(j);
        if(particle1.getId() != particle2.getId()) {
          if(particle1.isInRadius(particle2, state.getInteractionRadius())) {
            particle1.addNeighbour(particle2);
            particle2.addNeighbour(particle1);
          }
        }
      }
    }
  }

  @Override
  public void calculateDistanceWithoutEdges(State state) {
    // TODO: WRITE METHOD
  }
}
