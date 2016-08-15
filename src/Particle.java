import java.util.LinkedList;
import java.util.List;

public class Particle {
  public int id, x, y, radius;

  private List<Particle> neighbours = new LinkedList<>();

  public Particle(int id, int x, int y, int radius) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.radius = radius;
  }

  public boolean isInRadius(Particle otherParticle, int convergenceRadius) {
    int xDiff = Math.abs(x - otherParticle.x);
    int yDiff = Math.abs(y - otherParticle.y);
    double totalDiff = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    totalDiff -= radius;
    totalDiff -= otherParticle.radius;
    return totalDiff <= convergenceRadius;
  }

  public static Particle random(int id, int boardSize, int convergenceRadius) {
    int x = (int) (Math.random() * boardSize);
    int y = (int) (Math.random() * boardSize);
    int radius = (int) (Math.random() * convergenceRadius);
    return new Particle(id, x, y ,radius);
  }

  public List<Particle> getNeighbours() {
    return this.neighbours;
  }

}
