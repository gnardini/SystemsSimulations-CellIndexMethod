package damped_oscillator.model;

public class OscillatorState {

    private double gamma;
    private double mass;
    private double k;
    private double time;
    private double lastPosition;
    private double lastSpeed;
    private double previousAcceleration;

    private double[] gearPositions;

    public OscillatorState(double gamma, double mass, double k, double time, double lastPosition, double lastSpeed) {
        this(gamma, mass, k, time, lastPosition, lastSpeed,  (-k * lastPosition - gamma * lastSpeed) / mass, new double[6]);
    }

    public OscillatorState(double gamma, double mass, double k, double time, double lastPosition, double lastSpeed,
                           double previousAcceleration) {
        this(gamma, mass, k, time, lastPosition, lastSpeed,  previousAcceleration, new double[6]);
    }

    public OscillatorState(double gamma, double mass, double k, double time, double lastPosition, double lastSpeed,
                           double[] gearPositions) {
        this(gamma, mass, k, time, lastPosition, lastSpeed,  (-k * lastPosition - gamma * lastSpeed) / mass, gearPositions);
    }

    public OscillatorState(double gamma, double mass, double k, double time, double lastPosition, double lastSpeed,
                           double previousAcceleration, double[] gearPositions) {
        this.gamma = gamma;
        this.mass = mass;
        this.k = k;
        this.time = time;
        this.lastPosition = lastPosition;
        this.lastSpeed = lastSpeed;
        this.previousAcceleration = previousAcceleration;
        this.gearPositions = gearPositions;
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

    public double[] getGearPositions() {
        return gearPositions;
    }

    public OscillatorState withNewData(double newTime, double newPosition, double newSpeed) {
        return new OscillatorState(gamma, mass, k, newTime, newPosition, newSpeed, previousAcceleration);
    }

    public OscillatorState withNewData(double newTime, double newPosition, double newSpeed, double acceleration) {
        return new OscillatorState(gamma, mass, k, newTime, newPosition, newSpeed, acceleration);
    }

    public OscillatorState withNewData(double newTime, double[] newPositions) {
        return new OscillatorState(gamma, mass, k, newTime, newPositions[0], newPositions[1], newPositions);
    }
}
