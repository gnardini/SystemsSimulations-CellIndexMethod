package algorithms;

import models.State;

public interface DistanceCalculator {

  void calculateDistanceWithEdges(State state);

  void calculateDistanceWithoutEdges(State state);

}
