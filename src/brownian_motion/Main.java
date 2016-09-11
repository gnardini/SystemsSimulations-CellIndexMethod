package brownian_motion;

import brownian_motion.crash.Crash;
import brownian_motion.model.BMBoard;
import brownian_motion.model.BMParticle;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Main {
  private static final String OUTPUT = "files/output.xyz";
    private static final String STATISTICS_OUTPUT_PATH = "files/statistics/output.txt";

  private static final int SIMULATION_TIME_SECONDS = 30;
  private static final int FRAMES_PER_SECOND = 60 * 5;
  private static final int PRINT_STEPS = 1;

  private static final boolean WITH_ANIMATION = false;
  private static final boolean RECORD_STATISTICS = true;



  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    int totalFrames = SIMULATION_TIME_SECONDS * FRAMES_PER_SECOND * PRINT_STEPS;
    int frames = 0;
    PrintWriter basicWriter = new PrintWriter(OUTPUT, "UTF-8");
    PrintWriter writer = new PrintWriter(basicWriter, true);

    BMBoard board = BMParameters.getInitialBoard();

    double totalTime = 0;

    BMStats stats = new BMStats(board);

    while (frames < totalFrames) {
      if (WITH_ANIMATION) {
        if (frames % PRINT_STEPS == 0) {
          print(board, writer, frames);
        }
      }

      Crash crash = board.calculateTimeUntilNextCrash();
      stats.onCollision(crash.getTimeUntilCrash());
      totalTime += crash.getTimeUntilCrash();
      board.advanceTime(crash.getTimeUntilCrash());
      crash.applyCrash();
      frames += 1;

      System.out.println("Frame " + frames + " of " + totalFrames);
    }

    if (RECORD_STATISTICS) {
        recordStadisticsTo(STATISTICS_OUTPUT_PATH, stats);
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

  private static void recordStadisticsTo(String path, BMStats stats) {
      PrintWriter basicWriter = null;
      try {
          basicWriter = new PrintWriter(path, "UTF-8");
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      PrintWriter writer = new PrintWriter(basicWriter, true);

      System.out.println("Recording statistics to file");

      for (int i = 0; i < stats.collissionsPerSecond.size(); i++) {
          writer.println(stats.collissionsPerSecond.get(i));
      }

      System.out.println("Finished recording statistics");
  }
}
