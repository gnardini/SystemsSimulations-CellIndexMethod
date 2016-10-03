package damped_oscillator.oscillator;

import damped_oscillator.model.OscillatorState;

public class GearPredictorCorrectorOscillator implements Oscillator {

    private static final int[] FACTORIAL = { 1, 1, 2, 6, 24, 120 };
    private static final double[] ALPHA = { 3.0 / 16, 251.0 / 360, 1.0, 11.0 / 18, 1.0 / 6, 1.0 / 60 };

    @Override
    public OscillatorState updateState(OscillatorState state, double time) {
        double deltaTime = time - state.getTime();

        double[] predictions = predict(state, deltaTime);

        double deltaR2 = calculateDeltaR2(state, predictions, deltaTime);

        double[] correctedPositions = correctPredictions(predictions, deltaR2, deltaTime);

        return state.withNewData(time, correctedPositions);
    }

    private double[] predict(OscillatorState state, double deltaTime) {
        double[] previousPositions = state.getGearPositions();
        double[] predictions = new double[6];
        for (int i = 0; i < 6; i++) {
            for (int j = i; j < 6; j++) {
                predictions[i] += previousPositions[j] * Math.pow(deltaTime, j - i) / FACTORIAL[j - i];
            }
        }
        return predictions;
    }

    private double calculateDeltaR2(OscillatorState state, double[] predictions, double deltaTime) {
        double deltaAcceleration =
                calculateAcceleration(state, predictions[0], predictions[1]) - predictions[2];
        return deltaAcceleration * deltaTime * deltaTime / FACTORIAL[2];
    }

    private double[] correctPredictions(double[] predictions, double deltaR2, double deltaTime) {
        double[] correctedPositions = new double[6];
        for (int i = 0; i < 6; i++) {
            correctedPositions[i] = predictions[i] + ALPHA[i] * deltaR2 * FACTORIAL[i] / Math.pow(deltaTime, i);
        }
        return correctedPositions;
    }

    private double calculateAcceleration(OscillatorState state, double position, double velocity) {
        return (-state.getK() * position - state.getGamma() * velocity) / state.getMass();
    }

}
