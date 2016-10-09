package GranularMedium;

/**
 * Created by FranDepascuali on 10/9/16.
 */
public class Particle {

  private int id;

  private Vector2D position;
  private Vector2D speed;
  private Vector2D acceleration;

  private double radius;
  private double color;
  private double mass;

  public Particle(int id, Vector2D position, Vector2D speed, Vector2D acceleration, double radius, double color, double mass) {
    this.id = id;
    this.position = position;
    this.speed = speed;
    this.acceleration = acceleration;
    this.radius = radius;
    this.color = color;
    this.mass = mass;
  }

  public int getId() {
    return id;
  }

  public Vector2D getPosition() {
    return position;
  }

  public Vector2D getSpeed() {
    return speed;
  }

  public Vector2D getAcceleration() {
    return acceleration;
  }

  public double getRadius() {
    return radius;
  }

  public double getColor() {
    return color;
  }

  public double getMass() {
    return mass;
  }
}
