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

        g.setColor(Color.blue);
        // TODO: add eta value
        g.drawString("Particles: " + state.getParticleCount() + ", Speed: " + state.getSpeed() + ", eta: " + "1 (MODIFY THIS)", 20, 20);
        g.drawString("polarization: " + String.format("%.6f", state.polarization()), 20, SCREEN_SIZE - 20);

        g.setColor(Color.red);

        if (board != null) {
            for (int i = 0; i < board.getM(); i++) {
                for (int j = 0; j < board.getM(); j++) {
                    for (Particle particle : board.getCell(i, j).getParticles()) {
                        double angle = particle.getAngle();
                        double xPart = Math.cos(angle);
                        double yPart = Math.sin(angle);
                        double x = particle.getX();
                        double y = particle.getY();
                        float SIZE = .2f;
                        g.drawLine(
                                (int) ((x - SIZE * xPart) * multiplier),
                                (int) ((y - SIZE * yPart) * multiplier),
                                (int) ((x + SIZE * xPart) * multiplier),
                                (int) ((y + SIZE * yPart) * multiplier));
                        int ovalSize = (int) (.2f * multiplier);
                        g.fillOval(
                                (int) ((x + SIZE * xPart) * multiplier) - ovalSize / 2,
                                (int) ((y + SIZE * yPart) * multiplier) - ovalSize / 2,
                                ovalSize,
                                ovalSize);

//                        g.fillOval(
//                                (int) (particle.getX() * multiplier),
//                                (int) (particle.getY() * multiplier),
//                                (int) (.5 * multiplier),
//                                (int) (.5 * multiplier));
                    }
                }
            }
        }
    }

    public void setState(State state) {
        this.state = state;
    }

}
