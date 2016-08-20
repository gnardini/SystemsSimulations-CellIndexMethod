package algorithms;

import models.Cell;
import models.Particle;

import java.awt.*;
import java.util.List;

public class CellIndexMethod extends DistanceCalculator {

  public CellIndexMethod(List<Particle> particles, int boardM, int cellSize, int convergenceRadius) {
    super(particles, boardM, cellSize, convergenceRadius);
  }

  @Override
  public void calculateDistanceWithEdge() {
    for (int i = 0; i < board.getBoardM(); i++) {
      for (int j = 0; j < board.getBoardM(); j++) {
        updateCellNeighbours(board.getCell(i, j), convergenceRadius);

        maybeAddCellToList(i, j, new Point(1, 0));
        maybeAddCellToList(i, j, new Point(1, 1));
        maybeAddCellToList(i, j, new Point(0, 1));
        maybeAddCellToList(i, j, new Point(-1, 1));

      }
    }
  }

  @Override
  public void calculateDistanceWithoutEdge() {
    // TODO: WRITE METHOD
  }

  private void updateCellNeighbours(Cell cell, int convergenceRadius) {
    for (Particle particle1 : cell.getParticles()) {
      for (Particle particle2 : cell.getParticles()) {
        if (particle1.getId() != particle2.getId() && particle1.isInRadius(particle2, convergenceRadius)) {
          particle1.getNeighbours().add(particle2);
        }
      }
    }
  }

  private void maybeAddCellToList(int x, int y, Point movement) {
    int newX = x + movement.x;
    int newY = y + movement.y;
    boolean xModified = false;
    boolean yModified = false;

    if (newX > board.getBoardM() - 1) {
        xModified = true;
        newX = 0;
      } else if (newX < 0) {
        xModified = true;
        newX = board.getBoardM() - 1;
      }
      if (newY > board.getBoardM() - 1) {
        yModified = true;
        newY = 0;
      } else if (newY < 0) {
        yModified = true;
        newY = board.getBoardM() - 1;
      }

      if (newX >= 0 && newX < board.getBoardM() && newY >= 0 && newY < board.getBoardM()) {
      doUpdate(board.getCell(x, y), board.getCell(newX, newY), xModified, yModified);
    }
  }

  private void doUpdate(Cell cell1, Cell cell2, boolean xModified, boolean yModified) {
    for (Particle particle1 : cell1.getParticles()) {
      for (Particle particle2 : cell2.getParticles()) {

        Particle particle1ToCompare = particle1;
        Particle particle2ToCompare = particle2;
        if (xModified && particle1.getX() > particle2ToCompare.getY()) {
          particle2ToCompare = new Particle(
                  particle2.getId(),
                  particle2.getX() + boardM,
                  particle2.getY(),
                  particle2.getRadius());
        } else if (xModified && particle1.getX() < particle2ToCompare.getX()) {
          particle1ToCompare = new Particle(
                  particle1.getId(),
                  particle1.getX() + boardM,
                  particle1.getY(),
                  particle1.getRadius());
        }
        if (yModified && particle1.getY() > particle2ToCompare.getY()) {
          particle2ToCompare = new Particle(
                  particle2.getId(),
                  particle2ToCompare.getX(),
                  particle2ToCompare.getY() + boardM,
                  particle2.getRadius());
        }

        if (particle1ToCompare.isInRadius(particle2ToCompare, convergenceRadius)) {
          particle1.getNeighbours().add(particle2);
          particle2.getNeighbours().add(particle1);
        }
      }
    }
  }

}
