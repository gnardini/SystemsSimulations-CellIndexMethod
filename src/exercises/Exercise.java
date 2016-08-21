package exercises;

import algorithms.BruteForce;
import algorithms.CellIndexMethod;
import input.RandomInputGenerator;
import models.Board;
import models.Particle;

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
  protected static final String STATIC_PATH = "files/static100.txt";
  protected static final String DYNAMIC_PATH = "files/dynamic100.txt";

  protected static final String CELL_INDEX_METHOD_OUTPUT = "files/cimOutput.txt";
  protected static final String BRUTE_FORCE_OUTPUT = "files/bfOutput.txt";

  protected static final String CELL_INDEX_METHOD_XYZ_OUTPUT = "files/cimOutput.xyz";
  protected static final String BRUTE_FORCE_XYZ_OUTPUT = "files/bfOutput.xyz";

  protected static Board cellIndexMethodWithEdge(List<Particle> list, int M, int L, int convergenceRadius) {
    CellIndexMethod cellIndexMethod = new CellIndexMethod(list, M, L, convergenceRadius);
    cellIndexMethod.calculateDistanceWithEdge();
    return cellIndexMethod.getBoard();
  }

  protected static Board bruteForceMethodWithEdge(List<Particle> list, int M, int L, int convergenceRadius) {
    BruteForce bruteForce = new BruteForce(list, M, L, convergenceRadius);
    bruteForce.calculateDistanceWithEdge();
    return bruteForce.getBoard();
  }

  protected static void writeTo(String fileName, List<String> lines) {
    Path path = Paths.get(fileName);
    try {
      Files.write(path, lines, Charset.forName("UTF-8"));
    } catch (IOException ioException) {
      ioException.printStackTrace();
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
        list.add(new Particle(Integer.valueOf(properties[0]), Double.valueOf(properties[1]), Double.valueOf(properties[2]), radius));

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
