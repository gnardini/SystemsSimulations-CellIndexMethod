package solar_system.model;

import solar_system.Parameters;

public class State {

    private final Particle sun;
    private final Particle earth;
    private final Particle mars;
    private final Particle mercury;
    private final Particle ship;

    public State(Particle sun, Particle earth, Particle mars, Particle mercury, Particle ship) {
        this.sun = sun;
        this.earth = earth;
        this.mars = mars;
        this.mercury = mercury;
        this.ship = ship;
    }

    public Particle getSun() {
        return sun;
    }

    public Particle getEarth() {
        return earth;
    }

    public Particle getMars() {
        return mars;
    }

    public Particle getMercury() {
        return mercury;
    }

    public Particle getShip() {
        return ship;
    }

    public State launchShip() {

        Particle newShip = ship.launch()
                .withNewData(initialShipPosition(), initialShipSpeed(Parameters.SHIP_INITIAL_ADDED_SPEED, 0));
        newShip = newShip.withOldPosition(Parameters.previousPosition(this, newShip));

        return new State(sun, earth, mars, mercury, newShip);
    }

    public State withShipStartingAngle(double angle) {
        return withShipStartingConditions(Parameters.SHIP_INITIAL_ADDED_SPEED, angle);
    }

    public State withShipStartingConditions(double speed, double angle) {
        Particle newShip = ship.launch()
                .withNewData(initialShipPosition(), initialShipSpeed(speed, angle));
        newShip = newShip.withOldPosition(Parameters.previousPosition(this, newShip));

        return new State(sun, earth, mars, mercury, newShip);
    }

    private Vector initialShipPosition() {
        double mod = earth.getPosition().norm();
        double enx =  earth.getPosition().getX() / mod;
        double eny =  earth.getPosition().getY() / mod;

        double etx = -eny;
        double ety = enx;

        double totalHeight = mod + earth.getRadius() + ship.getRadius() + Parameters.SHIP_INITIAL_HEIGHT;
        return new Vector(enx * totalHeight, eny * totalHeight);
    }

    private Vector initialShipSpeed(double speed, double angle) {
        double mod = earth.getPosition().norm();
        double enx =  earth.getPosition().getX() / mod;
        double eny =  earth.getPosition().getY() / mod;

        double etx = -eny;
        double ety = enx;

        double targetAngle = Math.atan2(ety, etx) - angle;

        etx = Math.cos(targetAngle);
        ety = Math.sin(targetAngle);

        double totalSpeed = earth.getSpeed().norm()
                + Parameters.SHIP_INITIAL_BASE_SPEED
                + speed;

        double xSpeed = totalSpeed * etx;
        double ySpeed = totalSpeed * ety;
        return new Vector(xSpeed, ySpeed);
    }

}
