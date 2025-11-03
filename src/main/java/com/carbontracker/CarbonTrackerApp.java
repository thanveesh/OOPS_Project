package com.carbontracker;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.carbontracker.model.EmissionCalculator;
import com.carbontracker.ui.DashboardPane;
import com.carbontracker.ui.TransportPane;
import com.carbontracker.ui.EnergyPane;

/**
 * Main JavaFX Application for Carbon Emission Tracker
 */
public class CarbonTrackerApp extends Application {
    
    private EmissionCalculator calculator;
    private Label totalEmissionsLabel;
    private DashboardPane dashboardPane;
    private TransportPane transportPane;
    private EnergyPane energyPane;
    
    @Override
    public void start(Stage primaryStage) {
        calculator = new EmissionCalculator();
        
        primaryStage.setTitle("🌍 Carbon Footprint");
        
        // Create main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0f172a;");
        
        // Top header
        VBox header = createHeader();
        root.setTop(header);
        
        // Left navigation
        VBox navigation = createNavigation(root);
        root.setLeft(navigation);
        
        // Center content - start with dashboard
        dashboardPane = new DashboardPane(calculator);
        root.setCenter(dashboardPane.getPane());
        
        // Create scene
        Scene scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createHeader() {
        VBox header = new VBox(15);
        header.setPadding(new Insets(35, 30, 35, 30));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #0f172a; " +
                       "-fx-border-color: #10b981; " +
                       "-fx-border-width: 0 0 3 0; " +
                       "-fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.3), 20, 0, 0, 8);");
        
        Label title = new Label("🌍 CarbonSense ");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 44));
        title.setTextFill(Color.web("#10b981"));
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.6), 8, 0, 0, 3);");
        
        Label subtitle = new Label("Track, Analyze & Reduce Your Carbon Footprint");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.LIGHT, 17));
        subtitle.setTextFill(Color.web("#94a3b8"));
        
        totalEmissionsLabel = new Label("Total Emissions: 0.00 kg CO₂");
        totalEmissionsLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        totalEmissionsLabel.setTextFill(Color.web("#10b981"));
        totalEmissionsLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.5), 5, 0, 0, 2);");
        
        header.getChildren().addAll(title, subtitle, totalEmissionsLabel);
        return header;
    }
    
    private VBox createNavigation(BorderPane root) {
        VBox navigation = new VBox(22);
        navigation.setPadding(new Insets(30));
        navigation.setPrefWidth(250);
        navigation.setStyle("-fx-background-color: #1e293b; " +
                           "-fx-border-color: #334155; " +
                           "-fx-border-width: 0 1 0 0; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 5, 0);");
        
        Label navTitle = new Label("MENU");
        navTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        navTitle.setTextFill(Color.web("#10b981"));
        navTitle.setStyle("-fx-letter-spacing: 3px;");
        
        Button dashboardBtn = createNavButton("📊 Dashboard", "#10b981");
        dashboardBtn.setOnAction(e -> {
            dashboardPane.refresh();
            root.setCenter(dashboardPane.getPane());
            updateTotalEmissions();
        });
        
        Button transportBtn = createNavButton("🚗 Transport", "#64748b");
        transportBtn.setOnAction(e -> {
            if (transportPane == null) {
                transportPane = new TransportPane(calculator, this::updateTotalEmissions);
            }
            root.setCenter(transportPane.getPane());
        });
        
        Button energyBtn = createNavButton("⚡ Energy", "#64748b");
        energyBtn.setOnAction(e -> {
            if (energyPane == null) {
                energyPane = new EnergyPane(calculator, this::updateTotalEmissions);
            }
            root.setCenter(energyPane.getPane());
        });
        
        Separator separator = new Separator();
        
        Button resetBtn = createNavButton("🔄 Reset Data", "#64748b");
        resetBtn.setOnAction(e -> resetAllData(root));
        
        navigation.getChildren().addAll(navTitle, new Separator(), 
            dashboardBtn, transportBtn, energyBtn, separator, resetBtn);
        
        return navigation;
    }
    
    private Button createNavButton(String text, String color) {
        Button button = new Button(text);
        button.setPrefWidth(210);
        button.setPrefHeight(52);
        
        // Determine if this is the primary green button
        boolean isGreen = color.equals("#10b981") || color.equals("#27ae60");
        
        if (isGreen) {
            button.setStyle("-fx-background-color: #10b981; " +
                           "-fx-text-fill: #0f172a; " +
                           "-fx-font-size: 15px; " +
                           "-fx-font-family: 'Segoe UI'; " +
                           "-fx-font-weight: 700; " +
                           "-fx-background-radius: 12; " +
                           "-fx-cursor: hand; " +
                           "-fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.4), 10, 0, 0, 4);");
            
            button.setOnMouseEntered(e -> 
                button.setStyle("-fx-background-color: #059669; " +
                              "-fx-text-fill: #0f172a; " +
                              "-fx-font-size: 15px; " +
                              "-fx-font-family: 'Segoe UI'; " +
                              "-fx-font-weight: 700; " +
                              "-fx-background-radius: 12; " +
                              "-fx-cursor: hand; " +
                              "-fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.6), 15, 0, 0, 6); " +
                              "-fx-scale-x: 1.03; " +
                              "-fx-scale-y: 1.03;"));
            
            button.setOnMouseExited(e -> 
                button.setStyle("-fx-background-color: #10b981; " +
                              "-fx-text-fill: #0f172a; " +
                              "-fx-font-size: 15px; " +
                              "-fx-font-family: 'Segoe UI'; " +
                              "-fx-font-weight: 700; " +
                              "-fx-background-radius: 12; " +
                              "-fx-cursor: hand; " +
                              "-fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.4), 10, 0, 0, 4);"));
        } else {
            button.setStyle("-fx-background-color: transparent; " +
                           "-fx-text-fill: #94a3b8; " +
                           "-fx-font-size: 15px; " +
                           "-fx-font-family: 'Segoe UI'; " +
                           "-fx-font-weight: 600; " +
                           "-fx-background-radius: 12; " +
                           "-fx-border-color: #334155; " +
                           "-fx-border-width: 1.5; " +
                           "-fx-border-radius: 12; " +
                           "-fx-cursor: hand;");
            
            button.setOnMouseEntered(e -> 
                button.setStyle("-fx-background-color: #334155; " +
                              "-fx-text-fill: #f1f5f9; " +
                              "-fx-font-size: 15px; " +
                              "-fx-font-family: 'Segoe UI'; " +
                              "-fx-font-weight: 600; " +
                              "-fx-background-radius: 12; " +
                              "-fx-border-color: #10b981; " +
                              "-fx-border-width: 1.5; " +
                              "-fx-border-radius: 12; " +
                              "-fx-cursor: hand; " +
                              "-fx-scale-x: 1.03; " +
                              "-fx-scale-y: 1.03;"));
            
            button.setOnMouseExited(e -> 
                button.setStyle("-fx-background-color: transparent; " +
                              "-fx-text-fill: #94a3b8; " +
                              "-fx-font-size: 15px; " +
                              "-fx-font-family: 'Segoe UI'; " +
                              "-fx-font-weight: 600; " +
                              "-fx-background-radius: 12; " +
                              "-fx-border-color: #334155; " +
                              "-fx-border-width: 1.5; " +
                              "-fx-border-radius: 12; " +
                              "-fx-cursor: hand;"));
        }
        
        return button;
    }
    
    private void updateTotalEmissions() {
        double total = calculator.getTotalEmissions();
        totalEmissionsLabel.setText(String.format("Total Emissions: %.2f kg CO₂", total));
        
        // Update color - always keep green theme
        totalEmissionsLabel.setTextFill(Color.web("#10b981"));
    }
    
    private void resetAllData(BorderPane root) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reset Confirmation");
        alert.setHeaderText("Reset All Data");
        alert.setContentText("Are you sure you want to reset all emission data?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                calculator.reset();
                transportPane = null;
                energyPane = null;
                dashboardPane = new DashboardPane(calculator);
                root.setCenter(dashboardPane.getPane());
                updateTotalEmissions();
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
