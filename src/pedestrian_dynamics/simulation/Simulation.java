package pedestrian_dynamics.simulation;

import pedestrian_dynamics.Parameters;
import pedestrian_dynamics.models.State;
import pedestrian_dynamics.models.Vector;

public class Simulation {
    public static State updateState(State state, Parameters parameters) {

        return state;
    }

    public static Vector getForce(Vector normalVersor, Vector tangencialVersor, Vector relativeVelocity,
                                  double overlap, double kn, double kt) {
        Vector normalForce = normalVersor.scale(-1 * kn * overlap);
        double prod = tangencialVersor.scalarProduct(relativeVelocity);
        Vector tangencialForce = tangencialVersor.scale(-1 * kt * overlap * prod);
        return normalForce.sum(tangencialForce);
    }
}
