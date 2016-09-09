package brownian_motion;

import brownian_motion.crash.Crash;
import brownian_motion.model.BMBoard;
import brownian_motion.ui.BMPrinter;

public class BMGenerator {

    public BMGenerator() {
        BMBoard board = BMParameters.getInitialBoard();
        BMPrinter printer = new BMPrinter();

        double totalTime = 0;
        while (true) {
            printer.setBoard(board);

            Crash crash = board.calculateTimeUntilNextCrash();
            totalTime += crash.getTimeUntilCrash();
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
