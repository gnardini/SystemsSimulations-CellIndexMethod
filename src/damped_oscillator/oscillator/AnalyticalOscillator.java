package damped_oscillator.oscillator;

import damped_oscillator.model.OscillatorState;

public class AnalyticalOscillator implements Oscillator {

    //TODO: Figure out what this parameter is and rename it.
    private double gammaOverMass;

    public AnalyticalOscillator(double gamma, double mass) {
        gammaOverMass = gamma / (2 * mass);
    }

    @Override
    public OscillatorState updateState(OscillatorState state, double time) {
        double newPosition = Math.pow(Math.E, -gammaOverMass * time)
                * Math.cos(time * Math.pow(state.getK() / state.getMass() - gammaOverMass * gammaOverMass, .5));
        return state.withNewData(time, newPosition, 0);
    }
}
