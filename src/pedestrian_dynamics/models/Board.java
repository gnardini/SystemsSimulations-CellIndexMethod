package pedestrian_dynamics.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private int M;
    private double L;

    private Cell[][] cells;

    private List<HorizontalWall> horizontalWalls;

    private Board(int M, double L) {
        this.M = M;
        this.L = L;
        cells = new Cell[M][M];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public Board(List<HorizontalWall> horizontalWalls, int M, double L, List<Particle> particles) {
        this(M, L);
        this.horizontalWalls = horizontalWalls;
        particles.forEach(this::addParticle);
    }

    public void addParticle(Particle particle) {
        int row = (int) ((particle.getX() * M) / L);
        int col = (int) ((particle.getY() * M) / L);
        if (row < 0) row = 0;
        else if (row >= M) row = M - 1;
        if (col < 0) col = 0;
        else if (col >= M) col = M - 1;
        cells[row][col].addParticle(particle);
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

    public double getL() {
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
        return new Board(this.horizontalWalls, M, L, particles);
    }

    public Board withNewParticles(List<Particle> newParticles) {
        return new Board(this.horizontalWalls, M, L, newParticles);
    }

    public List<HorizontalWall> getHorizontalWalls() {
        return horizontalWalls;
    }

}
