import java.util.LinkedList;
import java.util.List;

public class Main {

	private static final int PARTICLE_COUNT = 1000;
	private static final int BOARD_SIZE = 100;
	private static final int CELL_SIZE = 10;
	private static final int CONVERGENCE_RADIUS = 10;
	
	public static void main(String[] args) {
		int cellCount = BOARD_SIZE / CELL_SIZE;
		Cell[][] board = new Cell[cellCount][cellCount];
		for (int i = 0; i < cellCount; i++) {
			for (int j = 0; j < cellCount; j++) {
				board[i][j] = new Cell();
			}
		}
		for (int i = 0; i < PARTICLE_COUNT; i++) {
			Particle newParticle = Particle.random(i);
			board[newParticle.x / CELL_SIZE][newParticle.y / CELL_SIZE].particles.add(newParticle);
		}
		
		for (int i = 0; i < cellCount; i++) {
			for (int j = 0; j < cellCount; j++) {
				if (i < cellCount - 1) {
					updateCells(board[i][j], board[i+1][j]);
					if (j < cellCount - 1) {
						updateCells(board[i][j], board[i+1][j+1]);
					}
				}
				if (j < cellCount - 1) {
					updateCells(board[i][j], board[i][j+1]);
					if (i > 0) {
						updateCells(board[i][j], board[i-1][j+1]);	
					}
				}
			}
		}
		
		
		for (int i = 0; i < cellCount; i++) {
			for (int j = 0; j < cellCount; j++) {
				//System.out.println();
				//System.out.println("New position: " + i + ", " + j);
				for (Particle particle: board[i][j].particles) {
					//System.out.println("Particle " + particle.index + ": " + particle.x + ", " + particle.y);
					System.out.print(particle.index);
					for (Particle neighbourParticle: particle.neighbours) {
						System.out.print(" " + neighbourParticle.index);
					}
					System.out.println();
				}
			}
		}
	}
	
	private static void updateCells(Cell cell1, Cell cell2) {
		for (Particle particle1: cell2.particles) {
			for (Particle particle2: cell1.particles) {
				if (particle1.isInRadius(particle2)) {
					particle1.neighbours.add(particle2);
					particle2.neighbours.add(particle1);
				}
			}
		}
	}
	
	static class Cell {
		
		List<Particle> particles = new LinkedList<>();
		
	}
	
	static class Particle {
		int index;
		int x, y, radius;
		
		List<Particle> neighbours = new LinkedList<>();

		public Particle(int index, int x, int y, int radius) {
			super();
			this.index = index;
			this.x = x;
			this.y = y;
			this.radius = radius;
		}
		
		public boolean isInRadius(Particle otherParticle) {
			int xDiff = Math.abs(x - otherParticle.x);
			int yDiff = Math.abs(y - otherParticle.y);
			double totalDiff = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			totalDiff -= radius;
			totalDiff -= otherParticle.radius;
			return totalDiff <= CONVERGENCE_RADIUS;
		}
		
		static Particle random(int index) {
			int x = (int) (Math.random() * BOARD_SIZE);
			int y = (int) (Math.random() * BOARD_SIZE);
			int radius = (int) (Math.random() * CONVERGENCE_RADIUS);
			return new Particle(index, x, y ,radius);
		}
		
		
	}
	
}
