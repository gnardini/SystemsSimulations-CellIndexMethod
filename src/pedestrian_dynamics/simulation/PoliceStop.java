package pedestrian_dynamics.simulation;

import pedestrian_dynamics.Parameters;
import pedestrian_dynamics.models.Vector;

public class PoliceStop {

    private final double yPosition;
    private final double totalStoppedTime;
    private final int sectionIndex;

    private boolean stopPassed = false;
    private boolean continued = false;
    private double stoppedTime = 0;

    public PoliceStop(double yPosition, double totalStopTime, int sectionIndex) {
        this.yPosition = yPosition;
        this.totalStoppedTime = totalStopTime;
        this.sectionIndex = sectionIndex;
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

    public boolean shouldContinue(Parameters parameters, int[] peopleInSections) {
        if (!stopPassed || continued) {
            return false;
        }
        stoppedTime += parameters.getDeltaTime();
        continued = stoppedTime >= totalStoppedTime
                && (sectionIndex == 0
                || peopleInSections[sectionIndex - 1] < parameters.getMaxPeoplePerSection()[sectionIndex - 1]);
        return continued;
    }

}
