package solar_system;

import solar_system.model.Particle;
import solar_system.model.State;
import solar_system.model.Vector;
import solar_system.verlet.VerletCalculator;

import java.awt.*;

public class Parameters {

    private static final double EARTH_INITIAL_X = 1.391734353396533E8 * 1000;
    private static final double EARTH_INITIAL_Y = -0.571059040560652E8 * 1000;
    private static final double EARTH_INITIAL_SPEED_X = 10.801963811159256 * 1000;
    private static final double EARTH_INITIAL_SPEED_Y = 27.565215006898345 * 1000;
    private static final double EARTH_RADIUS = 6371 * 1000;
    private static final double EARTH_MASS = 5.972E24;
    private static final Color EARTH_COLOR = Color.blue;

    private static final double MARS_INITIAL_X = 0.831483493435295E8 * 1000;
    private static final double MARS_INITIAL_Y = -1.914579540822006E8 * 1000;
    private static final double MARS_INITIAL_SPEED_X = 23.637912321314047 * 1000;
    private static final double MARS_INITIAL_SPEED_Y = 11.429021426712032 * 1000;
    private static final double MARS_RADIUS = 3389.9 * 1000;
    private static final double MARS_MASS = 6.4185E23;
    private static final Color MARS_COLOR = Color.red;

    private static final double SUN_INITIAL_X = 0;
    private static final double SUN_INITIAL_Y = 0;
    private static final double SUN_INITIAL_SPEED_X = 0;
    private static final double SUN_INITIAL_SPEED_Y = 0;
    private static final double SUN_RADIUS = 695700 * 1000;
    private static final double SUN_MASS = 1.988E30;
    private static final Color SUN_COLOR = Color.yellow;

    private static final double SHIP_INITIAL_HEIGHT = 1500 * 1000;
    private static final double SHIP_INITIAL_BASE_SPEED = 7.12 * 1000;
    private static final double SHIP_INITIAL_ADDED_SPEED = 17 * 1000;
    private static final double SHIP_RADIUS = MARS_RADIUS;
    private static final double SHIP_MASS = 2E5;
    private static final Color SHIP_COLOR = Color.green;

    public static final double TIME_STEP = 10;

    public static State initialState() {
        State state =  new State(initialSun(), initialEarth(), initialMars(), initialShip());
        Particle updatedMars = state.getMars().withOldPosition(previousPosition(state, state.getMars()));
        Particle updatedEarth = state.getEarth().withOldPosition(previousPosition(state, state.getEarth()));
        Particle updatedShip = state.getShip().withOldPosition(previousPosition(state, state.getShip()));
        return new State(state.getSun(), updatedEarth, updatedMars, updatedShip);
    }

    private static Particle initialSun() {
        return new Particle(
                1,
                new Vector(SUN_INITIAL_X, SUN_INITIAL_Y),
                new Vector(SUN_INITIAL_SPEED_X, SUN_INITIAL_SPEED_Y),
                SUN_MASS,
                SUN_RADIUS,
                SUN_COLOR);
    }

    private static Particle initialMars() {
        return new Particle(
                2,
                new Vector(MARS_INITIAL_X, MARS_INITIAL_Y),
                new Vector(MARS_INITIAL_SPEED_X, MARS_INITIAL_SPEED_Y),
                MARS_MASS,
                MARS_RADIUS,
                MARS_COLOR);
    }

    private static Particle initialEarth() {
        return new Particle(
                3,
                new Vector(EARTH_INITIAL_X, EARTH_INITIAL_Y),
                new Vector(EARTH_INITIAL_SPEED_X, EARTH_INITIAL_SPEED_Y),
                EARTH_MASS,
                EARTH_RADIUS,
                EARTH_COLOR);
    }

    private static Particle initialShip() {
        return new Particle(
                4,
                initialShipPosition(),
                initialShipSpeed(),
                SHIP_MASS,
                SHIP_RADIUS,
                SHIP_COLOR);
    }

    private static Vector initialShipPosition() {
        double xPercentage = EARTH_INITIAL_X / new Vector(EARTH_INITIAL_X, EARTH_INITIAL_Y).norm();
        double xPosition = EARTH_INITIAL_X + EARTH_RADIUS + xPercentage * SHIP_INITIAL_HEIGHT;
        double yPosition = EARTH_INITIAL_Y + EARTH_RADIUS + (1 - xPercentage) * SHIP_INITIAL_HEIGHT;
        return new Vector(xPosition, yPosition);
    }

    private static Vector initialShipSpeed() {
        double earthSpeed = new Vector(EARTH_INITIAL_SPEED_X, EARTH_INITIAL_SPEED_Y).norm();
        double xPercentage = EARTH_INITIAL_SPEED_X / earthSpeed;
        double totalSpeed = earthSpeed + SHIP_INITIAL_BASE_SPEED + SHIP_INITIAL_ADDED_SPEED;
        double xSpeed = xPercentage * totalSpeed;
        double ySpeed = (1 - xPercentage) * totalSpeed;
        return new Vector(xSpeed, ySpeed);
    }

    private static Vector previousPosition(State state, Particle particle) {
        return particle.getPosition()
                .subtract(particle.getSpeed().scale(TIME_STEP))
                .add(calculateForce(state, particle).scale(TIME_STEP * TIME_STEP / (2 * particle.getMass())));
    }

    private static Vector calculateForce(State state, Particle particle) {
        return new VerletCalculator().calculateForce(state, particle);
    }

}