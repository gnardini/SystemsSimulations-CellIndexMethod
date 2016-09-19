package tp4;

import tp4.model.OscillatorInstant;
import tp4.oscillator.Oscillator;
import tp4.model.OscillatorState;

import java.util.LinkedList;
import java.util.List;

public class OscillatorCalculator {

    private final Oscillator oscillator;
    private final List<OscillatorInstant> instants;

    private OscillatorState state;

    private double totalError;
    private double steps;

    public OscillatorCalculator(Oscillator oscillator) {
        this.oscillator = oscillator;
        state = Parameters.initialState();
        instants = new LinkedList<>();
        instants.add(new OscillatorInstant(state));
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
}
