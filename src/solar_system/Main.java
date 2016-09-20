package solar_system;

import solar_system.model.Particle;
import solar_system.model.State;
import solar_system.ui.SolarSystemPrinter;
import solar_system.verlet.VerletCalculator;

public class Main {

    public Main() {
        State state = Parameters.initialState();
        SolarSystemPrinter solarSystemPrinter = new SolarSystemPrinter();
        VerletCalculator verletCalculator = new VerletCalculator();

        double totalTime = 0;
        while (true) {
            state = verletCalculator.updateState(state, Parameters.TIME_STEP);
            solarSystemPrinter.setState(state);

            totalTime += Parameters.TIME_STEP;
            Particle ship = state.getShip();
            Particle mars = state.getMars();
            if (ship.collidesWith(mars)) {
                System.out.println("Collision! " + totalTime);
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
