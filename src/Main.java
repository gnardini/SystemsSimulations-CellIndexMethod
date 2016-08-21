import exercises.Exercise;
import input.InputFileReader;
import models.Board;
import models.Particle;
import output.XYZFormatter;

import java.util.List;

public class Main extends Exercise {
  public static void main(String[] args) {
    XYZFormatter formatter = new XYZFormatter();

    InputFileReader fileReader = new InputFileReader(STATIC_PATH, DYNAMIC_PATH);
    List<Particle> list = fileReader.getParticles();

    Board result;
    long start = System.currentTimeMillis();
    result = cellIndexMethodWithEdge(list, BOARD_M, fileReader.getL(), CONVERGENCE_RADIUS);
    long end = System.currentTimeMillis();
    long cellIndexMethodDuration = end - start;
    System.out.println("Cell index method with edges duration: " + cellIndexMethodDuration + " milliseconds");
    writeTo(CELL_INDEX_METHOD_OUTPUT, generateOutput(result));

    fileReader = new InputFileReader(STATIC_PATH, DYNAMIC_PATH);
    list = fileReader.getParticles();
    Board result2;
    start = System.currentTimeMillis();
    result2 = bruteForceMethodWithEdge(list, BOARD_M, fileReader.getL(), CONVERGENCE_RADIUS);
    end = System.currentTimeMillis();
    long bruteForceMethodDuration = end - start;
    System.out.println("Brute force method with edges duration: " + bruteForceMethodDuration + " milliseconds");
    writeTo(BRUTE_FORCE_OUTPUT, generateOutput(result2));

    List<String> lines = formatter.fformat(result, fileReader.getParticleCount(), 1, CONVERGENCE_RADIUS);
    writeTo(CELL_INDEX_METHOD_XYZ_OUTPUT, lines);
  }

}
