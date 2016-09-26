package solar_system;

import java.util.LinkedList;
import java.util.List;

public class Stats {

    private final FileManager fileManager;
    private List<String> distanceOverTime;

    public Stats() {
        fileManager = new FileManager();
        distanceOverTime = new LinkedList<>();
    }

    public void addDistance(double distance) {
        distanceOverTime.add(String.valueOf(distance));
    }

    public void printStats() {
        fileManager.saveDistancesOverTime(distanceOverTime, Parameters.SHIP_INITIAL_ADDED_SPEED / Parameters.KM);
    }

}
