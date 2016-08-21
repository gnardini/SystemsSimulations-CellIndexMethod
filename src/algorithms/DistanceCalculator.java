package algorithms;

import models.Board;
import models.Particle;

import java.util.List;

public abstract class DistanceCalculator {

  protected List<Particle> particles;
  protected int M;
  protected int L;
  protected int convergenceRadius;
  protected Board board;

  public DistanceCalculator(List<Particle> particles, int M, int L, int convergenceRadius) {
    this.particles = particles;
    this.M = M;
    this.L = L;
    this.convergenceRadius = convergenceRadius;
    initializeBoard();
  }

  private void initializeBoard() {
    this.board = new Board(M, L, convergenceRadius);
    // Add particles
    double maxRadius = 0;
    for (Particle particle: particles) {
      board.addParticle(particle);
      if (particle.getRadius() > maxRadius) {
        maxRadius = particle.getRadius();
      }
    }
    if (Double.valueOf(L) / M <= convergenceRadius + 2 * maxRadius) {
      throw new IllegalArgumentException("Invalid board size");
    }
  }

  public Board getBoard() {
    return board;
  }

  public abstract void calculateDistanceWithEdge();

  public abstract void calculateDistanceWithoutEdge();

}
