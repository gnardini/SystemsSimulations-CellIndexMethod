package solar_system.model;

import solar_system.Parameters;

public class State {

    private final Particle sun;
    private final Particle earth;
    private final Particle mars;
    private final Particle ship;

    public State(Particle sun, Particle earth, Particle mars, Particle ship) {
        this.sun = sun;
        this.earth = earth;
        this.mars = mars;
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

    public Particle getShip() {
        return ship;
    }

    public State launchShip() {

        Particle newShip = ship.launch().withNewData(initialShipPosition(), initialShipSpeed());
        newShip = newShip.withOldPosition(Parameters.previousPosition(this, newShip));

        return new State(sun, earth, mars, newShip);
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

    private Vector initialShipSpeed() {
        double mod = earth.getPosition().norm();
        double enx =  earth.getPosition().getX() / mod;
        double eny =  earth.getPosition().getY() / mod;

        double etx = -eny;
        double ety = enx;

        double totalSpeed = earth.getSpeed().norm()
                + Parameters.SHIP_INITIAL_BASE_SPEED
                + Parameters.SHIP_INITIAL_ADDED_SPEED;

        double xSpeed = totalSpeed * etx;
        double ySpeed = totalSpeed * ety;
        return new Vector(xSpeed, ySpeed);
    }

}
