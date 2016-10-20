package granular_medium;

public class Parameters {

  private static final double kn = Math.pow(10, 5);
  private static final double kt =  2 * kn;
  public static final double PARTICLES_MASS = 0.01;
  public static final double DELTA_TIME = 1e-5;

  // System parameters - L > W > D
  public static final double L = 2;
  public static final double W = 1;
  public static final double D = 0.25;

  public double getL() {
    return L;
  }

  public double getW() {
    return W;
  }

  public double getD() {
    return D;
  }

  public double getKn() {
    return kn;
  }

  public double getKt() {
    return kt;
  }

}
