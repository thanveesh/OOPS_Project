package com.carbontracker.ui;

import com.carbontracker.model.EmissionCalculator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Dashboard pane showing emission overview and statistics
 */
public class DashboardPane {
    
    private ScrollPane scrollPane;
    private EmissionCalculator calculator;
    
    public DashboardPane(EmissionCalculator calculator) {
        this.calculator = calculator;
        createPane();
    }
    
    private void createPane() {
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(35));
        mainContent.setAlignment(Pos.TOP_CENTER);
        
        Label title = new Label("📊 Emissions Dashboard");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#f1f5f9"));
        
        // Statistics cards
        HBox statsBox = createStatsCards();
        
        // Two-column layout: Chart on left, Tips on right
        HBox contentBox = new HBox(30);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPadding(new Insets(10, 0, 10, 0));
        
        // Left: Larger Pie Chart
        VBox chartBox = createChartSection();
        
        // Right: Reduction Tips Column
        VBox tipsColumn = createTipsColumn();
        
        contentBox.getChildren().addAll(chartBox, tipsColumn);
        
        mainContent.getChildren().addAll(title, statsBox, contentBox);
        
        // Wrap in ScrollPane
        scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; " +
                           "-fx-background: transparent;");
        scrollPane.setPannable(true);
    }
    
    private HBox createStatsCards() {
        HBox statsBox = new HBox(25);
        statsBox.setAlignment(Pos.CENTER);
        
        VBox transportCard = createStatCard("🚗 Transport", 
            calculator.getTransportTotal(), "#10b981");
        VBox energyCard = createStatCard("⚡ Energy", 
            calculator.getEnergyTotal(), "#f59e0b");
        VBox totalCard = createStatCard("🌍 Total Carbon", 
            calculator.getTotalEmissions(), "#3b82f6");
        
        statsBox.getChildren().addAll(transportCard, energyCard, totalCard);
        return statsBox;
    }
    
    private VBox createStatCard(String title, double value, String color) {
        VBox card = new VBox(12);
        card.setPrefSize(260, 150);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #1e293b; " +
                     "-fx-background-radius: 15; " +
                     "-fx-border-color: " + color + "; " +
                     "-fx-border-width: 2; " +
                     "-fx-border-radius: 15; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
        card.setPadding(new Insets(25));
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web(color));
        
        Label valueLabel = new Label(String.format("%.2f", value));
        valueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        valueLabel.setTextFill(Color.web("#f1f5f9"));
        
        Label unitLabel = new Label("kg CO₂");
        unitLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
        unitLabel.setTextFill(Color.web("#94a3b8"));
        
        card.getChildren().addAll(titleLabel, valueLabel, unitLabel);
        return card;
    }
    
    private VBox createChartSection() {
        VBox chartBox = new VBox(15);
        chartBox.setAlignment(Pos.TOP_CENTER);
        chartBox.setPrefWidth(500);
        
        Label chartTitle = new Label("📈 Emission Distribution");
        chartTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        chartTitle.setTextFill(Color.web("#f1f5f9"));
        
        PieChart pieChart = createPieChart();
        
        chartBox.getChildren().addAll(chartTitle, pieChart);
        return chartBox;
    }
    
    private PieChart createPieChart() {
        PieChart chart = new PieChart();
        
        // Get individual breakdowns
        var transportBreakdown = calculator.getTransportBreakdown();
        var energyBreakdown = calculator.getEnergyBreakdown();
        
        // Add transport slices with individual colors
        if (transportBreakdown.containsKey("car") && transportBreakdown.get("car") > 0) {
            PieChart.Data carSlice = new PieChart.Data(
                "🚗 Car\n" + String.format("%.2f kg", transportBreakdown.get("car")), 
                transportBreakdown.get("car"));
            chart.getData().add(carSlice);
        }
        
        if (transportBreakdown.containsKey("bus") && transportBreakdown.get("bus") > 0) {
            PieChart.Data busSlice = new PieChart.Data(
                "🚌 Bus\n" + String.format("%.2f kg", transportBreakdown.get("bus")), 
                transportBreakdown.get("bus"));
            chart.getData().add(busSlice);
        }
        
        if (transportBreakdown.containsKey("train") && transportBreakdown.get("train") > 0) {
            PieChart.Data trainSlice = new PieChart.Data(
                "🚆 Train\n" + String.format("%.2f kg", transportBreakdown.get("train")), 
                transportBreakdown.get("train"));
            chart.getData().add(trainSlice);
        }
        
        if (transportBreakdown.containsKey("flight") && transportBreakdown.get("flight") > 0) {
            PieChart.Data flightSlice = new PieChart.Data(
                "✈️ Flight\n" + String.format("%.2f kg", transportBreakdown.get("flight")), 
                transportBreakdown.get("flight"));
            chart.getData().add(flightSlice);
        }
        
        // Add energy slices
        if (energyBreakdown.containsKey("electricity") && energyBreakdown.get("electricity") > 0) {
            PieChart.Data electricitySlice = new PieChart.Data(
                "💡 Electricity\n" + String.format("%.2f kg", energyBreakdown.get("electricity")), 
                energyBreakdown.get("electricity"));
            chart.getData().add(electricitySlice);
        }
        
        if (energyBreakdown.containsKey("naturalGas") && energyBreakdown.get("naturalGas") > 0) {
            PieChart.Data gasSlice = new PieChart.Data(
                "🔥 Natural Gas\n" + String.format("%.2f kg", energyBreakdown.get("naturalGas")), 
                energyBreakdown.get("naturalGas"));
            chart.getData().add(gasSlice);
        }
        
        // If no data, show placeholder
        if (chart.getData().isEmpty()) {
            PieChart.Data placeholderSlice = new PieChart.Data("No data yet", 1);
            chart.getData().add(placeholderSlice);
        }
        
        chart.setTitle("Carbon Footprint Breakdown");
        chart.setLegendVisible(true);
        chart.setPrefSize(480, 480);
        chart.setLabelsVisible(true);
        chart.setStartAngle(90);
        
        chart.setStyle("-fx-background-color: #1e293b; " +
                      "-fx-background-radius: 15; " +
                      "-fx-border-color: #334155; " +
                      "-fx-border-width: 1.5; " +
                      "-fx-border-radius: 15; " +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5); " +
                      "-fx-padding: 25;");
        
        // Apply custom colors to each slice
        chart.applyCss();
        chart.layout();
        
        int colorIndex = 0;
        String[] colors = {"#ef4444", "#10b981", "#3b82f6", "#8b5cf6", "#f59e0b", "#ec4899"};
        
        for (PieChart.Data data : chart.getData()) {
            if (colorIndex < colors.length) {
                data.getNode().setStyle("-fx-pie-color: " + colors[colorIndex] + ";");
                colorIndex++;
            }
        }
        
        return chart;
    }
    
    private VBox createTipsColumn() {
        VBox tipsColumn = new VBox(20);
        tipsColumn.setPrefWidth(450);
        tipsColumn.setMaxWidth(450);
        tipsColumn.setAlignment(Pos.TOP_LEFT);
        
        Label columnTitle = new Label("💡 How to Reduce Emissions");
        columnTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        columnTitle.setTextFill(Color.web("#10b981"));
        
        VBox tipsContent = createPersonalizedTips();
        
        tipsColumn.getChildren().addAll(columnTitle, tipsContent);
        return tipsColumn;
    }
    
    private VBox createPersonalizedTips() {
        VBox tipsBox = new VBox(15);
        tipsBox.setPadding(new Insets(25));
        tipsBox.setStyle("-fx-background-color: #1e293b; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #10b981; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 15; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
        
        VBox tipsContent = new VBox(12);
        
        // Analyze user's emissions and give personalized tips
        var transportBreakdown = calculator.getTransportBreakdown();
        var energyBreakdown = calculator.getEnergyBreakdown();
        double total = calculator.getTotalEmissions();
        
        if (total == 0) {
            Label welcomeTip = createTipLabel("🌟 Start tracking your emissions to get personalized reduction strategies!");
            tipsContent.getChildren().add(welcomeTip);
        } else {
            // Show impact summary
            Label impactLabel = createHighlightLabel("📊 Your Emission Breakdown");
            tipsContent.getChildren().add(impactLabel);
            
            // Show transport breakdown
            if (!transportBreakdown.isEmpty()) {
                Label transportHeader = createHighlightLabel("🚗 Transport:");
                tipsContent.getChildren().add(transportHeader);
                
                if (transportBreakdown.containsKey("car") && transportBreakdown.get("car") > 0) {
                    double carPct = (transportBreakdown.get("car")/total)*100;
                    Label carTip = createTipLabel("  🚗 Car: " + String.format("%.2f kg (%.1f%%)", transportBreakdown.get("car"), carPct));
                    tipsContent.getChildren().add(carTip);
                }
                
                if (transportBreakdown.containsKey("bus") && transportBreakdown.get("bus") > 0) {
                    double busPct = (transportBreakdown.get("bus")/total)*100;
                    Label busTip = createTipLabel("  🚌 Bus: " + String.format("%.2f kg (%.1f%%)", transportBreakdown.get("bus"), busPct));
                    tipsContent.getChildren().add(busTip);
                }
                
                if (transportBreakdown.containsKey("train") && transportBreakdown.get("train") > 0) {
                    double trainPct = (transportBreakdown.get("train")/total)*100;
                    Label trainTip = createTipLabel("  🚆 Train: " + String.format("%.2f kg (%.1f%%)", transportBreakdown.get("train"), trainPct));
                    tipsContent.getChildren().add(trainTip);
                }
                
                if (transportBreakdown.containsKey("flight") && transportBreakdown.get("flight") > 0) {
                    double flightPct = (transportBreakdown.get("flight")/total)*100;
                    Label flightTip = createTipLabel("  ✈️ Flight: " + String.format("%.2f kg (%.1f%%)", transportBreakdown.get("flight"), flightPct));
                    tipsContent.getChildren().add(flightTip);
                }
            }
            
            // Show energy breakdown
            if (!energyBreakdown.isEmpty()) {
                Label energyHeader = createHighlightLabel("⚡ Energy:");
                tipsContent.getChildren().add(energyHeader);
                
                if (energyBreakdown.containsKey("electricity") && energyBreakdown.get("electricity") > 0) {
                    double elecPct = (energyBreakdown.get("electricity")/total)*100;
                    Label elecTip = createTipLabel("  💡 Electricity: " + String.format("%.2f kg (%.1f%%)", energyBreakdown.get("electricity"), elecPct));
                    tipsContent.getChildren().add(elecTip);
                }
                
                if (energyBreakdown.containsKey("naturalGas") && energyBreakdown.get("naturalGas") > 0) {
                    double gasPct = (energyBreakdown.get("naturalGas")/total)*100;
                    Label gasTip = createTipLabel("  🔥 Natural Gas: " + String.format("%.2f kg (%.1f%%)", energyBreakdown.get("naturalGas"), gasPct));
                    tipsContent.getChildren().add(gasTip);
                }
            }
            
            // Add separator
            Separator sep = new Separator();
            sep.setStyle("-fx-padding: 10 0 10 0;");
            tipsContent.getChildren().add(sep);
            
            // Quick reduction tips
            Label quickWins = createHighlightLabel("⚡ Quick Reduction Tips:");
            Label qw1 = createTipLabel("• Replace car trips with bus → Save 58% per km");
            Label qw2 = createTipLabel("• Use train instead of car → Save 80% per km");
            Label qw3 = createTipLabel("• Switch to LED bulbs → Save 75% on lighting");
            Label qw4 = createTipLabel("• Carpool to work → Cut commute emissions by 50%");
            Label qw5 = createTipLabel("• Bike/walk for trips under 5km → 100% reduction");
            tipsContent.getChildren().addAll(quickWins, qw1, qw2, qw3, qw4, qw5);
            
            // Footprint status
            if (total > 500) {
                Label highTip = createWarningLabel("⚠️ Your footprint is high - small changes = big impact!");
                tipsContent.getChildren().add(highTip);
            } else if (total > 0 && total <= 100) {
                Label goodTip = createSuccessLabel("✅ Excellent! You're below average - keep it up!");
                tipsContent.getChildren().add(goodTip);
            }
        }
        
        tipsBox.getChildren().addAll(tipsContent);
        return tipsBox;
    }
    
    private Label createTipLabel(String text) {
        Label tip = new Label(text);
        tip.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        tip.setTextFill(Color.web("#cbd5e1"));
        tip.setWrapText(true);
        tip.setMaxWidth(400);
        tip.setStyle("-fx-padding: 3 0 3 0;");
        return tip;
    }
    
    private Label createHighlightLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#10b981"));
        label.setWrapText(true);
        label.setMaxWidth(400);
        label.setStyle("-fx-padding: 8 0 5 0;");
        return label;
    }
    
    private Label createWarningLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#f59e0b"));
        label.setWrapText(true);
        label.setMaxWidth(400);
        label.setStyle("-fx-padding: 5 0 5 0;");
        return label;
    }
    
    private Label createSuccessLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#10b981"));
        label.setWrapText(true);
        label.setMaxWidth(400);
        label.setStyle("-fx-padding: 5 0 5 0;");
        return label;
    }
    
    public void refresh() {
        createPane();
    }
    
    public ScrollPane getPane() {
        return scrollPane;
    }
}
