package GranularMedium;

/**
 * Created by FranDepascuali on 10/9/16.
 */
public class Vector2D {

  private double x;
  private double y;

  public Vector2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public Vector2D sum(Vector2D vector) {
    return new Vector2D(x + vector.x, y + vector.y);
  }

  public Vector2D sub(Vector2D vector) {
    return sum(vector.scale(-1));
  }

  public double multiply(Vector2D vector) {
    return this.getX() * vector.getX() + this.getY() * vector.getY();
  }

  public Vector2D scale(double l) {
    return new Vector2D(x * l, y * l);
  }

  public Vector2D rotateCCW(double angle) {
    double[][] rm = new double[][] { { Math.cos(angle), -Math.sin(angle) },
            { Math.sin(angle), Math.cos(angle) } };
    return new Vector2D(rm[0][0] * this.getX() + rm[0][1] * this.getY(),
            rm[1][0] * this.getX() + rm[1][1] * this.getY());
  }

  public Vector2D rotateCW(double angle) {
    return rotateCCW(2 * Math.PI - angle);
  }

  public double distanceTo(Vector2D vector) {
    return Math.sqrt(Math.pow((x - vector.x),2) + Math.pow((y - vector.y), 2));
  }

  public Vector2D normalize() {
    return scale(1 / (norm()));
  }

  public double norm() {
    return Math.sqrt(x * x + y * y);
  }

  public double scalarProduct(Vector2D vector) {
    return x * vector.x + y * vector.y;
  }

}
