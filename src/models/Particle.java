package models;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Particle {
	private int id;
	private double x, y;
	private double radius;
	private double color;
	private Set<Particle> neighbours;

	public Particle(int id, double x, double y, double radius, double color) {
		this.id = id;
    this.x = x;
    this.y = y;
		this.radius = radius;
		this.color = color;
    this.neighbours = new HashSet<>();
	}

	public Particle(int id, double x, double y, double radius) {
		this.id = id;
    this.x = x;
    this.y = y;
		this.radius = radius;
    this.neighbours = new HashSet<>();
	}

  public static Particle random(int id, int boardSize) {
    double randomX = 0, randomY = 0;
    while (randomX == 0 || randomY == 0) {
      randomX = Math.random();
      randomY = Math.random();
    }
    double x = (randomX * boardSize);
    double y = (randomY * boardSize);
    return new Particle(id, x, y, 0.25);
  }

  public double getX() {
    return x;
  }

	public double getY() {
		return y;
	}

	public int getId() {
		return id;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getColor() {
		return color;
	}

	public Set<Particle> getNeighbours() {
	  return neighbours;
  }

	public boolean isInRadius(Particle otherParticle, int convergenceRadius) {
		double xDiff = Math.abs(x - otherParticle.getX());
		double yDiff = Math.abs(y - otherParticle.getY());
		double totalDiff = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		totalDiff -= radius;
		totalDiff -= otherParticle.radius;
		return totalDiff <= convergenceRadius;
	}

	public boolean isNeighbourOf(int particleId) {
		for (Particle neighbour : neighbours) {
			if (neighbour.id == particleId) {
				return true;
			}
		}
		return false;
	}

	public void addNeighbour(Particle particle) {
	  neighbours.add(particle);
    return;
  }

	public String getTwoDecimalRadius() {
		return String.format("%.02f", radius);
	}

  @Override
  public boolean equals(Object obj) {
    return id == ((Particle) obj).getId();
  }

  @Override
  public String toString() {
    return "id: " + id + ", x: " + x + ", y: " + y + ", radius: " + radius;
  }

  public String toStringWithNeighbours() {
    StringBuilder ans = new StringBuilder();
    ans.append("[ " + id);
    ans.append(": ");
    for (Particle particle: neighbours) {
      ans.append(particle.getId() + " ");
    }
    ans.append("]");
    return ans.toString();
  }

}
