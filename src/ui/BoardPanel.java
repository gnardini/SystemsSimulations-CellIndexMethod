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
        g.drawString("Particles: " + state.getParticleCount()
                + ", Speed: " + state.getSpeed()
                + ", eta: " + String.format("%.02f", state.getEta()),
                20, 20);
        g.drawString("polarization: " + String.format("%.6f", state.polarization()), 20, SCREEN_SIZE - 20);

        g.setColor(Color.red);

        for (Particle particle : board.getParticles()) {
            g.setColor(particle.getColorForAngle());
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
        }
    }

    public void setState(State state) {
        this.state = state;
    }

}
