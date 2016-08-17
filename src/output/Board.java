package output;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Board {
	private int boardSize;
	private int cellSize;
	private int convergenceRadius;

	private Cell[][] board;
	private int cellCount;
	private boolean checkEnvironment;

	public Board(int boardSize, int cellSize, int convergenceRadius, boolean checkEnvironment) {
		this.boardSize = boardSize;
		this.cellSize = cellSize;
		this.convergenceRadius = convergenceRadius;
		this.checkEnvironment = checkEnvironment;
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
		Particle newParticle = Particle.random(id, boardSize);
		addParticle(newParticle);
	}

	public void addParticle(Particle particle) {
		board[((int) particle.x) / cellSize][((int) particle.y) / cellSize].particles.add(particle);
	}

	public void updateCells() {
		for (int i = 0; i < cellCount; i++) {
			for (int j = 0; j < cellCount; j++) {
				updateCellNeighbours(board[i][j]);

				maybeAddCellToList(i, j, new Point(1, 0));
				maybeAddCellToList(i, j, new Point(1, 1));
				maybeAddCellToList(i, j, new Point(0, 1));
				maybeAddCellToList(i, j, new Point(-1, 1));

			}
		}
	}

	private void maybeAddCellToList(int x, int y, Point movement) {
		int newX = x + movement.x;
		int newY = y + movement.y;
		boolean xModified = false;
		boolean yModified = false;
		if (checkEnvironment) {
			if (newX > cellCount - 1) {
				xModified = true;
				newX = 0;
			} else if (newX < 0) {
				xModified = true;
				newX = cellCount - 1;
			}
			if (newY > cellCount - 1) {
				yModified = true;
				newY = 0;
			} else if (newY < 0) {
				yModified = true;
				newY = cellCount - 1;
			}
		}
		if (newX >= 0 && newX < cellCount && newY >= 0 && newY < cellCount) {
			doUpdate(board[x][y], board[newX][newY], xModified, yModified);
		}
	}

	private void updateCellNeighbours(Cell cell) {
		for (Particle particle1 : cell.particles) {
			for (Particle particle2 : cell.particles) {
				if (particle1.id != particle2.id && particle1.isInRadius(particle2, convergenceRadius)) {
					particle1.getNeighbours().add(particle2);
				}
			}
		}
	}

	private void doUpdate(Cell cell1, Cell cell2, boolean xModified, boolean yModified) {
		for (Particle particle1 : cell1.particles) {
			for (Particle particle2 : cell2.particles) {

				Particle particle1ToCompare = particle1;
				Particle particle2ToCompare = particle2;
				if (xModified && particle1.x > particle2ToCompare.x) {
					particle2ToCompare = new Particle(
							particle2.id,
							particle2.x + Test.BOARD_SIZE, 
							particle2.y,
							particle2.radius);
				} else if (xModified && particle1.x < particle2ToCompare.x) {
					particle1ToCompare = new Particle(
							particle1.id,
							particle1.x + Test.BOARD_SIZE, 
							particle1.y,
							particle1.radius);
				}
				if (yModified && particle1.y > particle2ToCompare.y) {
					particle2ToCompare = new Particle(
							particle2.id,
							particle2ToCompare.x,
							particle2ToCompare.y + Test.BOARD_SIZE,
							particle2.radius);
				}

				if (particle1ToCompare.isInRadius(particle2ToCompare, convergenceRadius)) {
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
