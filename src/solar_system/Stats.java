package solar_system;

import solar_system.model.Particle;
import solar_system.model.State;

import java.util.LinkedList;
import java.util.List;

public class Stats {

    private final FileManager fileManager;
    private List<String> distanceOverTime;
    private List<String> positionsOverTime;

    public Stats() {
        fileManager = new FileManager();
        distanceOverTime = new LinkedList<>();
        positionsOverTime = new LinkedList<>();
    }

    public void addDistance(double timeInDays, double distance) {
        distanceOverTime.add(timeInDays + "\t" + distance);
    }

    public void addPositions(State state) {
        positionsOverTime.add(
                particleToPositionString(state.getEarth()) + "\t"
                        + particleToPositionString(state.getMars()) + "\t"
                        + particleToPositionString(state.getShip()));
    }

    private String particleToPositionString(Particle particle) {
        return particle.getPosition().getX() + "\t" + particle.getPosition().getY();
    }

    public void printStats() {
        if (!distanceOverTime.isEmpty()) {
            fileManager.saveDistancesOverTime(distanceOverTime, Parameters.SHIP_INITIAL_ADDED_SPEED / Parameters.KM);
        }
        if (!positionsOverTime.isEmpty()) {
            fileManager.savePositionsOverTime(positionsOverTime);
        }
    }

}
