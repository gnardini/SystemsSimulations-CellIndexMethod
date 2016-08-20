package models;

import java.util.List;

public class Board {
	private int boardM;
	private int cellSize;
	private int convergenceRadius;

	private Cell[][] matrix;

	public Board(int boardM, int cellSize, int convergenceRadius) {
		this.boardM = boardM;
		this.cellSize = cellSize;
		this.convergenceRadius = convergenceRadius;
		initialize();
	}

	private void initialize() {
		this.matrix = new Cell[boardM][boardM];

		for (int i = 0; i < boardM; i++) {
			for (int j = 0; j < boardM; j++) {
				matrix[i][j] = new Cell();
			}
		}
	}

	public void addRandomParticle(int id) {
		Particle newParticle = Particle.random(id, boardM);
		addParticle(newParticle);
	}

	public void addParticle(Particle particle) {
		int row = (int) (particle.getX() / cellSize);
		int col = (int) (particle.getY() / cellSize);
		matrix[row][col].addParticle(particle);
	}

	public List<Particle> particlesAt(int i, int j) {
		return matrix[i][j].getParticles();
	}

	public Cell getCell(int i, int j) {
		return this.matrix[i][j];
	}

	public int getBoardM() {
		return boardM;
	}

	public int getCellSize() {
		return cellSize;
	}

	public int getConvergenceRadius() {
		return convergenceRadius;
	}

}
