package tp4.ui;

import tp4.model.OscillatorInstant;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OscillatorPrinter extends JFrame {

    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 200;

    public OscillatorPrinter(List<OscillatorInstant> baseInstants, List<OscillatorInstant> instantsToCompare) {
        setTitle("SS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        OscillatorInstantsVisualizer oscillatorInstantsVisualizer =
                new OscillatorInstantsVisualizer(baseInstants, instantsToCompare);
        add(oscillatorInstantsVisualizer);
        pack();

        setVisible(true);
    }

    private static class OscillatorInstantsVisualizer extends JPanel {

        private List<OscillatorInstant> instants;
        private List<OscillatorInstant> instantsToCompare;

        public OscillatorInstantsVisualizer(List<OscillatorInstant> instants, List<OscillatorInstant> instantsToCompare) {
            this.instants = instants;
            this.instantsToCompare = instantsToCompare;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            printInstants(g, Color.orange, instants);
            printInstants(g, Color.blue, instantsToCompare);
        }

        private void printInstants(Graphics g, Color color, List<OscillatorInstant> instants) {
            g.setColor(color);
            instants.stream().reduce((previousInstant, nextInstant) -> {
                g.drawLine(getTimeValue(previousInstant), getPositionValue(previousInstant),
                        getTimeValue(nextInstant), getPositionValue(nextInstant));
                return nextInstant;
            });
        }

        private int getTimeValue(OscillatorInstant instant) {
            return (int) (instant.getTime() * SCREEN_WIDTH / 5);
        }

        private int getPositionValue(OscillatorInstant instant) {
            return (int) (SCREEN_HEIGHT / 2 + instant.getPosition() * SCREEN_HEIGHT / 2);
        }
    }

}
