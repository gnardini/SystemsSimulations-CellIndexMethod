import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Test {
  private static final int PARTICLE_COUNT = 1000;
  private static final int BOARD_SIZE = 100;
  private static final int CELL_SIZE = 10;
  private static final int CONVERGENCE_RADIUS = 10;

  public static void main(String[] args) {
    // Create board
    Board board = new Board(BOARD_SIZE, CELL_SIZE, CONVERGENCE_RADIUS);
    // Add random particles to the board
    for (int i = 0; i < PARTICLE_COUNT; i++) {
      board.addRandomParticle(i);
    }

    board.updateCells();

    List<String> sb = new LinkedList<>();
    sb.add(String.valueOf(PARTICLE_COUNT));
    sb.add("This is a comment");

    for (int i = 0; i < board.getCellCount(); i++) {
      for (int j = 0; j < board.getCellCount(); j++) {
        //System.out.println();
        //System.out.println("New position: " + i + ", " + j);
        for (Particle particle: board.at(i, j)) {
          //System.out.println("Particle " + particle.index + ": " + particle.x + ", " + particle.y);
          //System.out.print(particle.index);
          sb.add(particle.id + "\t" + particle.x + "\t" + particle.y);
          for (Particle neighbourParticle: particle.getNeighbours()) {
            //System.out.print(" " + neighbourParticle.index);
          }
          //System.out.println();
        }
      }
    }

    Path file = Paths.get("output.xyz");
    try {
      Files.write(file, sb, Charset.forName("UTF-8"));
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}
