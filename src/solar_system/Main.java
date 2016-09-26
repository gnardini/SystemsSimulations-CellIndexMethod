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

        double minDistance = Double.MAX_VALUE;

        double totalTime = 0;
        while (true) {
            state = verletCalculator.updateState(state, Parameters.TIME_STEP);
            solarSystemPrinter.setState(state);

            totalTime += Parameters.TIME_STEP;
            Particle ship = state.getShip();
            Particle mars = state.getMars();

            double distance = ship.getPosition().subtract(mars.getPosition()).norm();
            if (distance < minDistance) {
              minDistance = distance;
              System.out.println("Minimum distance found: " + minDistance);
              System.out.println("Time: " + totalTime);
            }
//            if (distance <= 1.8301188019069897E10) {
//              System.out.println("Time: " + totalTime);
//              System.out.println("Distance: " + distance);
//            }
//            if (ship.collidesWith(mars)) {
//                System.out.println("Collision! " + totalTime);
//                System.out.println("Distance: " + distance);
//            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
