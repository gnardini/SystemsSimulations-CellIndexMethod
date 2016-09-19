package tp4.oscillator;

import tp4.model.OscillatorState;

public interface Oscillator {

    OscillatorState updateState(OscillatorState state, double time);

}
