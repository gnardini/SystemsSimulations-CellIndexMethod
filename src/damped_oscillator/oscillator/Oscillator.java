package damped_oscillator.oscillator;

import damped_oscillator.model.OscillatorState;

public interface Oscillator {

    OscillatorState updateState(OscillatorState state, double time);

}
