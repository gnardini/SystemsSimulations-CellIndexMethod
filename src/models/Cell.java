package models;


import java.util.ArrayList;
import java.util.List;

public class Cell {
  private List<Particle> particles;

  public Cell() {
    this.particles = new ArrayList<>();
  }

  public List<Particle> getParticles() {
    return this.particles;
  }

  public void addParticle(Particle p) {
    this.particles.add(p);
  }
}
