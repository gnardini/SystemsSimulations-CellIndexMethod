package cell_index_method.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {

	private int M;
	private int L;
	private Cell[][] cells;

	public Board(int M, int L) {
	    this.M = M;
        this.L = L;

		cells = new Cell[M][M];

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				cells[i][j] = new Cell();
			}
		}
	}

	public Board(int M, int L, List<Particle> particles) {
        this(M, L);
		particles.forEach(this::addParticle);
    }

	public void addParticle(Particle particle) {
		int row = (int) ((particle.getX() * M) / L);
		int col = (int) ((particle.getY() * M) / L);
		cells[row][col].addParticle(particle);
	}

	public void addRandomParticle(int id) {
		Particle newParticle = Particle.random(id, M);
		addParticle(newParticle);
	}

	public List<Particle> particlesAt(int i, int j) {
		return cells[i][j].getParticles();
	}

	public Cell getCell(int i, int j) {
		return cells[i][j];
	}

	public int getM() {
		return M;
	}

	public int getL() {
	    return L;
    }

	public List<Particle> getParticles() {
		List<Particle> particles = new ArrayList();

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < M; j++) {
				cells[i][j].getParticles().forEach(particles::add);
			}
		}

		return particles;
	}

	public Board copy() {
	    List<Particle> particles = new LinkedList<>();
        getParticles().forEach(particle -> particles.add(particle.clone()));
		return new Board(M, L, particles);
	}

}
