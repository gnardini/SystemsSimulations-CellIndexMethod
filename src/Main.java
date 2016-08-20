import exercises.Exercise;
import input.InputFileReader;
import input.RandomInputGenerator;
import models.Board;
import models.Particle;
import output.XYZFormatter;

import java.util.List;

public class Main extends Exercise {


  public static void main(String[] args) {
    int particleCount = 1000;
    RandomInputGenerator.generateInput(RANDOM_INPUT_PATH, particleCount, BOARD_M);

    XYZFormatter formatter = new XYZFormatter();

    InputFileReader fileReader = new InputFileReader(STATIC_PATH, DYNAMIC_PATH);
    List<Particle> list = fileReader.getParticles();

    // Start calculating times
    Board result;
    long start = System.currentTimeMillis();
    result = cellIndexMethodWithEdge(list, BOARD_M, fileReader.getCellSize(), CONVERGENCE_RADIUS);
    long end = System.currentTimeMillis();
    long cellIndexMethodDuration = end - start;
    System.out.println("Cell index method with edges duration: " + cellIndexMethodDuration + " milliseconds");

    start = System.currentTimeMillis();
    result = bruteForceMethodWithEdge(list, BOARD_M, fileReader.getCellSize(), CONVERGENCE_RADIUS);
    end = System.currentTimeMillis();
    long bruteForceMethodDuration = end - start;
    System.out.println("Brute force method with edges duration: " + bruteForceMethodDuration + " milliseconds");

    List<String> lines = formatter.fformat(result, fileReader.getParticleCount(), 1, CONVERGENCE_RADIUS);
    writeTo(CELL_INDEX_METHOD_XYZ_OUTPUT, lines);
  }

}
