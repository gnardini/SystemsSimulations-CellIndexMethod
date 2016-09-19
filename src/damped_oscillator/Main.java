package damped_oscillator;

import damped_oscillator.ui.OscillatorPrinter;

public class Main {

    public Main() {
        double time = 0;
        OscillatorCalculator analyticalData = new OscillatorCalculator(Parameters.getAnalyticalOscillator());
        OscillatorCalculator beemanData = new OscillatorCalculator(Parameters.getBeemanOscillator());
        OscillatorCalculator gearPredictorData = new OscillatorCalculator(Parameters.getGearPredictorCorrectorOscillator());
        OscillatorCalculator verletData = new OscillatorCalculator(Parameters.getVerletOscillator());

        while (time < Parameters.TOTAL_TIME) {
            time += Parameters.TIME_STEP;
            analyticalData.update(time);
            double expectedPosition = analyticalData.getLastPosition();
            beemanData.update(time, expectedPosition);
            gearPredictorData.update(time, expectedPosition);
            verletData.update(time, expectedPosition);
        }

        System.out.println("Beeman Error: " + beemanData.getError());
        System.out.println("GPD Error: " + gearPredictorData.getError());
        System.out.println("Verlet Error: " + verletData.getError());

        new OscillatorPrinter(analyticalData.getInstants(), gearPredictorData.getInstants());
    }

    public static void main(String[] args) {
        new Main();
    }
}
