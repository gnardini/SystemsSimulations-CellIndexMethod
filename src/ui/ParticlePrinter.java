package ui;

import models.Board;
import models.State;

import javax.swing.*;

public class ParticlePrinter extends JFrame {

    public static final int SCREEN_SIZE = 500;
    private BoardPanel boardPanel;

    public ParticlePrinter() {
        setTitle("SS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_SIZE, SCREEN_SIZE);

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
