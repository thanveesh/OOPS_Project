package com.carbontracker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Core calculator for carbon emissions across different categories
 */
public class EmissionCalculator {
    
    private Map<String, Double> transportEmissions;
    private Map<String, Double> energyEmissions;
    private Map<String, Double> foodEmissions;
    
    // Emission factors (kg CO2 per unit)
    public static final double CAR_EMISSION_FACTOR = 0.21;        // per km
    public static final double BUS_EMISSION_FACTOR = 0.089;       // per km
    public static final double TRAIN_EMISSION_FACTOR = 0.041;     // per km
    public static final double FLIGHT_EMISSION_FACTOR = 0.255;    // per km
    
    public static final double ELECTRICITY_EMISSION_FACTOR = 0.92; // per kWh
    public static final double NATURAL_GAS_EMISSION_FACTOR = 2.0;  // per therm
    public static final double HEATING_OIL_EMISSION_FACTOR = 2.68; // per liter
    
    public static final double BEEF_EMISSION_FACTOR = 27.0;       // per kg
    public static final double PORK_EMISSION_FACTOR = 12.1;       // per kg
    public static final double CHICKEN_EMISSION_FACTOR = 6.9;     // per kg
    public static final double VEGETARIAN_EMISSION_FACTOR = 2.0;  // per meal
    
    public EmissionCalculator() {
        transportEmissions = new HashMap<>();
        energyEmissions = new HashMap<>();
        foodEmissions = new HashMap<>();
    }
    
    // Transport methods
    public void addCarEmission(double kilometers) {
        addEmission(transportEmissions, "car", kilometers * CAR_EMISSION_FACTOR);
    }
    
    public void addBusEmission(double kilometers) {
        addEmission(transportEmissions, "bus", kilometers * BUS_EMISSION_FACTOR);
    }
    
    public void addTrainEmission(double kilometers) {
        addEmission(transportEmissions, "train", kilometers * TRAIN_EMISSION_FACTOR);
    }
    
    public void addFlightEmission(double kilometers) {
        addEmission(transportEmissions, "flight", kilometers * FLIGHT_EMISSION_FACTOR);
    }
    
    // Energy methods
    public void addElectricityEmission(double kWh) {
        addEmission(energyEmissions, "electricity", kWh * ELECTRICITY_EMISSION_FACTOR);
    }
    
    public void addNaturalGasEmission(double therms) {
        addEmission(energyEmissions, "naturalGas", therms * NATURAL_GAS_EMISSION_FACTOR);
    }
    
    public void addHeatingOilEmission(double liters) {
        addEmission(energyEmissions, "heatingOil", liters * HEATING_OIL_EMISSION_FACTOR);
    }
    
    // Food methods
    public void addBeefEmission(double kg) {
        addEmission(foodEmissions, "beef", kg * BEEF_EMISSION_FACTOR);
    }
    
    public void addPorkEmission(double kg) {
        addEmission(foodEmissions, "pork", kg * PORK_EMISSION_FACTOR);
    }
    
    public void addChickenEmission(double kg) {
        addEmission(foodEmissions, "chicken", kg * CHICKEN_EMISSION_FACTOR);
    }
    
    public void addVegetarianMeal(int meals) {
        addEmission(foodEmissions, "vegetarian", meals * VEGETARIAN_EMISSION_FACTOR);
    }
    
    private void addEmission(Map<String, Double> map, String key, double value) {
        map.put(key, map.getOrDefault(key, 0.0) + value);
    }
    
    public double getTransportTotal() {
        return transportEmissions.values().stream().mapToDouble(Double::doubleValue).sum();
    }
    
    public double getEnergyTotal() {
        return energyEmissions.values().stream().mapToDouble(Double::doubleValue).sum();
    }
    
    public double getFoodTotal() {
        return foodEmissions.values().stream().mapToDouble(Double::doubleValue).sum();
    }
    
    public double getTotalEmissions() {
        return getTransportTotal() + getEnergyTotal() + getFoodTotal();
    }
    
    public Map<String, Double> getTransportBreakdown() {
        return new HashMap<>(transportEmissions);
    }
    
    public Map<String, Double> getEnergyBreakdown() {
        return new HashMap<>(energyEmissions);
    }
    
    public Map<String, Double> getFoodBreakdown() {
        return new HashMap<>(foodEmissions);
    }
    
    public void reset() {
        transportEmissions.clear();
        energyEmissions.clear();
        foodEmissions.clear();
    }
}
