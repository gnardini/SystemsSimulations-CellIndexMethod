package solar_system.model;

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

}
