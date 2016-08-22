import exercises.Exercise;
import input.InputFileReader;
import models.Board;
import models.Particle;
import output.XYZFormatter;

import java.util.List;

public class Main extends Exercise {
  public static void main(String[] args) {
    XYZFormatter formatter = new XYZFormatter();
    int interestingParticleId = 1;

    InputFileReader fileReader = new InputFileReader(STATIC_PATH, DYNAMIC_PATH);
    List<Particle> list = fileReader.getParticles();

    Board cellIndexMethodBoard;
    long start = System.currentTimeMillis();
    cellIndexMethodBoard = cellIndexMethodWithEdge(list, BOARD_M, fileReader.getL(), CONVERGENCE_RADIUS);
    long end = System.currentTimeMillis();
    long cellIndexMethodDuration = end - start;
    System.out.println("Cell index method with edges duration: " + cellIndexMethodDuration + " milliseconds");
    // Generate normal output
    writeTo(CELL_INDEX_METHOD_OUTPUT, generateOutput(cellIndexMethodBoard));
    // Generate XYZ output
    List<String> lines = formatter.fformat(cellIndexMethodBoard, fileReader.getParticleCount(), interestingParticleId, CONVERGENCE_RADIUS);
    writeTo(CELL_INDEX_METHOD_XYZ_OUTPUT, lines);

    fileReader = new InputFileReader(STATIC_PATH, DYNAMIC_PATH);
    list = fileReader.getParticles();
    Board bruteForceBoard;
    start = System.currentTimeMillis();
    bruteForceBoard = bruteForceMethodWithEdge(list, BOARD_M, fileReader.getL(), CONVERGENCE_RADIUS);
    end = System.currentTimeMillis();
    long bruteForceMethodDuration = end - start;
    System.out.println("Brute force method with edges duration: " + bruteForceMethodDuration + " milliseconds");
    // Generate normal output
    writeTo(BRUTE_FORCE_OUTPUT, generateOutput(bruteForceBoard));
    // Generate XYZ output
    lines = formatter.fformat(bruteForceBoard, fileReader.getParticleCount(), interestingParticleId, CONVERGENCE_RADIUS);
    //writeTo(CELL_INDEX_METHOD_XYZ_OUTPUT, lines);
  }

}
