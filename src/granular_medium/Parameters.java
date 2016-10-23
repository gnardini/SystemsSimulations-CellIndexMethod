package granular_medium;

public class Parameters {

  private static final double kn = Math.pow(10, 5);
  private static final double kt =  2 * kn;
  public static final double PARTICLES_MASS = 0.01;
  public static final double DELTA_TIME = 1e-5;

  // System parameters - L > W > D
  private final double L;
  private final double W;
  private final double D;

  public Parameters() {
    L = 2;
    W = 1;
    D = .25;
  }

  public Parameters(double l, double w, double d) {
    L = l;
    W = w;
    D = d;
  }

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
