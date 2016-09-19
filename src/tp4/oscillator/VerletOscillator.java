package tp4.oscillator;

import tp4.model.OscillatorState;

public class VerletOscillator implements Oscillator {

    @Override
    public OscillatorState updateState(OscillatorState state, double time) {
        double timeDiff = time - state.getTime();
        double force = calculateForce(state);

        double newPosition =
                state.getLastPosition()
                + state.getLastSpeed() * timeDiff
                + force * timeDiff * timeDiff / state.getMass();

        double newSpeed =
                state.getLastSpeed()
                + (timeDiff / (2 * state.getMass()))
                        * (state.getPreviousAcceleration() - state.getK() * newPosition);
        newSpeed /= 1 + state.getGamma() * timeDiff / (2 * state.getMass());

        return state.withNewData(time, newPosition, newSpeed, force);
    }

    private double calculateForce(OscillatorState state) {
        return - state.getK() * state.getLastPosition() - state.getGamma() * state.getLastSpeed();
    }

}
