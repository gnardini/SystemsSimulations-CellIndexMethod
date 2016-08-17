package output;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Test {
	
  public static final int PARTICLE_COUNT = 1000;
  public static final int BOARD_SIZE = 20;
  private static final int CELL_SIZE = 2;
  public static final int CONVERGENCE_RADIUS = 10;

  public static void main(String[] args) {

    XYZFormatter formatter = new XYZFormatter();
    Board board = initializeBoard();
    
    
    List<String> lines = formatter.fformat(board, PARTICLE_COUNT, 1);
    writeTo("output.xyz", lines);
  }
  
  private static Board initializeBoard() {
	    // Create board
	    Board board = new Board(BOARD_SIZE, CELL_SIZE, CONVERGENCE_RADIUS, true);
	    // Load particles and add them to the board
    	try {
	    	Scanner scanner = new Scanner(new File("input.txt"));
	    	while (scanner.hasNext()) {
	    		Particle particle = new Particle(
	    				scanner.nextInt(), 
	    				scanner.nextFloat(), 
	    				scanner.nextFloat(), 
	    				.25f);
	    		if (particle.id == 1) {
	    			particle.x = BOARD_SIZE - .1f;
	    			particle.y = BOARD_SIZE - .1f;
	    		}
	    		board.addParticle(particle);
	    	}
	    	scanner.close();
    	} catch (FileNotFoundException fileNotFoundException) {
    		fileNotFoundException.printStackTrace();
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
