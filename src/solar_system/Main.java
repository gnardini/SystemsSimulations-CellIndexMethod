package solar_system;

import solar_system.model.Particle;
import solar_system.model.State;
import solar_system.ui.SolarSystemPrinter;
import solar_system.verlet.VerletCalculator;

// Mejor dia con v0 = 3 -> 570 * ONE_DAY
// Mejor dia con v0 = 8 -> 580 * ONE_DAY - Profesor: 575.9

public class Main {

    private static final double ONE_DAY = 24 * 60 * 60;
    private static final double ONE_WEEK = 7 * ONE_DAY;
    private static final double ONE_YEAR = 365 * ONE_DAY;

    private static final boolean RUN_SINGLE_SIMULATION = false;
    private static final double SINGLE_SIMULATION_DAY = 575.9 * ONE_DAY;
    private static final double OFFSET_STEP = 20 * ONE_DAY;

    private static final double MIN_DAY = 0;
    private static final double MAX_DAY = 2 * ONE_YEAR;

    public Main() {
        State initialState = Parameters.initialState();
        Stats stats = new Stats();

        double minDay;
        double minDistance;

        if (RUN_SINGLE_SIMULATION) {
            minDay = SINGLE_SIMULATION_DAY;
            minDistance = playSimulation(initialState, SINGLE_SIMULATION_DAY, true);
        } else {
            double totalTime = MIN_DAY;
            minDistance = Double.MAX_VALUE;
            minDay = 0;

            while (totalTime <= MAX_DAY) {
                double distance = playSimulation(initialState, totalTime, false);
                stats.addDistance(distance);
                System.out.println("Day " + (totalTime / ONE_DAY) + ": " + distance);
                if (distance < minDistance) {
                    minDay = totalTime / ONE_DAY;
                    minDistance = distance;
                    System.out.println("NEW MINIMUM = Day " + (totalTime / ONE_DAY) + ": " + distance);
                }
                totalTime += OFFSET_STEP;
            }

            stats.printStats();
        }

        System.out.println(minDay + ": " + minDistance);
    }

    public double playSimulation(State state, double timeOffset, boolean print) {
        VerletCalculator verletCalculator = new VerletCalculator();
        SolarSystemPrinter solarSystemPrinter = null;
        if (print) {
            solarSystemPrinter = new SolarSystemPrinter();
        }

        double maxTime = ONE_YEAR + timeOffset;

        double totalTime = 0;
        double minDistance = Double.MAX_VALUE;
        boolean launched = false;

        while (totalTime <= maxTime) {
            if (!launched && totalTime >= timeOffset) {
                state = state.launchShip();
                launched = true;
            }

            if (print) {
                solarSystemPrinter.setState(state);
            }

            state = verletCalculator.updateState(state, Parameters.TIME_STEP);

            totalTime += Parameters.TIME_STEP;
            Particle ship = state.getShip();
            if (ship.isLaunched()) {
                Particle mars = state.getMars();

                double distance = ship.getPosition().distanceTo(mars.getPosition()) - ship.getRadius() - mars.getRadius();
                if (distance < minDistance) {
                    minDistance = distance;
                }
                if (ship.collidesWith(mars)) {
                    System.out.println("Collision! " + totalTime);
                }
            }
        }
        return minDistance;
    }

    public static void main(String[] args) {
        new Main();
    }

}
