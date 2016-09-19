package tp4.model;

public class OscillatorInstant {

    private OscillatorState state;

    public OscillatorInstant(OscillatorState state) {
        this.state = state;
    }

    public double getTime() {
        return state.getTime();
    }

    public double getPosition() {
        return state.getLastPosition();
    }
}
