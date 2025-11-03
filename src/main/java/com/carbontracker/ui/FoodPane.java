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
 * Food emissions input pane
 */
public class FoodPane {
    
    private VBox pane;
    private EmissionCalculator calculator;
    private Runnable updateCallback;
    
    public FoodPane(EmissionCalculator calculator, Runnable updateCallback) {
        this.calculator = calculator;
        this.updateCallback = updateCallback;
        createPane();
    }
    
    private void createPane() {
        pane = new VBox(20);
        pane.setPadding(new Insets(30));
        pane.setAlignment(Pos.TOP_CENTER);
        
        Label title = new Label("🍽️ Food Emissions");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#e53935"));
        
        Label subtitle = new Label("Track the carbon footprint of your diet");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitle.setTextFill(Color.web("#666666"));
        
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: white; " +
                     "-fx-background-radius: 15; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        
        // Beef
        addInputRow(grid, 0, "🥩 Beef", "kg", 
            value -> calculator.addBeefEmission(value), "#d32f2f");
        
        // Pork
        addInputRow(grid, 1, "🥓 Pork", "kg", 
            value -> calculator.addPorkEmission(value), "#e64a19");
        
        // Chicken
        addInputRow(grid, 2, "🍗 Chicken", "kg", 
            value -> calculator.addChickenEmission(value), "#f57c00");
        
        // Vegetarian meals
        addVegetarianRow(grid, 3);
        
        // Info box
        VBox infoBox = createInfoBox();
        
        pane.getChildren().addAll(title, subtitle, grid, infoBox);
    }
    
    private void addInputRow(GridPane grid, int row, String label, String unit, 
                            EmissionConsumer consumer, String color) {
        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web(color));
        
        TextField input = new TextField();
        input.setPromptText("Enter " + unit);
        input.setPrefWidth(150);
        input.setStyle("-fx-font-size: 14px; -fx-padding: 8;");
        
        Label unitLabel = new Label(unit);
        unitLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: " + color + "; " +
                          "-fx-text-fill: white; " +
                          "-fx-font-weight: bold; " +
                          "-fx-background-radius: 8; " +
                          "-fx-cursor: hand;");
        
        Label resultLabel = new Label("");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        resultLabel.setTextFill(Color.web("#43a047"));
        
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
    
    private void addVegetarianRow(GridPane grid, int row) {
        Label nameLabel = new Label("🥗 Vegetarian Meals");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web("#43a047"));
        
        TextField input = new TextField();
        input.setPromptText("Enter number");
        input.setPrefWidth(150);
        input.setStyle("-fx-font-size: 14px; -fx-padding: 8;");
        
        Label unitLabel = new Label("meals");
        unitLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #43a047; " +
                          "-fx-text-fill: white; " +
                          "-fx-font-weight: bold; " +
                          "-fx-background-radius: 8; " +
                          "-fx-cursor: hand;");
        
        Label resultLabel = new Label("");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        resultLabel.setTextFill(Color.web("#43a047"));
        
        addButton.setOnAction(e -> {
            try {
                int value = Integer.parseInt(input.getText());
                if (value > 0) {
                    calculator.addVegetarianMeal(value);
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
    
    private VBox createInfoBox() {
        VBox infoBox = new VBox(10);
        infoBox.setPadding(new Insets(20));
        infoBox.setStyle("-fx-background-color: #ffebee; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #e53935; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 15;");
        
        Label infoTitle = new Label("ℹ️ Emission Factors");
        infoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        infoTitle.setTextFill(Color.web("#c62828"));
        
        Label info1 = new Label("• Beef: 27.0 kg CO₂ per kg (highest impact!)");
        Label info2 = new Label("• Pork: 12.1 kg CO₂ per kg");
        Label info3 = new Label("• Chicken: 6.9 kg CO₂ per kg");
        Label info4 = new Label("• Vegetarian meal: 2.0 kg CO₂ per meal");
        Label tip = new Label("\n💡 Tip: Reducing meat consumption is one of the most effective ways to lower your carbon footprint!");
        tip.setStyle("-fx-font-style: italic;");
        tip.setWrapText(true);
        
        for (Label info : new Label[]{info1, info2, info3, info4, tip}) {
            info.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
            info.setTextFill(Color.web("#b71c1c"));
        }
        
        infoBox.getChildren().addAll(infoTitle, info1, info2, info3, info4, tip);
        return infoBox;
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public VBox getPane() {
        return pane;
    }
    
    @FunctionalInterface
    interface EmissionConsumer {
        void consume(double value);
    }
}
