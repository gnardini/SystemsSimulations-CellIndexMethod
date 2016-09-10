package brownian_motion.ui;

import brownian_motion.BMStats;
import brownian_motion.model.BMBoard;

import javax.swing.*;

public class BMPrinter extends JFrame {

    public static final int SCREEN_SIZE = 500;
    public static final int MARGIN = 20;

    private BMPanel boardPanel;

    public BMPrinter(BMBoard board, BMStats stats) {
        setTitle("SS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_SIZE, SCREEN_SIZE + MARGIN * 3);

        boardPanel = new BMPanel(board, stats);
        add(boardPanel);
        pack();

        setVisible(true);
    }

    public void updateBoard() {
        repaint();
    }

}
