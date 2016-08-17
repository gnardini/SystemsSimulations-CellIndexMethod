package output;

import java.util.LinkedList;
import java.util.List;

public class Particle {
	public int id;
	public float x, y, radius;

	private List<Particle> neighbours = new LinkedList<>();

	public Particle(int id, float x, float y, float radius) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public boolean isInRadius(Particle otherParticle, int convergenceRadius) {
		float xDiff = Math.abs(x - otherParticle.x);
		float yDiff = Math.abs(y - otherParticle.y);
		double totalDiff = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		totalDiff -= radius;
		totalDiff -= otherParticle.radius;
		boolean isInRadius = totalDiff <= convergenceRadius;
		if (id == 1 || otherParticle.id == 1) {
			System.out.println(isInRadius + " " + totalDiff + " " + toString() + " " + otherParticle.toString());
		}
		return isInRadius;
	}

	public boolean isNeighbourOf(int particleId) {
		for (Particle neighbour : neighbours) {
			if (neighbour.id == particleId) {
				return true;
			}
		}
		return false;
	}

	public String getTwoDecimalRadius() {
		return String.format("%.02f", radius);
	}

	public static Particle random(int id, int boardSize) {
		float randomX = 0, randomY = 0;
		while (randomX == 0 || randomY == 0) {
			randomX = (float) Math.random();
			randomY = (float) Math.random();
		}
		float x = (float) (randomX * boardSize);
		float y = (float) (randomY * boardSize);
		return new Particle(id, x, y, .25f);
	}

	List<Particle> getNeighbours() {
		return this.neighbours;
	}

	@Override
	public String toString() {
		return "x: " + x + ", y: " + y + ", radius: " + radius;
	}

}
