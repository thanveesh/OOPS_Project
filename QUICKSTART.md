# 🌍 Carbon Emission Tracker - Quick Start Guide

## ⚠️ Maven Not Installed? No Problem!

You have several options to run this application:

---

## Option 1: Install Maven (Recommended)

### Download and Install Maven:

1. **Download Maven**:
   - Visit: https://maven.apache.org/download.cgi
   - Download the Binary zip archive (e.g., `apache-maven-3.9.5-bin.zip`)

2. **Extract the Archive**:
   - Extract to a location like `C:\Program Files\Apache\maven`

3. **Set Environment Variables**:
   - Add to System PATH:
     - Right-click "This PC" → Properties → Advanced System Settings
     - Click "Environment Variables"
     - Under "System Variables", find "Path" and click "Edit"
     - Add: `C:\Program Files\Apache\maven\bin`
   - Create `MAVEN_HOME` variable:
     - Click "New" under System Variables
     - Variable name: `MAVEN_HOME`
     - Variable value: `C:\Program Files\Apache\maven`

4. **Verify Installation**:
   ```powershell
   mvn -version
   ```

5. **Run the Application**:
   ```powershell
   cd "C:\Users\ADMIN\Desktop\code"
   mvn clean javafx:run
   ```

---

## Option 2: Use IntelliJ IDEA (Easy Setup)

1. **Download IntelliJ IDEA Community Edition** (Free):
   - Visit: https://www.jetbrains.com/idea/download/
   
2. **Open the Project**:
   - Launch IntelliJ IDEA
   - Click "Open" and select the `code` folder
   - IntelliJ will automatically detect the Maven project and download dependencies

3. **Run the Application**:
   - Wait for IntelliJ to index and download dependencies
   - Right-click on `CarbonTrackerApp.java`
   - Select "Run 'CarbonTrackerApp.main()'"

---

## Option 3: Use VS Code with Java Extensions

1. **Install Extensions**:
   - Extension Pack for Java (Microsoft)
   - Maven for Java

2. **Open the Project**:
   - Open the `code` folder in VS Code
   - VS Code will detect Maven and prompt to install it

3. **Run**:
   - Press F5 or use the Run button

---

## Option 4: Manual Compilation (Advanced)

If you have Java 17+ installed but no Maven:

### 1. Download JavaFX SDK:
   - Visit: https://gluonhq.com/products/javafx/
   - Download JavaFX SDK 21 for Windows
   - Extract to `C:\javafx-sdk-21`

### 2. Compile manually:
```powershell
# Set JavaFX path
$env:PATH_TO_FX="C:\javafx-sdk-21\lib"

# Create output directory
New-Item -ItemType Directory -Force -Path "target\classes"

# Copy resources
Copy-Item "src\main\resources\*" -Destination "target\classes\" -Recurse

# Compile all Java files
javac --module-path $env:PATH_TO_FX --add-modules javafx.controls,javafx.fxml `
  -d target\classes `
  -sourcepath src\main\java `
  src\main\java\com\carbontracker\*.java `
  src\main\java\com\carbontracker\model\*.java `
  src\main\java\com\carbontracker\ui\*.java

# Run the application
java --module-path $env:PATH_TO_FX --add-modules javafx.controls,javafx.fxml `
  -cp target\classes `
  com.carbontracker.CarbonTrackerApp
```

---

## ✅ Recommended Quick Start

**For beginners**, I recommend **Option 2 (IntelliJ IDEA)**:
- ✅ Easiest setup
- ✅ Automatic dependency management
- ✅ Great debugging tools
- ✅ Free Community Edition

**For developers**, I recommend **Option 1 (Maven)**:
- ✅ Industry standard
- ✅ Command-line control
- ✅ Useful for many Java projects

---

## 📋 Prerequisites

Before running the application, ensure you have:
- ✅ **Java 17 or higher** installed
- Check with: `java -version`
- If not installed, download from: https://adoptium.net/

---

## 🎨 What to Expect

Once running, you'll see:
- 🌈 **Colorful gradient header** (blue to green)
- 📊 **Interactive dashboard** with emission statistics
- 🔘 **Color-coded navigation buttons**:
  - 🔵 Blue: Dashboard
  - 🟢 Green: Transport
  - 🟠 Orange: Energy
  - 🔴 Red: Food
- 📈 **Live updating bar charts**
- 💡 **Helpful eco-tips**

---

## 🆘 Troubleshooting

### Java Not Found?
```powershell
java -version
```
If this fails, download Java 17+ from https://adoptium.net/

### Port Already in Use?
The JavaFX app doesn't use ports, but if you have issues:
- Close other Java applications
- Restart your computer

### JavaFX Module Errors?
- Ensure you're using Java 17+
- Use Maven or IntelliJ (they handle JavaFX automatically)

---

## 📞 Need Help?

The project is ready to run! Just choose one of the options above based on what you have installed.

**Fastest path**: Download IntelliJ IDEA Community (free) → Open project → Click Run! 🚀
