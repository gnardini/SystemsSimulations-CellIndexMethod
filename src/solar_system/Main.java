package solar_system;

import solar_system.model.Particle;
import solar_system.model.SimulationResult;
import solar_system.model.State;
import solar_system.ui.SolarSystemPrinter;
import solar_system.verlet.VerletCalculator;

// Mejor dia con v0 = 3 -> 565.12 * ONE_DAY
// Mejor dia con v0 = 8 -> 586.11 * ONE_DAY - Profesor: 575.9
// Mejor angulo con v0 = 3 -> 0.82535 * Math.PI
// Mejor angulo con v0 = 30 -> 0.90553 * Math.PI - Travel time: 20.068171296296295

public class Main {

    private static final double ONE_DAY = 24 * 60 * 60;
    private static final double ONE_WEEK = 7 * ONE_DAY;
    private static final double ONE_YEAR = 365 * ONE_DAY;

    private static final boolean RUN_SINGLE_SIMULATION = true;
    private static final double SINGLE_SIMULATION_DAY = 565.12 * ONE_DAY;
    private static final double SINGLE_SIMULATION_ANGLE = 0.82535 * Math.PI;
    private static final double OFFSET_STEP = 10 * ONE_DAY;

    private static final double MIN_DAY = 0 * ONE_DAY;
    private static final double MAX_DAY = 2 * ONE_YEAR;

    private static final double MIN_ANGLE = 0 * Math.PI;
    private static final double MAX_ANGLE = 2 * Math.PI;
    private static final double ANGLE_STEP = .1 * Math.PI;

    public Main() {
        State initialState = Parameters.initialState();

        if (RUN_SINGLE_SIMULATION) {
//            playSimulation(initialState.withShipStartingAngle(SINGLE_SIMULATION_ANGLE), 0, true);
            playSimulation(initialState, SINGLE_SIMULATION_DAY, true);
        } else {
            runSimulationSetWithTimeOffset(initialState);
//            runSimulationSetWithInitialAngles(initialState);
        }
    }

    private double runSimulationSetWithTimeOffset(State initialState) {
        double totalTime = MIN_DAY;
        double minDistance = Double.MAX_VALUE;
        double minDay = 0;
        Stats stats = new Stats();

        while (totalTime <= MAX_DAY) {
            double distance = playSimulation(initialState, totalTime, false).getDistanceToMars();
            stats.addDistance(totalTime / ONE_DAY, distance);
            System.out.println("Day " + (totalTime / ONE_DAY) + ": " + distance);
            if (distance < minDistance) {
                minDay = totalTime / ONE_DAY;
                minDistance = distance;
                System.out.println("NEW MINIMUM = Day " + (totalTime / ONE_DAY) + ": " + distance);
            }
            totalTime += OFFSET_STEP;
        }

        stats.printStats();
        return minDistance;
    }

    private double runSimulationSetWithInitialAngles(State initialState) {
        double totalAngle = MIN_ANGLE;
        double minDistance = Double.MAX_VALUE;
        Stats stats = new Stats();

        while (totalAngle <= MAX_ANGLE) {
            double distance = playSimulation(initialState.withShipStartingAngle(totalAngle), 0, false).getDistanceToMars();
            System.out.println("Angle: " + (totalAngle / Math.PI) + ": " + distance);
            if (distance < minDistance) {
                minDistance = distance;
                System.out.println("NEW MINIMUM = Angle " + (totalAngle / Math.PI) + ": " + distance);
            }
            totalAngle += ANGLE_STEP;
        }

        return minDistance;
    }

    private SimulationResult playSimulation(State state, double timeOffset, boolean print) {
        Stats stats = new Stats();
        VerletCalculator verletCalculator = new VerletCalculator();
        SolarSystemPrinter solarSystemPrinter = null;
        if (print) {
            solarSystemPrinter = new SolarSystemPrinter();
        }

        double maxTime = ONE_YEAR + timeOffset;

        double totalTime = 0;
        double minDistance = Double.MAX_VALUE;
        boolean launched = state.getShip().isLaunched();

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
                if (totalTime % 1000 == 0) {
                    stats.addPositions(state);
                }
                Particle mars = state.getMars();

                double distance = ship.getPosition().distanceTo(mars.getPosition()) - ship.getRadius() - mars.getRadius();
                if (distance < minDistance) {
                    minDistance = distance;
                }
                if (ship.collidesWith(mars)) {
                    System.out.println("Collision! Travel time: " + (totalTime - timeOffset) / ONE_DAY
                        + "days. Speed: " + ship.getSpeed().norm() / Parameters.KM + " km/s");
                    stats.printStats();
                    return new SimulationResult(totalTime - timeOffset, 0);
                }
            }
        }
        stats.printStats();
        return new SimulationResult(ONE_YEAR, minDistance);
    }

    public static void main(String[] args) {
        new Main();
    }

}
