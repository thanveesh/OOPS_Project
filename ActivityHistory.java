import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityHistory {
    private List<EmissionActivity> activities = new ArrayList<>();

    public void addActivity(EmissionActivity activity) {
        activities.add(activity);
    }

    public double getTotalEmissions() {
        return activities.stream().mapToDouble(EmissionActivity::calculateEmissions).sum();
    }

    public void printHistory() {
        if (activities.isEmpty()) {
            System.out.println("No activities yet.");
            return;
        }
        System.out.println("\nActivity History:");
        for (int i = 0; i < activities.size(); i++) {
            EmissionActivity activity = activities.get(i);
            System.out.printf("%d. %s: %s | Emissions: %.2f kg CO2\n",
                    i + 1, activity.getActivityType(),
                    activity.getDetails(),
                    activity.calculateEmissions());
        }
    }

    public String getTopSuggestion() {
        double travel = activities.stream()
                .filter(a -> a.getActivityType().equals("Travel"))
                .mapToDouble(EmissionActivity::calculateEmissions).sum();
        double total = getTotalEmissions();
        if (total == 0) return "Add some activities to get suggestions!";
        if ((travel / total) > 0.5) return "Consider reducing car travel or using public transport more.";
        return "Great job! Keep tracking and finding ways to improve.";
    }

    // 🔹 Step 2: Added for UI integration
    public List<EmissionActivity> getActivities() {
        return Collections.unmodifiableList(activities); // prevents accidental modification
    }

    // Optional helper if you want "Clear History" button in UI
    public void clearHistory() {
        activities.clear();
    }
}
