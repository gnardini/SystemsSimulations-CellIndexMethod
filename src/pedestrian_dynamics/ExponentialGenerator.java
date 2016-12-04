package pedestrian_dynamics;

import java.util.Random;

/**
 * Created by FranDepascuali on 12/4/16.
 */
public class ExponentialGenerator {

    public static double next(double rate) {
        return -Math.log(1.0 - new Random().nextDouble()) / rate;
    }
}
