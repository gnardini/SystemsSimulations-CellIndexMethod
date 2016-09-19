package damped_oscillator.oscillator;

import damped_oscillator.model.OscillatorState;

public class BeemanOscillator implements Oscillator {

    @Override
    public OscillatorState updateState(OscillatorState state, double time) {
        double timeDiff = time - state.getTime();
        double acceleration = calculateAcceleration(state);

        double newPosition =
                state.getLastPosition()
                + state.getLastSpeed() * timeDiff
                + (2.0 / 3) * acceleration * timeDiff * timeDiff
                - (1.0 / 6) * state.getPreviousAcceleration() * timeDiff * timeDiff;

        double newSpeed =
                state.getLastSpeed()
                + (1.0 / 3) * (-state.getK() * newPosition / state.getMass()) * timeDiff
                + (5.0 / 6) * acceleration * timeDiff
                - (1.0 / 6) * state.getPreviousAcceleration() * timeDiff;
        newSpeed /= 1 + (state.getGamma() * timeDiff / (3 * state.getMass()));

        return state.withNewData(time, newPosition, newSpeed, acceleration);
    }

    private double calculateAcceleration(OscillatorState state) {
        return (- state.getK() * state.getLastPosition() - state.getGamma() * state.getLastSpeed()) / state.getMass();
    }

}
