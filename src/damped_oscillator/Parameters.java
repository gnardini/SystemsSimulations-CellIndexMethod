package damped_oscillator;

import damped_oscillator.oscillator.AnalyticalOscillator;
import damped_oscillator.oscillator.BeemanOscillator;
import damped_oscillator.model.OscillatorState;
import damped_oscillator.oscillator.GearPredictorCorrectorOscillator;
import damped_oscillator.oscillator.VerletOscillator;

public class Parameters {

    static double MASS = 70;
    static double K = 10000;
    static double GAMMA = 100;
    static double TOTAL_TIME = 5;
    static double TIME_STEP = 0.0001;

    static double INITIAL_POSITION = 1;
    static double INITIAL_SPEED = -GAMMA / (2 * MASS);

    static AnalyticalOscillator getAnalyticalOscillator() {
        return new AnalyticalOscillator(GAMMA, MASS);
    }

    static BeemanOscillator getBeemanOscillator() {
        return new BeemanOscillator();
    }

    static GearPredictorCorrectorOscillator getGearPredictorCorrectorOscillator() {
        return new GearPredictorCorrectorOscillator();
    }

    static VerletOscillator getVerletOscillator() {
        return new VerletOscillator();
    }

    static OscillatorState initialState() {
        return new OscillatorState(GAMMA, MASS, K, 0, INITIAL_POSITION, INITIAL_SPEED, initialGearPositions());
    }

    static double[] initialGearPositions() {
        double[] initialPositions = new double[6];
        initialPositions[0] = INITIAL_POSITION;
        initialPositions[1] = INITIAL_SPEED;
        for (int i = 2; i < 6; i++) {
            initialPositions[i] = (-K * initialPositions[i - 2] - GAMMA * initialPositions[i - 1]) / MASS;
        }
        return initialPositions;
    }
}
