package GranularMedium;

import GranularMedium.models.Particle;
import GranularMedium.models.State;
import GranularMedium.ui.Printer;

import java.util.List;

public class Main {

  private static int CREATION_TIME_MILLIS = 200;

  public static void main(String[] args) {
    Parameters parameters = new Parameters();
    List<Particle> particles = ParticleGenerator.generateParticles(
            Parameters.L, Parameters.W, Parameters.D, Parameters.PARTICLES_MASS, CREATION_TIME_MILLIS);

    State state = new State(particles, 10, parameters.getL() + 1, 0);

    Printer printer = new Printer(state);

    double time = 0;
    double totalTime = 100;

    int steps = 0;
    while (true) {//time < totalTime) {
      state = Simulation.simulateStep(state, parameters, Parameters.DELTA_TIME);
      time += Parameters.DELTA_TIME;
      steps++;
      if (steps % 1000 == 0) {
        printer.updateState(state);
      }
    }
  }

  private static void delay(long ms) {
    try {
      Thread.sleep(ms);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
