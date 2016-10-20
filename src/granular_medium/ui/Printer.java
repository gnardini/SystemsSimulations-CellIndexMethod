package granular_medium.ui;

import granular_medium.models.State;

public interface Printer {

    default void updateState(State state) {}

}
