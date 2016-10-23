package granular_medium;

import granular_medium.models.Particle;
import granular_medium.models.State;
import granular_medium.simulation.ParticleGenerator;
import granular_medium.simulation.Simulation;
import granular_medium.ui.Printer;
import granular_medium.ui.UiPrinter;

import java.util.List;

public class Main {

  private static double SIMULATION_TIME = 10;
  private static int CREATION_TIME_MILLIS = 200;
  private static int M = 10;

  public static void main(String[] args) {
//    makeVisualRun();
//    makeSilentRun();
    recordingStatistics();
  }

  private static void makeVisualRun() {
    Printer printer = new UiPrinter();
    makeRun(printer, new Parameters());
  }

  private static void makeSilentRun() {
    Printer printer = new Printer() {};

//    makeRun(printer, new Parameters(2, 1, .25));
//    makeRun(printer, new Parameters(4, 1, .25));
    makeRun(printer, new Parameters(6, 1, 2));
  }

  private static Stats makeRun(Printer printer, Parameters parameters) {
    List<Particle> particles = ParticleGenerator.generateParticles(
            parameters.getL(), parameters.getW(), parameters.getD(), Parameters.PARTICLES_MASS, CREATION_TIME_MILLIS);
    State state = new State(particles, M, parameters, 0);
    return run(parameters, state, printer);
  }

  private static void recordingStatistics() {
    Printer printer = new Printer() {};
    Stats stats = makeRun(printer, new Parameters(6, 4, 2));
    FileManager.saveParticlesOverTime(stats);
    FileManager.saveEnergy(stats);
  }

  private static Stats run(Parameters parameters, State state, Printer printer) {
    Stats stats = new Stats(state.particleCount, parameters);

    double time = 0;

    int steps = 0;
    while (time < SIMULATION_TIME && !stats.equilibriumReached()) {
      state = Simulation.simulateStep(state, parameters, Parameters.DELTA_TIME);
      time += Parameters.DELTA_TIME;
      stats.update(state, Parameters.DELTA_TIME);
      steps++;
      if (steps % 1000 == 0) {
//        System.out.println(time + " " + state.calculateKineticEnergy());
        printer.updateState(state);
      }
    }
    return stats;
  }

}
