# 🌍 Carbon Emission Tracker

A beautiful and intuitive JavaFX application to track and monitor your carbon footprint across transport, energy, and food consumption.

## Features

✨ **Colorful Modern Interface** - Vibrant gradient colors and smooth animations  
📊 **Interactive Dashboard** - Visual charts showing emission breakdowns  
🚗 **Transport Tracking** - Monitor emissions from cars, buses, trains, and flights  
⚡ **Energy Monitoring** - Track electricity, natural gas, and heating oil consumption  
🍽️ **Food Footprint** - Calculate emissions from different dietary choices  
💡 **Helpful Tips** - Get suggestions to reduce your carbon footprint  
🔄 **Reset Functionality** - Start fresh anytime with data reset  

## Screenshot Description

The application features:
- **Header**: Gradient blue-to-green header with live total emissions display
- **Navigation**: Left sidebar with color-coded category buttons
- **Dashboard**: Statistics cards and bar chart visualization
- **Input Forms**: Easy-to-use forms for each emission category
- **Info Boxes**: Educational content about emission factors

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+** for building the project
- **JavaFX 21** (included in dependencies)

## Project Structure

```
carbon-emission-tracker/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── carbontracker/
│   │   │           ├── CarbonTrackerApp.java          # Main application
│   │   │           ├── model/
│   │   │           │   └── EmissionCalculator.java    # Core calculation logic
│   │   │           └── ui/
│   │   │               ├── DashboardPane.java         # Dashboard view
│   │   │               ├── TransportPane.java         # Transport input
│   │   │               ├── EnergyPane.java           # Energy input
│   │   │               └── FoodPane.java             # Food input
│   │   └── resources/
│   │       └── styles.css                            # Application styling
```

## Building the Project

### Using Maven

1. **Navigate to the project directory**:
   ```bash
   cd code
   ```

2. **Clean and compile the project**:
   ```bash
   mvn clean compile
   ```

3. **Run the application**:
   ```bash
   mvn javafx:run
   ```

### Alternative: Package as JAR

```bash
mvn clean package
java -jar target/carbon-emission-tracker-1.0.0.jar
```

## Usage Guide

### 1. Dashboard
- View your total emissions and breakdown by category
- See color-coded statistics cards
- Interactive bar chart visualization
- Read tips to reduce your footprint

### 2. Transport Emissions
- **Car**: Enter kilometers driven
- **Bus**: Public bus travel distance
- **Train**: Rail travel distance
- **Flight**: Air travel distance

Emission factors:
- Car: 0.21 kg CO₂/km
- Bus: 0.089 kg CO₂/km
- Train: 0.041 kg CO₂/km
- Flight: 0.255 kg CO₂/km

### 3. Energy Emissions
- **Electricity**: Enter kWh consumed
- **Natural Gas**: Enter therms used
- **Heating Oil**: Enter liters consumed

Emission factors:
- Electricity: 0.92 kg CO₂/kWh
- Natural Gas: 2.0 kg CO₂/therm
- Heating Oil: 2.68 kg CO₂/liter

### 4. Food Emissions
- **Beef**: Enter kg consumed
- **Pork**: Enter kg consumed
- **Chicken**: Enter kg consumed
- **Vegetarian Meals**: Enter number of meals

Emission factors:
- Beef: 27.0 kg CO₂/kg (highest impact!)
- Pork: 12.1 kg CO₂/kg
- Chicken: 6.9 kg CO₂/kg
- Vegetarian: 2.0 kg CO₂/meal

## Color Scheme

The application uses a vibrant, eco-friendly color palette:

- **Primary Blue**: #1e88e5 (Trust, stability)
- **Green**: #43a047 (Eco-friendly, nature)
- **Orange**: #fb8c00 (Energy, warmth)
- **Red**: #e53935 (Awareness, importance)
- **Yellow**: #ffeb3b (Highlights, warnings)

## Tips to Reduce Carbon Footprint

💡 **Transport**: Use public transportation, carpool, or bike for short distances  
💡 **Energy**: Switch to renewable energy, use LED bulbs, improve insulation  
💡 **Food**: Reduce meat consumption, choose local and seasonal produce  
💡 **General**: Reduce, reuse, recycle; minimize waste

## Technical Details

### Technologies Used
- **JavaFX 21**: Modern UI framework
- **Java 17**: Core language
- **Maven**: Build and dependency management
- **CSS**: Custom styling

### Key Classes
- `EmissionCalculator`: Core business logic for calculating emissions
- `CarbonTrackerApp`: Main JavaFX application class
- UI Panes: Modular components for different views

### Design Patterns
- **MVC Pattern**: Separation of concerns
- **Observer Pattern**: Callback mechanism for UI updates
- **Builder Pattern**: UI component construction

## Troubleshooting

### JavaFX Not Found
If you encounter JavaFX module errors, ensure you have:
- Java 17+ installed
- Maven configured properly
- JavaFX dependencies downloaded

### Module Issues
If you see module-related errors, run:
```bash
mvn clean install -U
```

## Future Enhancements

- 📱 Data persistence (save/load emission history)
- 📈 Historical tracking and trends
- 🎯 Set reduction goals and track progress
- 🌐 Compare with global/national averages
- 📊 More visualization options (pie charts, line graphs)
- 🔔 Alerts when exceeding emission targets
- 📤 Export reports as PDF

## Contributing

Feel free to fork this project and submit pull requests for improvements!

## License

This project is created for educational purposes.

## Author

Created with ❤️ for the environment 🌍

---

**Start tracking your carbon footprint today and make a difference!** 🌱
