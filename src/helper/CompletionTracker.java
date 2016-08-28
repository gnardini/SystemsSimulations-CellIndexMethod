package helper;

public class CompletionTracker {

    private static final double DEFAULT_DELTA = 10;

    private double nextPercentToShow;
    private double deltaToShow;

    public CompletionTracker() {
        this(DEFAULT_DELTA);
    }

    public CompletionTracker(double deltaToShow) {
        this.deltaToShow = deltaToShow;
        nextPercentToShow = deltaToShow;
    }

    public void updateCompletedPercentage(double percentage) {
        if (percentage * 100 > nextPercentToShow) {
            System.out.println(nextPercentToShow + "%");
            nextPercentToShow += deltaToShow;
        }
    }

}
