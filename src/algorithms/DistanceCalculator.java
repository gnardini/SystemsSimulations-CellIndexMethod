package algorithms;

import models.Board;
import models.Particle;

import java.util.List;

public abstract class DistanceCalculator {

  protected List<Particle> particles;
  protected int boardM;
  protected int cellSize;
  protected int convergenceRadius;
  protected Board board;

  public DistanceCalculator(List<Particle> particles, int boardM, int cellSize, int convergenceRadius) {
    this.particles = particles;
    this.boardM = boardM;
    this.cellSize = cellSize;
    this.convergenceRadius = convergenceRadius;
    initializeBoard();
  }

  private void initializeBoard() {
    this.board = new Board(boardM, cellSize, convergenceRadius);
    // Add particles
    double maxRadius = 0;
    for (Particle particle: particles) {
      board.addParticle(particle);
      if (particle.getRadius() > maxRadius) {
        maxRadius = particle.getRadius();
      }
    }
    if (cellSize / boardM <= convergenceRadius + 2 * maxRadius) {
      throw new IllegalArgumentException("Invalid board size");
    }
  }

  public Board getBoard() {
    return board;
  }

  public abstract void calculateDistanceWithEdge();

  public abstract void calculateDistanceWithoutEdge();

}
