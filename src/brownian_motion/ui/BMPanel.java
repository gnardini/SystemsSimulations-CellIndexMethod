package brownian_motion.ui;

import brownian_motion.BMStats;
import brownian_motion.model.BMBoard;
import brownian_motion.model.BMParticle;
import brownian_motion.model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static brownian_motion.ui.BMPrinter.MARGIN;
import static brownian_motion.ui.BMPrinter.SCREEN_SIZE;

public class BMPanel extends JPanel {

    private final BMBoard board;
    private final BMStats stats;

    public BMPanel(BMBoard board, BMStats stats) {
        this.board = board;
        this.stats = stats;
    }

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

        printParticles(g, multiplier);
        printBigParticlePath(g, multiplier);

        g.setColor(Color.blue);
        g.drawString("Particles: " + board.getParticles().size()
                + " Collisions/sec: " + String.format("%.02f", stats.getCollisionsPerSecond())
                + " Secs/collision: " + String.format("%.02f", stats.getAverageTimeToCollision()),
                MARGIN, SCREEN_SIZE + MARGIN * 2);
    }

    private void printParticles(Graphics g, double multiplier) {
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
    }

    private void printBigParticlePath(Graphics g, double multiplier) {
        List<Point> path = stats.getBigParticlePath();
        if (path.isEmpty()) {
            return;
        }
        Iterator<Point> points = path.iterator();
        Point lastPoint = points.next();
        while (points.hasNext()) {
            Point newPoint = points.next();
            g.drawLine(
                    (int) (lastPoint.x * multiplier),
                    (int) (lastPoint.y * multiplier),
                    (int) (newPoint.x * multiplier),
                    (int) (newPoint.y * multiplier));
            lastPoint = newPoint;
        }
    }

}
