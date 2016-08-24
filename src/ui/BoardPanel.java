package ui;

import models.Board;
import models.Particle;

import javax.swing.*;
import java.awt.*;

import static ui.ParticlePrinter.SCREEN_SIZE;

public class BoardPanel extends JPanel {

    private Board board;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_SIZE, SCREEN_SIZE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        double multiplier = 1;
        if (board != null) {
            multiplier = SCREEN_SIZE / board.getL();
        }
        g.setColor(Color.red);

        if (board != null) {
            for (int i = 0; i < board.getM(); i++) {
                for (int j = 0; j < board.getM(); j++) {
                    for (Particle particle : board.getCell(i, j).getParticles()) {
                        g.fillOval(
                                (int) (particle.getX() * multiplier),
                                (int) (particle.getY() * multiplier),
                                (int) (particle.getRadius() * multiplier),
                                (int) (particle.getRadius() * multiplier));
                    }
                }
            }
        }
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
