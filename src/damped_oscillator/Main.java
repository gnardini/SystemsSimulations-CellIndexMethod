package damped_oscillator;

import damped_oscillator.oscillator.Oscillator;
import damped_oscillator.ui.OscillatorPrinter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static List<OscillatorCalculator> run(int totalTimes) {
        double time = 0;
        OscillatorCalculator analyticalData = new OscillatorCalculator(Parameters.getAnalyticalOscillator(), "analyticalData");
        OscillatorCalculator beemanData = new OscillatorCalculator(Parameters.getBeemanOscillator(), "beemanData");
        OscillatorCalculator gearPredictorData = new OscillatorCalculator(Parameters.getGearPredictorCorrectorOscillator(), "gearPredictorData");
        OscillatorCalculator verletData = new OscillatorCalculator(Parameters.getVerletOscillator(), "verletData");

        List<OscillatorCalculator> oscillators = new ArrayList<>();

        oscillators.add(analyticalData);
        oscillators.add(beemanData);
        oscillators.add(gearPredictorData);
        oscillators.add(verletData);

        while (time < totalTimes) {
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

//        new OscillatorPrinter(analyticalData.getInstants(), gearPredictorData.getInstants());
        return oscillators;
    }

    public static void main(String[] args) {
        run(Parameters.TOTAL_TIME);
    }


    public static void recordStadistics() {
        String directory = "octave/TP4/files/";

        Map<String, List<Double>> errors = new HashMap();

        for (int total_time = 1; total_time < 16; total_time++) {

            for (OscillatorCalculator oscillator: run(total_time)) {
                if (total_time == 1) {
                    errors.put(oscillator.getName(), new ArrayList());
                }

                errors.get(oscillator.getName()).add(oscillator.getError());

                if (total_time == 5) {
                    recordTo(directory + "oscillator_" + oscillator.getName() + total_time + ".txt", oscillator.getPositions());
                }
            }
        }

        for (Map.Entry error: errors.entrySet()) {
            recordTo(directory + "oscillator_errors_" + (String)error.getKey() + ".txt", (List<Double>) error.getValue());
        }
    }

    private static void recordTo(String path, List<Double> toRecord) {
        PrintWriter basicWriter = null;
        try {
            basicWriter = new PrintWriter(path, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        PrintWriter writer = new PrintWriter(basicWriter, true);

        for (int i = 0; i < toRecord.size(); i++) {
            writer.println(toRecord.get(i));
        }
    }

    private static void addPositionsTo(Map<String, List<Double>> map, OscillatorCalculator oscillatorCalculator, String key) {
        map.get(key).add(oscillatorCalculator.getLastPosition());
    }
}
