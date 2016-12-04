package pedestrian_dynamics.ui;

import pedestrian_dynamics.models.Particle;
import pedestrian_dynamics.models.State;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class UiPrinter extends JFrame implements Printer {

    public static final int SCREEN_SIZE = 500;
    public static final int MARGIN = 20;
    public static final int ANIMATION_SIZE = SCREEN_SIZE - 2 * MARGIN;
    public static final int LEFT_MARGIN = MARGIN;
    public static final int RIGHT_MARGIN = MARGIN;
    public static final int TOP_MARGIN = MARGIN;
    public static final int BOTTOM_MARGIN = MARGIN;

    private Panel statePanel;

    public UiPrinter() {
        setTitle("SS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_SIZE, SCREEN_SIZE + TOP_MARGIN + BOTTOM_MARGIN);

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

        private State state;

        public void setState(State state) {
            this.state = state;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(SCREEN_SIZE, SCREEN_SIZE + MARGIN * 4);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (state == null) return;

            double multiplier = ANIMATION_SIZE / state.getParameters().getL();

            g.setColor(Color.black);

            // Left border
            g.drawLine(
                    MARGIN,
                    (int) (SCREEN_SIZE - state.getL() * multiplier),
                    MARGIN,
                    SCREEN_SIZE);
            // Right border
            g.drawLine(
                    (int) (MARGIN + state.getW() * multiplier),
                    (int) (SCREEN_SIZE - state.getL() * multiplier),
                    (int) (MARGIN + state.getW() * multiplier),
                    SCREEN_SIZE);
            double gapCenter = state.getW() / 2;
            double gapRadius = state.getD() / 2;
            state.getBoard().getHorizontalWalls().forEach(wall ->
                    drawLine(g, wall.getPosition(), gapCenter, gapRadius, multiplier)
            );

            printParticles(g, state, multiplier);

            g.setColor(Color.blue);
            g.drawString("Particles: " + state.getParticles().size(), MARGIN, SCREEN_SIZE + MARGIN * 2);

            g.drawString(String.format("Time: %.2f s", state.getCurrentTime()), SCREEN_SIZE / 2, SCREEN_SIZE + MARGIN * 2);
        }

        private void drawLine(Graphics g, double y, double center, double opening, double multiplier) {
            int yPosition = (int) (SCREEN_SIZE - y * multiplier - BOTTOM_MARGIN);
            g.drawLine(
                    LEFT_MARGIN,
                    yPosition,
                    LEFT_MARGIN + (int) ((center - opening) * multiplier),
                    yPosition);
            g.drawLine(
                    MARGIN + (int) (state.getW() * multiplier),
                    yPosition,
                    LEFT_MARGIN + (int) ((center + opening) * multiplier),
                    yPosition);
        }

        private void printParticles(Graphics g, State state, double multiplier) {
            List<Particle> allParticles = new LinkedList<>();
            allParticles.addAll(state.getParticles());
            allParticles.addAll(state.getStaticParticles());
            for (Particle particle : allParticles) {
                g.setColor(particle.getColor());
                double radius = particle.getRadius();
                double x = particle.getPosition().getX() - radius;
                double y = particle.getPosition().getY() + radius;
                int ovalSize = (int) (radius * 2 * multiplier);
                g.fillOval(
                        (int) (LEFT_MARGIN + (x * multiplier)),
                        (int) (SCREEN_SIZE - BOTTOM_MARGIN - (y * multiplier)),
                        ovalSize,
                        ovalSize);
            }
        }

    }

}
