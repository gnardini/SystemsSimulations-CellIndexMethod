package brownian_motion.ui;

import brownian_motion.model.BMBoard;
import brownian_motion.model.BMParticle;

import javax.swing.*;
import java.awt.*;

import static brownian_motion.ui.BMPrinter.MARGIN;
import static brownian_motion.ui.BMPrinter.SCREEN_SIZE;

public class BMPanel extends JPanel {

    private BMBoard board;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_SIZE, SCREEN_SIZE + MARGIN * 3);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (board == null) {
            return;
        }

        double multiplier = SCREEN_SIZE / board.getSize();

        for (BMParticle particle : board.getParticles()) {
            g.setColor(particle.getColor());
            double radius = particle.getRadius();
            double x = particle.getX() - radius;
            double y = particle.getY() - radius;
            int ovalSize = (int) (radius * 2 * multiplier);
            g.fillOval(
                    (int) (x * multiplier),
                    (int) (y * multiplier),
                    ovalSize,
                    ovalSize);
        }

        g.setColor(Color.blue);
        g.drawString("Particles: " + board.getParticles().size(), MARGIN, SCREEN_SIZE + MARGIN * 2);
    }

    public void setBoard(BMBoard board) {
        this.board = board;
    }

}
