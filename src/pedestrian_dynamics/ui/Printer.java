package pedestrian_dynamics.ui;

import pedestrian_dynamics.models.State;

public interface Printer {

    default void updateState(State state) {
    }

}
