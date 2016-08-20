package models;

import java.util.List;

public class Board {
	private int M; // M * M is the total number of cells
	private int L; // L is the size of the side of the board
	private int convergenceRadius;

	private Cell[][] matrix;

	public Board(int M, int L, int convergenceRadius) {
		this.M = M;
		this.L = L;
		this.convergenceRadius = convergenceRadius;
		initialize();
	}

	private void initialize() {
		this.matrix = new Cell[M][M];

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				matrix[i][j] = new Cell();
			}
		}
	}

	public void addRandomParticle(int id) {
		Particle newParticle = Particle.random(id, M);
		addParticle(newParticle);
	}

	public void addParticle(Particle particle) {
		int row = (int) ((particle.getX() * M) / L);
		int col = (int) ((particle.getY() * M) / L);
		matrix[row][col].addParticle(particle);
	}

	public List<Particle> particlesAt(int i, int j) {
		return matrix[i][j].getParticles();
	}

	public Cell getCell(int i, int j) {
		return this.matrix[i][j];
	}

	public int getM() {
		return M;
	}

	public int getL() {
		return L;
	}

	public int getConvergenceRadius() {
		return convergenceRadius;
	}

}
