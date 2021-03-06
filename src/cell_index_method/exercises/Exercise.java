package cell_index_method.exercises;

import cell_index_method.algorithms.BruteForce;
import cell_index_method.algorithms.CellIndexMethod;
import cell_index_method.input.RandomInputGenerator;
import cell_index_method.models.Board;
import cell_index_method.models.Particle;
import cell_index_method.models.State;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Exercise {
  protected static final int BOARD_M = 10; // The amount of cells in the board is M * M
  protected static final int CONVERGENCE_RADIUS = 6;

  protected static final String RANDOM_INPUT_PATH = "files/randomInput.txt";
  protected static final String STATIC_PATH = "files/Static100.txt";
  protected static final String DYNAMIC_PATH = "files/Dynamic100.txt";

  protected static final String CELL_INDEX_METHOD_OUTPUT = "files/cimOutput.txt";
  protected static final String BRUTE_FORCE_OUTPUT = "files/bfOutput.txt";

  protected static final String CELL_INDEX_METHOD_XYZ_OUTPUT = "files/cimOutput.xyz";
  protected static final String BRUTE_FORCE_XYZ_OUTPUT = "files/bfOutput.xyz";

  protected static Board cellIndexMethodWithEdge(List<Particle> particles, int M, int L, double speed,
                                                 int interactionRadius, double eta) {
    State state = new State(particles, M, L, speed, interactionRadius, eta);
    CellIndexMethod cellIndexMethod = new CellIndexMethod();
    cellIndexMethod.calculateDistanceWithEdges(state);
    return state.getBoard();
  }

  protected static Board bruteForceMethodWithEdge(List<Particle> particles, int M, int L, double speed,
                                                  int interactionRadius, double eta) {
    State state = new State(particles, M, L, speed, interactionRadius, eta);
    BruteForce bruteForce = new BruteForce();
    bruteForce.calculateDistanceWithEdges(state);
    return state.getBoard();
  }

  public static void writeTo(String fileName, List<String> lines) {
    Path path = Paths.get(fileName);
    try {
      Files.write(path, lines, Charset.forName("UTF-8"));
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  public static List<String> readFrom(String fileName) {
    Path path = Paths.get(fileName);
    try {
      return Files.readAllLines(path, Charset.forName("UTF-8"));
    } catch (IOException ioException) {
      ioException.printStackTrace();
      return new LinkedList<>();
    }
  }

  protected static List<Particle> getParticlesFromRandomInput(String path, double radius) {
    List<Particle> list = new LinkedList<>();

    try {

      File file = new File(path);
      FileReader reader = new FileReader(file);
      BufferedReader buffer = new BufferedReader(reader);

      String line = buffer.readLine();

      while (line != null) {
        String[] properties = line.trim().split("\t");
        list.add(new Particle(
                Integer.valueOf(properties[0]),
                Double.valueOf(properties[1]),
                Double.valueOf(properties[2]),
                0));

        line = buffer.readLine();
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    return list;
  }

  protected static void generateRandomInput(String path, int particleCount, int M) {
    RandomInputGenerator.generateInput(path, particleCount, M);
  }

  protected static List<Particle> getParticlesFromNewRandomInput(String path, int particleCount, int M, double radius) {
    generateRandomInput(path, particleCount, M);
    return getParticlesFromRandomInput(path, radius);
  }

  protected static List<String> generateOutput(Board board) {
    List<String> answer = new LinkedList<>();
    for (int i = 0; i < board.getM(); i++) {
      for (int j = 0; j < board.getM(); j++) {
        List<Particle> particles = board.getCell(i, j).getParticles();
        for (Particle particle: particles) {
          answer.add(particle.toStringWithNeighbours());
        }
      }
    }
    return answer;
  }
}
