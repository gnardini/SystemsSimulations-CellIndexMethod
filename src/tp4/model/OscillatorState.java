package tp4.model;

public class OscillatorState {

    private double gamma;
    private double mass;
    private double k;
    private double time;
    private double lastPosition;
    private double lastSpeed;
    private double previousAcceleration;

    public OscillatorState(double gamma, double mass, double k, double time, double lastPosition, double lastSpeed) {
        this(gamma, mass, k, time, lastPosition, lastSpeed,  (-k * lastPosition - gamma * lastSpeed) / mass);
    }

    public OscillatorState(double gamma, double mass, double k, double time, double lastPosition, double lastSpeed,
                           double previousAcceleration) {
        this.gamma = gamma;
        this.mass = mass;
        this.k = k;
        this.time = time;
        this.lastPosition = lastPosition;
        this.lastSpeed = lastSpeed;
        this.previousAcceleration = previousAcceleration;
    }

    public double getGamma() {
        return gamma;
    }

    public double getMass() {
        return mass;
    }

    public double getK() {
        return k;
    }

    public double getTime() {
        return time;
    }

    public double getLastPosition() {
        return lastPosition;
    }

    public double getLastSpeed() {
        return lastSpeed;
    }

    public double getPreviousAcceleration() {
        return previousAcceleration;
    }

    public OscillatorState withNewData(double newTime, double newPosition, double newSpeed) {
        return new OscillatorState(gamma, mass, k, newTime, newPosition, newSpeed, previousAcceleration);
    }

    public OscillatorState withNewData(double newTime, double newPosition, double newSpeed, double acceleration) {
        return new OscillatorState(gamma, mass, k, newTime, newPosition, newSpeed, acceleration);
    }
}
