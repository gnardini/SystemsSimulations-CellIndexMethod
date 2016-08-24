package algorithms;

import models.Cell;
import models.Particle;

import java.awt.*;
import java.util.List;

public class CellIndexMethod extends DistanceCalculator {

  private float AMPLITUD = 0.2f;

  public CellIndexMethod(List<Particle> particles, int M, int L, int convergenceRadius) {
    super(particles, M, L, convergenceRadius);
  }

  @Override
  public void calculateDistanceWithEdge() {
    for (int i = 0; i < board.getM(); i++) {
      for (int j = 0; j < board.getM(); j++) {
        updateCellNeighbours(board.getCell(i, j), convergenceRadius);

        maybeAddCellToListWithEdge(i, j, new Point(1, 0));
        maybeAddCellToListWithEdge(i, j, new Point(1, 1));
        maybeAddCellToListWithEdge(i, j, new Point(0, 1));
        maybeAddCellToListWithEdge(i, j, new Point(-1, 1));

      }
    }
  }

  @Override
  public void calculateDistanceWithoutEdge() {
    for (int i = 0; i < board.getM(); i++) {
      for (int j = 0; j < board.getM(); j++) {
        updateCellNeighbours(board.getCell(i, j), convergenceRadius);

        maybeAddCellToListWithoutEdge(i, j, new Point(1, 0));
        maybeAddCellToListWithoutEdge(i, j, new Point(1, 1));
        maybeAddCellToListWithoutEdge(i, j, new Point(0, 1));
        maybeAddCellToListWithoutEdge(i, j, new Point(-1, 1));

      }
    }
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

  private void maybeAddCellToListWithEdge(int x, int y, Point movement) {
    int newX = x + movement.x;
    int newY = y + movement.y;
    boolean xModified = false;
    boolean yModified = false;

    if (newX > board.getM() - 1) {
        xModified = true;
        newX = 0;
      } else if (newX < 0) {
        xModified = true;
        newX = board.getM() - 1;
      }
      if (newY > board.getM() - 1) {
        yModified = true;
        newY = 0;
      } else if (newY < 0) {
        yModified = true;
        newY = board.getM() - 1;
      }
      
      if (newX >= 0 && newX < board.getM() && newY >= 0 && newY < board.getM()) {
      doUpdate(board.getCell(x, y), board.getCell(newX, newY), xModified, yModified);
    }
  }

  private void maybeAddCellToListWithoutEdge(int x, int y, Point movement) {
    int newX = x + movement.x;
    int newY = y + movement.y;
    boolean xModified = false;
    boolean yModified = false;

    if (newX >= 0 && newX < board.getM() && newY >= 0 && newY < board.getM()) {
      doUpdate(board.getCell(x, y), board.getCell(newX, newY), xModified, yModified);
    }
  }

  private void doUpdate(Cell cell1, Cell cell2, boolean xModified, boolean yModified) {
    for (Particle particle1 : cell1.getParticles()) {
      for (Particle particle2 : cell2.getParticles()) {

        Particle particle1ToCompare = particle1;
        Particle particle2ToCompare = particle2;
        if (xModified && particle1.getX() > particle2ToCompare.getX()) {
          particle2ToCompare = new Particle(
                  particle2.getId(),
                  particle2.getX() + L,
                  particle2.getY(),
                  particle2.getRadius());
        } else if (xModified && particle1.getX() < particle2ToCompare.getX()) {
          particle1ToCompare = new Particle(
                  particle1.getId(),
                  particle1.getX() + L,
                  particle1.getY(),
                  particle1.getRadius());
        }
        if (yModified && particle1.getY() > particle2ToCompare.getY()) {
          particle2ToCompare = new Particle(
                  particle2.getId(),
                  particle2ToCompare.getX(),
                  particle2ToCompare.getY() + L,
                  particle2.getRadius());
        }

        if (particle1ToCompare.isInRadius(particle2ToCompare, convergenceRadius)) {
          particle1.getNeighbours().add(particle2);
          particle2.getNeighbours().add(particle1);
        }
      }
    }
  }

  public void nextStep() {
    for (int i = 0; i < board.getM(); i++) {
      for (int j = 0; j < board.getM(); j++) {
        for (Particle particle : board.getCell(i, j).getParticles()) {
          particle.move(L);
          updateParticleAngle(particle);
          particle.clearNeighbours();
        }
      }
    }
  }

  private void updateParticleAngle(Particle particle) {
    double sinSum = Math.sin(particle.getAngle());
    double cosSum = Math.cos(particle.getAngle());

    for (Particle neighbour : particle.getNeighbours()) {
      sinSum += Math.sin(neighbour.getAngle());
      cosSum += Math.cos(neighbour.getAngle());
    }
    int size = particle.getNeighbours().size() + 1;
    double newAngle = Math.atan2(sinSum / size, cosSum / size);

    newAngle += Math.random() * AMPLITUD - AMPLITUD / 2;

    if (newAngle < 0) {
      newAngle += Math.PI * 2;
    } else if (newAngle > Math.PI * 2) {
      newAngle -= Math.PI * 2;
    }

    particle.setAngle(newAngle);
  }

}
