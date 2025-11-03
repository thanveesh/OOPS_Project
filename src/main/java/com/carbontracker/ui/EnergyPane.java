package com.carbontracker.ui;

import com.carbontracker.model.EmissionCalculator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Energy emissions input pane
 */
public class EnergyPane {
    
    private ScrollPane scrollPane;
    private EmissionCalculator calculator;
    private Runnable updateCallback;
    
    public EnergyPane(EmissionCalculator calculator, Runnable updateCallback) {
        this.calculator = calculator;
        this.updateCallback = updateCallback;
        createPane();
    }
    
    private void createPane() {
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(35));
        mainContent.setAlignment(Pos.TOP_CENTER);
        
        Label title = new Label("⚡ Energy Consumption");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#f59e0b"));
        
        Label subtitle = new Label("Track your household energy usage and discover reduction strategies");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
        subtitle.setTextFill(Color.web("#94a3b8"));
        
        GridPane grid = new GridPane();
        grid.setHgap(25);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(25));
        grid.setStyle("-fx-background-color: #1e293b; " +
                     "-fx-background-radius: 15; " +
                     "-fx-border-color: #334155; " +
                     "-fx-border-width: 1.5; " +
                     "-fx-border-radius: 15; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
        
        // Electricity
        addInputRow(grid, 0, "💡 Electricity", "kWh", 
            value -> calculator.addElectricityEmission(value), "#f59e0b");
        
        // Natural Gas
        addInputRow(grid, 1, "🔥 Natural Gas", "therms", 
            value -> calculator.addNaturalGasEmission(value), "#f59e0b");
        
        // Info and tips box
        VBox infoBox = createInfoAndTipsBox();
        
        mainContent.getChildren().addAll(title, subtitle, grid, infoBox);
        
        // Wrap in ScrollPane
        scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; " +
                           "-fx-background: transparent;");
        scrollPane.setPannable(true);
    }
    
    private void addInputRow(GridPane grid, int row, String label, String unit, 
                            EmissionConsumer consumer, String color) {
        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        nameLabel.setTextFill(Color.web(color));
        
        TextField input = new TextField();
        input.setPromptText("Enter " + unit);
        input.setPrefWidth(180);
        input.setStyle("-fx-font-size: 15px; " +
                      "-fx-padding: 10; " +
                      "-fx-background-radius: 6; " +
                      "-fx-border-color: #dcdde1; " +
                      "-fx-border-radius: 6;");
        
        Label unitLabel = new Label(unit);
        unitLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
        unitLabel.setTextFill(Color.web("#7f8c8d"));
        
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: " + color + "; " +
                          "-fx-text-fill: white; " +
                          "-fx-font-weight: 600; " +
                          "-fx-font-family: 'Segoe UI'; " +
                          "-fx-background-radius: 6; " +
                          "-fx-cursor: hand; " +
                          "-fx-padding: 10 25; " +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 2);");
        
        addButton.setOnMouseEntered(e ->
            addButton.setStyle("-fx-background-color: derive(" + color + ", -10%); " +
                              "-fx-text-fill: white; " +
                              "-fx-font-weight: 600; " +
                              "-fx-font-family: 'Segoe UI'; " +
                              "-fx-background-radius: 6; " +
                              "-fx-cursor: hand; " +
                              "-fx-padding: 10 25; " +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 3);"));
        
        addButton.setOnMouseExited(e ->
            addButton.setStyle("-fx-background-color: " + color + "; " +
                              "-fx-text-fill: white; " +
                              "-fx-font-weight: 600; " +
                              "-fx-font-family: 'Segoe UI'; " +
                              "-fx-background-radius: 6; " +
                              "-fx-cursor: hand; " +
                              "-fx-padding: 10 25; " +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 2);"));
        
        Label resultLabel = new Label("");
        resultLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        resultLabel.setTextFill(Color.web("#27ae60"));
        
        addButton.setOnAction(e -> {
            try {
                double value = Double.parseDouble(input.getText());
                if (value > 0) {
                    consumer.consume(value);
                    updateCallback.run();
                    resultLabel.setText("✓ Added successfully!");
                    input.clear();
                    
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            javafx.application.Platform.runLater(() -> resultLabel.setText(""));
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                } else {
                    showError("Please enter a positive number");
                }
            } catch (NumberFormatException ex) {
                showError("Please enter a valid number");
            }
        });
        
        grid.add(nameLabel, 0, row);
        grid.add(input, 1, row);
        grid.add(unitLabel, 2, row);
        grid.add(addButton, 3, row);
        grid.add(resultLabel, 4, row);
    }
    
    private VBox createInfoAndTipsBox() {
        VBox infoBox = new VBox(18);
        infoBox.setPadding(new Insets(25));
        infoBox.setStyle("-fx-background-color: #1e293b; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #10b981; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
        
        Label infoTitle = new Label("📊 Emission Factors & Reduction Tips");
        infoTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        infoTitle.setTextFill(Color.web("#10b981"));
        
        VBox factorsBox = new VBox(8);
        Label info1 = new Label("💡 Electricity: 0.92 kg CO₂ per kWh");
        Label info2 = new Label("🔥 Natural Gas: 2.0 kg CO₂ per therm");
        
        for (Label info : new Label[]{info1, info2}) {
            info.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
            info.setTextFill(Color.web("#cbd5e1"));
        }
        factorsBox.getChildren().addAll(info1, info2);
        
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #334155;");
        
        Label tipsTitle = new Label("💡 How to Reduce Energy Emissions:");
        tipsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        tipsTitle.setTextFill(Color.web("#10b981"));
        tipsTitle.setStyle("-fx-padding: 10 0 5 0;");
        
        VBox tipsContent = new VBox(8);
        Label tip1 = createTipLabel("🌟 Switch to LED bulbs - they use 75% less energy than incandescent");
        Label tip2 = createTipLabel("🌡️ Set your thermostat 2°C lower in winter and higher in summer");
        Label tip3 = createTipLabel("☀️ Install solar panels - can reduce electricity emissions by 80-100%");
        Label tip4 = createTipLabel("🔌 Unplug chargers and appliances when not in use - saves 10% on bills");
        Label tip5 = createTipLabel("🏠 Improve home insulation - reduces heating/cooling needs by 30%");
        Label tip6 = createTipLabel("⚡ Use smart power strips to eliminate phantom power drain");
        Label tip7 = createTipLabel("🌊 Wash clothes in cold water - saves 90% of washing machine energy");
        
        tipsContent.getChildren().addAll(tip1, tip2, tip3, tip4, tip5, tip6, tip7);
        
        infoBox.getChildren().addAll(infoTitle, factorsBox, sep, tipsTitle, tipsContent);
        return infoBox;
    }
    
    private Label createTipLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        label.setTextFill(Color.web("#cbd5e1"));
        label.setWrapText(true);
        label.setMaxWidth(800);
        return label;
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public ScrollPane getPane() {
        return scrollPane;
    }
    
    @FunctionalInterface
    interface EmissionConsumer {
        void consume(double value);
    }
}
