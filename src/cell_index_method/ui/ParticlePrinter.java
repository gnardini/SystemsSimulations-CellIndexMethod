package cell_index_method.ui;

import cell_index_method.models.State;

import javax.swing.*;

public class ParticlePrinter extends JFrame {

    public static final int SCREEN_SIZE = 500;
    public static final int MARGIN = 20;

    private BoardPanel boardPanel;

    public ParticlePrinter() {
        setTitle("SS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_SIZE, SCREEN_SIZE + MARGIN * 3);

        boardPanel = new BoardPanel();
        add(boardPanel);
        pack();

        setVisible(true);
    }

    public void setState(State state) {
        boardPanel.setState(state);
        repaint();
    }

}
