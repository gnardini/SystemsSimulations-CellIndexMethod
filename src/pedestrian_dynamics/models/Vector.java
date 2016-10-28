package pedestrian_dynamics.models;

public class Vector {

  public static final Vector ZERO = new Vector(0, 0);

  private double x;
  private double y;

  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public Vector sum(Vector vector) {
    return new Vector(x + vector.x, y + vector.y);
  }

  public Vector sub(Vector vector) {
    return sum(vector.scale(-1));
  }

  public double multiply(Vector vector) {
    return this.getX() * vector.getX() + this.getY() * vector.getY();
  }

  public Vector scale(double l) {
    return new Vector(x * l, y * l);
  }

  public Vector rotateCCW(double angle) {
    double[][] rm = new double[][] { { Math.cos(angle), -Math.sin(angle) },
            { Math.sin(angle), Math.cos(angle) } };
    return new Vector(rm[0][0] * this.getX() + rm[0][1] * this.getY(),
            rm[1][0] * this.getX() + rm[1][1] * this.getY());
  }

  public Vector rotateCW(double angle) {
    return rotateCCW(2 * Math.PI - angle);
  }

  public double distanceTo(Vector vector) {
    return Math.sqrt(Math.pow((x - vector.x),2) + Math.pow((y - vector.y), 2));
  }

  public Vector normalize() {
    return scale(1 / (norm()));
  }

  public double norm() {
    return Math.sqrt(x * x + y * y);
  }

  public double scalarProduct(Vector vector) {
    return x * vector.x + y * vector.y;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

}
