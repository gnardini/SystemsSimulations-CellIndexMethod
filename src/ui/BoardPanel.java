package ui;

import models.Board;
import models.Particle;
import models.State;
import off_lattice.OffLatticeParameters;

import javax.swing.*;
import java.awt.*;

import static ui.ParticlePrinter.SCREEN_SIZE;

public class BoardPanel extends JPanel {

    private State state;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_SIZE, SCREEN_SIZE + ParticlePrinter.MARGIN * 3);
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

        double xSpeedSum = 0;
        double ySpeedSum = 0;

        for (Particle particle : board.getParticles()) {
            xSpeedSum += particle.getSpeed() * Math.cos(particle.getAngle());
            ySpeedSum += particle.getSpeed() * Math.sin(particle.getAngle());

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

        double polarization = Math.sqrt(Math.pow(xSpeedSum, 2) + Math.pow(ySpeedSum, 2)) / (OffLatticeParameters.SPEED * board.getParticles().size());
        g.setColor(Color.blue);
        g.drawString("Particles: " + state.getParticleCount()
                + ", Speed: " + state.getSpeed()
                + ", L: " + state.getL()
                + ", eta: " + String.format("%.02f", state.getEta())
                + ", polarization: " + String.format("%.6f", polarization), ParticlePrinter.MARGIN, SCREEN_SIZE + ParticlePrinter.MARGIN * 2);
    }

    public void setState(State state) {
        this.state = state;
    }

}
