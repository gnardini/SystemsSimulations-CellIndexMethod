package ui;

import models.Board;
import models.Particle;
import models.State;

import javax.swing.*;
import java.awt.*;

import static ui.ParticlePrinter.SCREEN_SIZE;

public class BoardPanel extends JPanel {

    private State state;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_SIZE, SCREEN_SIZE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state == null || state.getBoard() == null) {
            return;
        }

        Board board = state.getBoard();
        double multiplier = SCREEN_SIZE / state.getL();
        g.setColor(Color.red);

        if (board != null) {
            for (int i = 0; i < board.getM(); i++) {
                for (int j = 0; j < board.getM(); j++) {
                    for (Particle particle : board.getCell(i, j).getParticles()) {
                        g.fillOval(
                                (int) (particle.getX() * multiplier),
                                (int) (particle.getY() * multiplier),
                                (int) (multiplier),
                                (int) (multiplier));
                    }
                }
            }
        }
    }

    public void setState(State state) {
        this.state = state;
    }

}
