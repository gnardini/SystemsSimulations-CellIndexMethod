package brownian_motion.model;

public class Point {

    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point forParticle(BMParticle particle) {
        return new Point(particle.getX(), particle.getY());
    }
}
