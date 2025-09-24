public class FoodIntake implements EmissionActivity {
    private double kg;
    private String foodType; // e.g., meat, veg

    public FoodIntake(double kg, String foodType) {
        this.kg = kg;
        this.foodType = foodType;
    }

    @Override
    public double calculateEmissions() {
        double factor = foodType.equalsIgnoreCase("meat") ? 14.0 : 2.0;
        return kg * factor;
    }

    @Override
    public String getActivityType() {
        return "Food";
    }

    @Override
    public String getDetails() {
        return kg + " kg " + foodType;
    }
}
