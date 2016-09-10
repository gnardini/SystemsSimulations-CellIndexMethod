package brownian_motion;

import brownian_motion.crash.Crash;
import brownian_motion.model.BMBoard;
import brownian_motion.ui.BMPrinter;

public class BMGenerator {

    public BMGenerator() {
        BMBoard board = BMParameters.getInitialBoard();

        BMStats stats = new BMStats(board);
        BMPrinter printer = new BMPrinter(board, stats);

        while (true) {
            printer.updateBoard();

            Crash crash = board.calculateTimeUntilNextCrash();
            stats.onCollision(crash.getTimeUntilCrash());

            if (crash.getTimeUntilCrash() < 0) {
                throw new IllegalStateException("Time < 0");
            }
            board.advanceTime(crash.getTimeUntilCrash());
            crash.applyCrash();
            sleep(BMParameters.ANIMATION_DELAY);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new BMGenerator();
    }

}
