package solar_system.model;

public class SimulationResult {

    private final double travelTime;
    private final double distanceToMars;

    public SimulationResult(double travelTime, double distanceToMars) {
        this.travelTime = travelTime;
        this.distanceToMars = distanceToMars;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public double getDistanceToMars() {
        return distanceToMars;
    }

    public boolean collidedWithMars() {
        return distanceToMars == 0;
    }

}
