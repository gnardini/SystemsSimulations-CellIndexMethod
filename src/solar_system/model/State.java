package solar_system.model;

public class State {

    private final Particle sun;
    private final Particle earth;
    private final Particle mars;

    public State(Particle sun, Particle earth, Particle mars) {
        this.sun = sun;
        this.earth = earth;
        this.mars = mars;
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

}
