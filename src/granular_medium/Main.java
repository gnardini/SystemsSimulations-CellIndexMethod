package granular_medium;

import granular_medium.models.Particle;
import granular_medium.models.State;
import granular_medium.simulation.ParticleGenerator;
import granular_medium.simulation.Simulation;
import granular_medium.ui.Printer;
import granular_medium.ui.UiPrinter;

import java.util.List;

public class Main {

  private static double SIMULATION_TIME = 20;
  private static int CREATION_TIME_MILLIS = 200;
  private static int M = 20;

  public static void main(String[] args) {
//    makeVisualRun();
//    makeSilentRun();
    recordingStatistics();
  }

  private static void makeVisualRecordingRun() {
    Printer printer = new Printer() {};
    Stats stats = makeRunWithDefaultParticles(printer, new Parameters(6, 5, 2));
    List<State> stateList = stats.getStateList();
    printer = new UiPrinter();
    for (State state: stateList) {
      printer.updateState(state);
      delay(50);
    }
  }

  private static void delay(long millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception e) {

    }
  }

  private static void makeVisualRun() {
    Printer printer = new UiPrinter();
    makeRunWithDefaultParticles(printer, new Parameters(6, 4, 0));
  }

  private static void makeSilentRun() {
    Printer printer = new Printer() {};

//    makeRunWithDefaultParticles(printer, new Parameters(2, 1, .25));
//    makeRunWithDefaultParticles(printer, new Parameters(4, 1, .25));
    makeRunWithDefaultParticles(printer, new Parameters(6, 5, 2));
  }

  private static Stats makeRunWithDefaultParticles(Printer printer, Parameters parameters) {
    List<Particle> particles = ParticleGenerator.generateParticles(
            parameters.getL(), parameters.getW(), parameters.getD(), Parameters.PARTICLES_MASS, CREATION_TIME_MILLIS);
    return makeRun(particles, printer, parameters);
  }

  private static Stats makeRun(List<Particle> particles, Printer printer, Parameters parameters) {
    State state = new State(particles, M, parameters, Math.max(parameters.getD() / 5, .1));
    return run(parameters, state, printer);
  }

  private static void recordStatisticsWithHole(Printer printer, Parameters parameters) {
    List<Particle> particles = ParticleGenerator.generateParticles(
            parameters.getL(), parameters.getW(), parameters.getD(), Parameters.PARTICLES_MASS, CREATION_TIME_MILLIS);

    System.out.println("Running simulation with hole");
    Stats withHole = makeRun(particles, printer, parameters);
    particles.forEach(Particle::clearNeighbours);

    System.out.println("Running simulation without hole");
    Stats withoutHole = makeRun(particles, new Printer() {}, new Parameters(parameters.getL(), parameters.getW(), 0));

    System.out.println("Using D = 2: Max Energy: " + withHole.getEnergyOverTime().stream().mapToDouble(Double::doubleValue).max());
    System.out.println("Using D = 0: Max Energy: " + withoutHole.getEnergyOverTime().stream().mapToDouble(Double::doubleValue).max());

    FileManager.saveParticlesOverTime(withHole);
    FileManager.saveEnergy(withHole);

    FileManager.saveParticlesOverTime(withoutHole);
    FileManager.saveEnergy(withoutHole);
  }

  private static void recordingStatistics() {
    Printer printer = new Printer() {};

    recordStatisticsWithHole(printer, new Parameters(6, 4, 2));

//    Stats stats1 = makeRunWithDefaultParticles(printer, new Parameters(6, 5, 0));
//    Stats stats2 = makeRunWithDefaultParticles(printer, new Parameters(6, 5, 2));
//
//    System.out.println("Using D = 0: Max Energy: " + stats1.getEnergyOverTime().stream().mapToDouble(Double::doubleValue).max());
//    System.out.println("Using D = 2: Max Energy: " + stats2.getEnergyOverTime().stream().mapToDouble(Double::doubleValue).max());
//
//    FileManager.saveParticlesOverTime(stats);
//    FileManager.saveEnergy(stats);
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
        if (steps % 10000 == 0) {
          System.out.println(time + " " + state.calculateKineticEnergy());
        }
        printer.updateState(state);
      }
    }
    return stats;
  }

}
