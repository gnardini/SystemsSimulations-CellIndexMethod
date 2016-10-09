package GranularMedium;

/**
 * Created by FranDepascuali on 10/8/16.
 */
public class Parameters {

  private static double kn = Math.pow(10, 5);
  private static double kt =  2 * kn;
  private static double particlesMass = 0.01;

  private static double particleRadious;

  // System parameters - L > W > D
  private static double L;
  private static double W;
  private static double D;

  public Parameters(double L, double W, double D, double particleRadious) {
    this.L = L;
    this.W = W;
    this.D = D;
    this.particleRadious = particleRadious;
  }

}
