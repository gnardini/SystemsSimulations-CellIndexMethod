package brownian_motion;

import brownian_motion.crash.Crash;
import brownian_motion.model.BMBoard;
import brownian_motion.model.BMParticle;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {
  private static final String OUTPUT = "files/output.xyz";
  private static final int SIMULATION_TIME_SECONDS = 30;
  private static final int FRAMES_PER_SECOND = 60 * 5;
  private static final int PRINT_STEPS = 1;

  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    int totalFrames = SIMULATION_TIME_SECONDS * FRAMES_PER_SECOND * PRINT_STEPS;
    int frames = 0;
    PrintWriter basicWriter = new PrintWriter(OUTPUT, "UTF-8");
    PrintWriter writer = new PrintWriter(basicWriter, true);

    BMBoard board = BMParameters.getInitialBoard();

    double totalTime = 0;
    while (frames < totalFrames) {
      if (frames % PRINT_STEPS == 0) {
        print(board, writer, frames);
      }

      Crash crash = board.calculateTimeUntilNextCrash();
      totalTime += crash.getTimeUntilCrash();
      board.advanceTime(crash.getTimeUntilCrash());
      crash.applyCrash();
      frames += 1;

      System.out.println("Frame " + frames + " of " + totalFrames);
    }

    writer.close();
  }

  private static void print(BMBoard board, PrintWriter writer, int time) {
    // Create 2 invisible particles in the diagonal
    writer.println(board.getParticles().size() + 2);
    writer.println(time);

    writer.println("0 0 0 0 0 0 0 0");
    writer.println(BMParameters.BOARD_SIDE_LENGTH + " " + BMParameters.BOARD_SIDE_LENGTH + " 0 0 0 0 0 0");

    for (BMParticle p : board.getParticles()) {
      writer.println(p.toDrawableString());
    }
  }
}
