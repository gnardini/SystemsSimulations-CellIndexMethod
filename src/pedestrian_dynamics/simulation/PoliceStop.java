package pedestrian_dynamics.simulation;

import pedestrian_dynamics.models.Vector;

public class PoliceStop {

    private final double yPosition;
    private final double totalStoppedTime;

    private boolean stopPassed = false;
    private boolean continued = false;
    private double stoppedTime = 0;

    public PoliceStop(double yPosition, double totalStopTime) {
        this.yPosition = yPosition;
        this.totalStoppedTime = totalStopTime;
    }

    public boolean shouldStop(Vector position) {
        if (stopPassed) {
            return false;
        }
        if (position.getY() <= yPosition) {
            stopPassed = true;
            return true;
        }
        return false;
    }

    public boolean shouldContinue(double deltaTime) {
        if (!stopPassed || continued) {
            return false;
        }
        stoppedTime += deltaTime;
        continued = stoppedTime >= totalStoppedTime;
        return continued;
    }

}
