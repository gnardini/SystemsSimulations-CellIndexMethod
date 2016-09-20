package damped_oscillator;

import damped_oscillator.model.OscillatorInstant;
import damped_oscillator.oscillator.Oscillator;
import damped_oscillator.model.OscillatorState;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OscillatorCalculator {

    private final Oscillator oscillator;
    private final List<OscillatorInstant> instants;

    private OscillatorState state;

    private double totalError;
    private double steps;

    private String name;

    public OscillatorCalculator(Oscillator oscillator, String name) {
        this.oscillator = oscillator;
        state = Parameters.initialState();
        instants = new LinkedList<>();
        instants.add(new OscillatorInstant(state));
        this.name = name;
    }

    public void update(double time) {
        state = oscillator.updateState(state, time);
        instants.add(new OscillatorInstant(state));
    }

    public void update(double time, double expectedPosition) {
        state = oscillator.updateState(state, time);
        instants.add(new OscillatorInstant(state));

        totalError += Math.pow(expectedPosition - state.getLastPosition(), 2);
        steps ++;
    }

    public double getError() {
        return totalError / steps;
    }

    public List<OscillatorInstant> getInstants() {
        return instants;
    }

    public double getLastPosition() {
        return state.getLastPosition();
    }

    public String getName() {
        return name;
    }

    public List<Double> getPositions() {
        return instants.stream().map(instant -> instant.getPosition()).collect(Collectors.toList());
    }
}
