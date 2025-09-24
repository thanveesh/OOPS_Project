public class Travel implements EmissionActivity {
    private double kilometers;
    private String mode; // e.g., car, bus

    public Travel(double kilometers, String mode) {
        this.kilometers = kilometers;
        this.mode = mode;
    }

    @Override
    public double calculateEmissions() {
        double factor = mode.equalsIgnoreCase("car") ? 0.21 : 0.05;
        return kilometers * factor;
    }

    @Override
    public String getActivityType() {
        return "Travel";
    }

    @Override
    public String getDetails() {
        return mode + " - " + kilometers + " km";
    }
}
