package solar_system;

import solar_system.model.State;
import solar_system.ui.SolarSystemPrinter;
import solar_system.verlet.VerletCalculator;

public class Main {

    public Main() {
        State state = Parameters.initialState();
        SolarSystemPrinter solarSystemPrinter = new SolarSystemPrinter();
        VerletCalculator verletCalculator = new VerletCalculator();

        while (true) {
            state = verletCalculator.updateState(state, Parameters.TIME_STEP);
            solarSystemPrinter.setState(state);
        }
    }

    private void delay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
