package granular_medium.ui;

import granular_medium.Parameters;
import granular_medium.models.Particle;
import granular_medium.models.State;

import javax.swing.*;
import java.awt.*;

public class UiPrinter extends JFrame implements Printer {

    public static final int SCREEN_SIZE = 500;
    public static final int MARGIN = 20;

    private Panel statePanel;

    public UiPrinter() {
        setTitle("SS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_SIZE, SCREEN_SIZE + MARGIN * 3);

        statePanel = new Panel();
        add(statePanel);
        pack();

        setVisible(true);
    }

    public void updateState(State state) {
        statePanel.setState(state);
        repaint();
    }

    public class Panel extends JPanel {

        private int DRAWING_MARGIN = 3 * MARGIN;

        private State state;

        public void setState(State state) {
            this.state = state;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(SCREEN_SIZE, SCREEN_SIZE + MARGIN * 3);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (state == null) return;

            double multiplier = SCREEN_SIZE / state.getL();
            //multiplier *= .7;

            g.setColor(Color.black);
            g.drawLine(
                    DRAWING_MARGIN,
                    (int) (SCREEN_SIZE - state.getL() * multiplier),
                    DRAWING_MARGIN,
                    SCREEN_SIZE);
            g.drawLine(
                    (int) (DRAWING_MARGIN + state.getW() * multiplier),
                    (int) (SCREEN_SIZE - state.getL() * multiplier),
                    (int) (DRAWING_MARGIN + state.getW() * multiplier),
                    SCREEN_SIZE);
            double gapCenter = state.getW() / 2;
            double gapRadius = state.getD() / 2;
            g.drawLine(
                    DRAWING_MARGIN,
                    SCREEN_SIZE,
                    (int) (DRAWING_MARGIN + (gapCenter - gapRadius) * multiplier),
                    SCREEN_SIZE);
            g.drawLine(
                    (int) (DRAWING_MARGIN + (gapCenter + gapRadius) * multiplier),
                    SCREEN_SIZE,
                    (int) (DRAWING_MARGIN + state.getW() * multiplier),
                    SCREEN_SIZE);

            printParticles(g, state, multiplier);

            g.setColor(Color.blue);
            g.drawString("Particles: " + state.getParticles().size(), MARGIN, SCREEN_SIZE + MARGIN * 2);
        }

        private void printParticles(Graphics g, State state, double multiplier) {
            for (Particle particle : state.getParticles()) {
                g.setColor(particle.getColor());
                double radius = particle.getRadius();
                double x = particle.getPosition().getX() - radius;
                double y = particle.getPosition().getY() + radius;
                int ovalSize = (int) (radius * 2 * multiplier);
                g.fillOval(
                        (int) (DRAWING_MARGIN + x * multiplier),
                        (int) (SCREEN_SIZE - y * multiplier),
                        ovalSize,
                        ovalSize);
            }
        }

    }

}
