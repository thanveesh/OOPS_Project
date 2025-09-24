import java.util.Scanner;

public class CarbonEmissionCalculatorApp {
    private Scanner scanner = new Scanner(System.in);
    private ActivityHistory history = new ActivityHistory();

    public void run() {
        while (true) {
            System.out.println("\n--- Carbon Emission Calculator ---");
            System.out.println("1. Add Travel");
            System.out.println("2. Add Electricity Usage");
            System.out.println("3. Add Food Intake");
            System.out.println("4. View Activity History");
            System.out.println("5. View Total Emissions");
            System.out.println("6. Get Suggestion");
            System.out.println("7. Exit");
            System.out.print("Select option: ");
            int ch = scanner.nextInt();

            switch (ch) {
                case 1: addTravel(); break;
                case 2: addElectricity(); break;
                case 3: addFood(); break;
                case 4: history.printHistory(); break;
                case 5: System.out.printf("Total emissions: %.2f kg CO2\n", history.getTotalEmissions()); break;
                case 6: System.out.println(history.getTopSuggestion()); break;
                case 7: System.out.println("Thank you!"); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void addTravel() {
        System.out.print("Enter kilometers: ");
        double km = scanner.nextDouble();
        System.out.print("Vehicle type (car/bus): ");
        String mode = scanner.next();
        history.addActivity(new Travel(km, mode));
        System.out.println("Travel record added.");
    }

    private void addElectricity() {
        System.out.print("Enter kWh used: ");
        double kwh = scanner.nextDouble();
        history.addActivity(new Electricity(kwh));
        System.out.println("Electricity usage recorded.");
    }

    private void addFood() {
        System.out.print("Enter food amount (kg): ");
        double kg = scanner.nextDouble();
        System.out.print("Type (meat/veg): ");
        String type = scanner.next();
        history.addActivity(new FoodIntake(kg, type));
        System.out.println("Food intake recorded.");
    }

    public static void main(String[] args) {
        new CarbonEmissionCalculatorApp().run();
    }
}
