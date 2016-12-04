package pedestrian_dynamics.models;

import pedestrian_dynamics.Parameters;
import pedestrian_dynamics.simulation.Simulation;

/**
 * Created by FranDepascuali on 12/3/16.
 */
public class HorizontalWall {

    private double _position;

    private Vector normalVector = new Vector(0, -1);

    public double getPosition() {
        return _position;
    }

    public HorizontalWall(double position) {
        _position = position;
    }


    public Vector getForce(Parameters parameters, Particle particle, double kn, double kt) {
        double overlap = horizontalWall(parameters, particle);
        if (overlap < 0) {
            return Simulation.socialForce(parameters, normalVector, overlap);
        }
        return Simulation.getForce(
                normalVector,
                new Vector(-normalVector.getY(), normalVector.getX()),
                particle.getSpeed(),
                overlap,
                kn,
                kt);
    }

    public double horizontalWall(Parameters parameters, Particle particle) {
        double x = particle.getX();
        if (isInRange(x, parameters.getW() / 2, parameters.getD() / 2)
                || particle.getY() < _position) {
            return 0;
        }
        return -(particle.getPosition().getY() - _position) + particle.getRadius();
    }

    private boolean isInRange(double value, double center, double difference) {
        double min = center - difference;
        double max = center + difference;
        return value >= min && value <= max;
    }
}
