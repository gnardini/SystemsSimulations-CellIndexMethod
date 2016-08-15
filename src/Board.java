import java.util.LinkedList;
import java.util.List;

public class Board {
  private int boardSize;
  private int cellSize;
  private int convergenceRadius;

  private Cell[][] board;
  private int cellCount;

  public Board(int boardSize, int cellSize, int convergenceRadius) {
    this.boardSize = boardSize;
    this.cellSize = cellSize;
    this.convergenceRadius = convergenceRadius;
    initialize();
  }

  private void initialize() {
    this.cellCount = boardSize / cellSize;
    this.board = new Cell[cellCount][cellCount];

    for (int i = 0; i < cellCount; i++) {
      for (int j = 0; j < cellCount; j++) {
        board[i][j] = new Cell();
      }
    }
  }

  public void addRandomParticle(int id) {
    Particle newParticle = Particle.random(id, boardSize, convergenceRadius);
    addParticle(newParticle);
  }

  public void addParticle(Particle particle) {
    board[particle.x / cellSize][particle.y / cellSize].particles.add(particle);
  }

  public void updateCells() {
    for (int i = 0; i < cellCount; i++) {
      for (int j = 0; j < cellCount; j++) {
        if (i < cellCount - 1) {
          doUpdate(board[i][j], board[i+1][j]);
          if (j < cellCount - 1) {
            doUpdate(board[i][j], board[i+1][j+1]);
          }
        }
        if (j < cellCount - 1) {
          doUpdate(board[i][j], board[i][j+1]);
          if (i > 0) {
            doUpdate(board[i][j], board[i-1][j+1]);
          }
        }
      }
    }
  }

  private void doUpdate(Cell cell1, Cell cell2) {
    for (Particle particle1: cell2.particles) {
      for (Particle particle2: cell1.particles) {
        if (particle1.isInRadius(particle2, convergenceRadius)) {
          particle1.getNeighbours().add(particle2);
          particle2.getNeighbours().add(particle1);
        }
      }
    }
  }

  public List<Particle> at(int i, int j) {
    return board[i][j].particles;
  }

  public int getBoardSize() {
    return boardSize;
  }

  public int getCellSize() {
    return cellSize;
  }

  public int getConvergenceRadius() {
    return convergenceRadius;
  }

  public int getCellCount() {
    return cellCount;
  }

  private static class Cell {
    List<Particle> particles = new LinkedList<>();
  }
}
