// CarbonEmissionCalculatorUI.java
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Map;
import java.util.stream.Collectors;

public class CarbonEmissionCalculatorUI extends Application {

    private ActivityHistory history = new ActivityHistory();
    private TableView<EmissionActivity> table = new TableView<>();
    private PieChart pieChart = new PieChart();
    private Label totalLabel = new Label("Total: 0.00 kg CO2");
    private Label suggestionLabel = new Label("");

    @Override
    public void start(Stage stage) {
        // --- Input fields ---
        // Travel
        TextField kmField = new TextField();
        kmField.setPromptText("kilometers (e.g. 10.5)");
        TextField modeField = new TextField();
        modeField.setPromptText("vehicle (car / bus)");

        Button addTravelBtn = new Button("Add Travel");

        // Electricity
        TextField kwhField = new TextField();
        kwhField.setPromptText("kWh used");
        Button addElecBtn = new Button("Add Electricity");

        // Food
        TextField foodKgField = new TextField();
        foodKgField.setPromptText("kg (e.g. 0.3)");
        TextField foodTypeField = new TextField();
        foodTypeField.setPromptText("type (meat / veg)");
        Button addFoodBtn = new Button("Add Food");

        // Controls
        Button clearBtn = new Button("Clear History");

        // Table columns
        TableColumn<EmissionActivity, String> idxCol = new TableColumn<>("#");
        idxCol.setCellValueFactory(cell -> 
            new SimpleStringProperty(String.valueOf(table.getItems().indexOf(cell.getValue()) + 1))
        );
        idxCol.setPrefWidth(40);

        TableColumn<EmissionActivity, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getActivityType()));
        typeCol.setPrefWidth(100);

        TableColumn<EmissionActivity, String> detailsCol = new TableColumn<>("Details");
        detailsCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDetails()));
        detailsCol.setPrefWidth(220);

        TableColumn<EmissionActivity, Number> emissionsCol = new TableColumn<>("Emissions (kg CO2)");
        emissionsCol.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().calculateEmissions()).asObject());
        emissionsCol.setPrefWidth(140);

        table.getColumns().addAll(idxCol, typeCol, detailsCol, emissionsCol);
        table.setPrefHeight(240);

        // Layout - inputs grouped
        HBox travelBox = new HBox(8, kmField, modeField, addTravelBtn);
        HBox elecBox = new HBox(8, kwhField, addElecBtn);
        HBox foodBox = new HBox(8, foodKgField, foodTypeField, addFoodBtn);
        VBox inputs = new VBox(8, new Label("Add Activities"), travelBox, elecBox, foodBox, clearBtn);
        inputs.setPadding(new Insets(8));
        inputs.setPrefWidth(420);

        // Right side - table + pie chart + summary
        VBox right = new VBox(10, new Label("History"), table, new Label("Emissions Breakdown"), pieChart, totalLabel, suggestionLabel);
        right.setPadding(new Insets(8));

        HBox root = new HBox(12, inputs, right);
        root.setPadding(new Insets(12));

        // --- Handlers ---
        addTravelBtn.setOnAction(e -> {
            try {
                double km = Double.parseDouble(kmField.getText().trim());
                String mode = modeField.getText().trim();
                if (mode.isEmpty()) mode = "car";
                history.addActivity(new Travel(km, mode));
                kmField.clear(); modeField.clear();
                updateUI();
            } catch (NumberFormatException ex) {
                showError("Travel input invalid", "Please enter a valid number for kilometers.");
            }
        });

        addElecBtn.setOnAction(e -> {
            try {
                double kwh = Double.parseDouble(kwhField.getText().trim());
                history.addActivity(new Electricity(kwh));
                kwhField.clear();
                updateUI();
            } catch (NumberFormatException ex) {
                showError("Electricity input invalid", "Please enter a valid number for kWh.");
            }
        });

        addFoodBtn.setOnAction(e -> {
            try {
                double kg = Double.parseDouble(foodKgField.getText().trim());
                String type = foodTypeField.getText().trim();
                if (type.isEmpty()) type = "veg";
                history.addActivity(new FoodIntake(kg, type));
                foodKgField.clear(); foodTypeField.clear();
                updateUI();
            } catch (NumberFormatException ex) {
                showError("Food input invalid", "Please enter a valid number for kg.");
            }
        });

        clearBtn.setOnAction(e -> {
            history.clearHistory();
            updateUI();
        });

        // Initial UI populate
        updateUI();

        Scene scene = new Scene(root, 920, 620);
        stage.setTitle("Carbon Emission Tracker — UI");
        stage.setScene(scene);
        stage.show();
    }

    private void updateUI() {
        ObservableList<EmissionActivity> obs = FXCollections.observableArrayList(history.getActivities());
        table.setItems(obs);

        // Pie chart: group by activity type
        Map<String, Double> byType = history.getActivities().stream()
                .collect(Collectors.groupingBy(EmissionActivity::getActivityType,
                        Collectors.summingDouble(EmissionActivity::calculateEmissions)));

        pieChart.getData().clear();
        byType.forEach((k, v) -> {
            if (v > 0.0) pieChart.getData().add(new PieChart.Data(k, v));
        });

        totalLabel.setText(String.format("Total: %.2f kg CO2", history.getTotalEmissions()));
        suggestionLabel.setText("Suggestion: " + history.getTopSuggestion());
    }

    private void showError(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}