public class Electricity implements EmissionActivity {
    private double kwh;

    public Electricity(double kwh) {
        this.kwh = kwh;
    }

    @Override
    public double calculateEmissions() {
        return kwh * 0.92; // Example factor kg CO2 per kWh
    }

    @Override
    public String getActivityType() {
        return "Electricity";
    }

    @Override
    public String getDetails() {
        return kwh + " kWh";
    }
}
