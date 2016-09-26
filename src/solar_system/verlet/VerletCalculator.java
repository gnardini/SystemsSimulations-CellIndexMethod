package solar_system.verlet;

import solar_system.model.Particle;
import solar_system.model.State;
import solar_system.model.Vector;

public class VerletCalculator {

    private static final double G = 6.6738431e-11;

    public State updateState(State previousState, double deltaTime) {

        Particle sun = evolveParticle(previousState.getSun(), previousState, deltaTime);
        Particle earth = evolveParticle(previousState.getEarth(), previousState, deltaTime);
        Particle mars = evolveParticle(previousState.getMars(), previousState, deltaTime);
        Particle ship = evolveParticle(previousState.getShip(), previousState, deltaTime);

        return new State(sun, earth, mars, ship);
    }

    private Particle evolveParticle(Particle particle, State state, double deltaTime) {
        if (!particle.isLaunched()) return particle;

        //x(i+1) = 2 x(i) - x(i-1) + t^2/m * f(i)
        Vector newPosition = particle.getPosition()
                .scale(2)
                .subtract(particle.getOldPosition())
                .add(calculateForce(state, particle).scale(deltaTime * deltaTime / particle.getMass()));

        //v(i) = (x(i+1) - x(i -1)) / 2*t
        Vector newSpeed = newPosition.subtract(particle.getOldPosition()).scale(1.0 / (2 * deltaTime));

        return particle.withNewData(newPosition, newSpeed);
    }

    public Vector calculateForce(State state, Particle particle) {
        return forceBetween(state.getSun(), particle)
                .add(forceBetween(state.getEarth(), particle))
                .add(forceBetween(state.getMars(), particle))
                .add(forceBetween(state.getShip(), particle));
    }

    private Vector forceBetween(Particle particle1, Particle particle2) {
        if (particle1.getId() == particle2.getId() || !particle1.isLaunched() || !particle2.isLaunched()) {
            return new Vector(0, 0);
        }
        Vector particle1Position = particle1.getPosition();
        Vector particle2Position = particle2.getPosition();
        return particle1Position.subtract(particle2Position).normalize()
                .scale(G * particle1.getMass() * particle2.getMass()
                        / Math.pow(particle1Position.distanceTo(particle2Position), 2));

    }

}
