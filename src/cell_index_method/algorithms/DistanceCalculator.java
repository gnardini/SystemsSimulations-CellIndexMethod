package cell_index_method.algorithms;

import cell_index_method.models.State;

public interface DistanceCalculator {

  void calculateDistanceWithEdges(State state);

  void calculateDistanceWithoutEdges(State state);

}
