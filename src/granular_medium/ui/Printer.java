package granular_medium.ui;

import granular_medium.Parameters;
import granular_medium.models.Particle;
import granular_medium.models.State;

import javax.swing.*;
import java.awt.*;

public class Printer extends JFrame {

    public static final int SCREEN_SIZE = 500;
    public static final int MARGIN = 20;

    private Panel statePanel;

    public Printer(State state) {
        setTitle("SS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_SIZE, SCREEN_SIZE + MARGIN * 3);

        statePanel = new Panel(state);
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

        public Panel(State state) {
            this.state = state;
        }

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

            double multiplier = SCREEN_SIZE / state.getL();
            //multiplier *= .7;

            g.setColor(Color.black);
            g.drawLine(
                    DRAWING_MARGIN,
                    (int) (SCREEN_SIZE - Parameters.L * multiplier),
                    DRAWING_MARGIN,
                    SCREEN_SIZE);
            g.drawLine(
                    (int) (DRAWING_MARGIN + Parameters.W * multiplier),
                    (int) (SCREEN_SIZE - Parameters.L * multiplier),
                    (int) (DRAWING_MARGIN + Parameters.W * multiplier),
                    SCREEN_SIZE);
            g.drawLine(
                    DRAWING_MARGIN,
                    SCREEN_SIZE,
                    (int) (DRAWING_MARGIN + Parameters.W * multiplier),
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
