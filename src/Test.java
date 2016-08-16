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

    XYZFormatter formatter = new XYZFormatter();
    Board board = initializeBoard();
    
    List<String> lines = formatter.fformat(board, PARTICLE_COUNT);
    writeTo("output.xyz", lines);
  }
  
  private static Board initializeBoard() {
	    // Create board
	    Board board = new Board(BOARD_SIZE, CELL_SIZE, CONVERGENCE_RADIUS);
	    // Add random particles to the board
	    for (int i = 0; i < PARTICLE_COUNT; i++) {
	      board.addRandomParticle(i);
	    }
	    
	    board.updateCells();
	    return board;
  }
  
  private static void writeTo(String fileName, List<String> lines) {
	  Path path = Paths.get(fileName);
	  try {
		  Files.write(path, lines, Charset.forName("UTF-8"));
	  } catch (IOException ioException) {
		  ioException.printStackTrace();
	  }
  }
}
