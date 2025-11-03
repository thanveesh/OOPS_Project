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
 * Transport emissions input pane
 */
public class TransportPane {
    
    private ScrollPane scrollPane;
    private EmissionCalculator calculator;
    private Runnable updateCallback;
    
    public TransportPane(EmissionCalculator calculator, Runnable updateCallback) {
        this.calculator = calculator;
        this.updateCallback = updateCallback;
        createPane();
    }
    
    private void createPane() {
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(35));
        mainContent.setAlignment(Pos.TOP_CENTER);
        
        Label title = new Label("🚗 Transport Emissions");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#10b981"));
        
        Label subtitle = new Label("Monitor your daily commute and discover greener alternatives");
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
        
        // Car
        addInputRow(grid, 0, "🚗 Car", "kilometers", 
            value -> calculator.addCarEmission(value), "#f59e0b");
        
        // Bus
        addInputRow(grid, 1, "🚌 Bus", "kilometers", 
            value -> calculator.addBusEmission(value), "#10b981");
        
        // Train
        addInputRow(grid, 2, "🚆 Train", "kilometers", 
            value -> calculator.addTrainEmission(value), "#3b82f6");
        
        // Flight
        addInputRow(grid, 3, "✈️ Flight", "kilometers", 
            value -> calculator.addFlightEmission(value), "#94a3b8");
        
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
                    
                    // Clear success message after 2 seconds
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
        
        Label infoTitle = new Label("📊 Emission Factors & Greener Alternatives");
        infoTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        infoTitle.setTextFill(Color.web("#10b981"));
        
        VBox factorsBox = new VBox(8);
        Label info1 = new Label("🚗 Car: 0.21 kg CO₂/km (highest individual impact)");
        Label info2 = new Label("🚌 Bus: 0.089 kg CO₂/km (57% less than car)");
        Label info3 = new Label("🚆 Train: 0.041 kg CO₂/km (80% less than car)");
        Label info4 = new Label("✈️ Flight: 0.255 kg CO₂/km (consider for long distances only)");
        
        for (Label info : new Label[]{info1, info2, info3, info4}) {
            info.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
            info.setTextFill(Color.web("#cbd5e1"));
        }
        factorsBox.getChildren().addAll(info1, info2, info3, info4);
        
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #334155;");
        
        Label tipsTitle = new Label("💡 Smart Ways to Reduce Transport Emissions:");
        tipsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        tipsTitle.setTextFill(Color.web("#10b981"));
        tipsTitle.setStyle("-fx-padding: 10 0 5 0;");
        
        VBox tipsContent = new VBox(8);
        Label tip1 = createTipLabel("🚴 Walk or bike for trips under 5 km - zero emissions + health benefits!");
        Label tip2 = createTipLabel("🚌 Take public transit - one bus can remove 40 cars from the road");
        Label tip3 = createTipLabel("🚗 Carpool with colleagues - cut your commute emissions in half");
        Label tip4 = createTipLabel("🚆 Choose trains over planes for distances under 1000 km - 90% less emissions");
        Label tip5 = createTipLabel("🔋 Consider electric vehicles - 60-70% lower lifecycle emissions");
        Label tip6 = createTipLabel("🏠 Work from home when possible - eliminates commute entirely");
        Label tip7 = createTipLabel("📱 Combine errands into one trip - reduces total distance traveled");
        
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
