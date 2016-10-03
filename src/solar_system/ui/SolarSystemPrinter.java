package solar_system.ui;

import solar_system.model.Particle;
import solar_system.model.State;
import solar_system.model.Vector;

import javax.swing.*;
import java.awt.*;

public class SolarSystemPrinter  extends JFrame {

    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 500;

    private SolarSystemPanel solarSystemPanel;

    public SolarSystemPrinter() {
        setTitle("SS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        solarSystemPanel = new SolarSystemPanel();
        add(solarSystemPanel);
        pack();

        setVisible(true);
    }

    public void setState(State state) {
        solarSystemPanel.setState(state);
        repaint();
    }

    private static class SolarSystemPanel extends JPanel {

        private State state;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);

            if (state == null) return;

            printParticle(g, state.getSun());
            printParticle(g, state.getEarth());
            printParticle(g, state.getMars());
            printParticle(g, state.getMercury());
            printParticle(g, state.getShip());
        }

        private void printParticle(Graphics g, Particle particle) {
            if (!particle.isLaunched()) {
                return;
            }
            g.setColor(particle.getColor());
            double multiplier = SCREEN_WIDTH / 8E11;
            Vector position = particle.getPosition();
            int planetSize = (int) Math.min(30, particle.getRadius() * 4000 * multiplier);
            g.fillOval(
                    SCREEN_WIDTH / 2 + (int) (position.getX() * multiplier) - planetSize / 2,
                    SCREEN_WIDTH / 2 + (int) (position.getY() * multiplier) - planetSize / 2,
                    planetSize,
                    planetSize);
        }

        public void setState(State state) {
            this.state = state;
        }
    }

}

