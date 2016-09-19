import cell_index_method.exercises.Exercise;
import cell_index_method.input.InputFileReader;
import cell_index_method.models.Board;
import cell_index_method.models.Particle;
import cell_index_method.output.XYZFormatter;

import java.util.List;

public class Main extends Exercise {

  public static void main(String[] args) {
    XYZFormatter formatter = new XYZFormatter();
    int interestingParticleId = 1;

    InputFileReader fileReader = new InputFileReader(STATIC_PATH, DYNAMIC_PATH);
    List<Particle> list = fileReader.getParticles();

    Board cellIndexMethodBoard;
    long start = System.currentTimeMillis();
    cellIndexMethodBoard = cellIndexMethodWithEdge(list, BOARD_M, fileReader.getL(), 0, CONVERGENCE_RADIUS, 0);
    long end = System.currentTimeMillis();
    long cellIndexMethodDuration = end - start;
    System.out.println("Cell index method with edges duration: " + cellIndexMethodDuration + " milliseconds");
    // Generate normal cell_index_method.output
    writeTo(CELL_INDEX_METHOD_OUTPUT, generateOutput(cellIndexMethodBoard));
    // Generate XYZ cell_index_method.output
    List<String> lines = formatter.format(cellIndexMethodBoard, fileReader.getParticleCount(), interestingParticleId, CONVERGENCE_RADIUS);
    writeTo(CELL_INDEX_METHOD_XYZ_OUTPUT, lines);

    fileReader = new InputFileReader(STATIC_PATH, DYNAMIC_PATH);
    list = fileReader.getParticles();
    Board bruteForceBoard;
    start = System.currentTimeMillis();
    bruteForceBoard = bruteForceMethodWithEdge(list, BOARD_M, fileReader.getL(), 0, CONVERGENCE_RADIUS, 0);
    end = System.currentTimeMillis();
    long bruteForceMethodDuration = end - start;
    System.out.println("Brute force method with edges duration: " + bruteForceMethodDuration + " milliseconds");
    // Generate normal cell_index_method.output
    writeTo(BRUTE_FORCE_OUTPUT, generateOutput(bruteForceBoard));
    // Generate XYZ cell_index_method.output
    lines = formatter.format(bruteForceBoard, fileReader.getParticleCount(), interestingParticleId, CONVERGENCE_RADIUS);
    //writeTo(CELL_INDEX_METHOD_XYZ_OUTPUT, lines);
  }

}
