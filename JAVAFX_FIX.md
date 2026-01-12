# JavaFX Runtime Components Error - Solutions

## Error Message
```
Error: JavaFX runtime components are missing, and are required to run this application
```

## Root Cause

JavaFX is a modular framework (since Java 9) and requires:
1. Proper module path configuration
2. JavaFX SDK on your system or downloaded via Maven
3. Correct JVM arguments to load JavaFX modules
4. IDE configuration for JavaFX support

---

## Solution 1: Use Maven (Recommended) ✅

### Why This Works
Maven's JavaFX plugin handles all module configuration automatically.

### Steps

```bash
# Step 1: Clean and rebuild
mvn clean install

# Step 2: Run with Maven (this is the KEY)
mvn javafx:run

# NOT: java -jar target/...
# NOT: Right-click and Run in IDE
```

### If Still Having Issues

```bash
# Force update dependencies
mvn clean install -U

# Verify JavaFX is downloaded
mvn dependency:tree | grep javafx

# Should show:
# +- org.openjfx:javafx-controls:jar:22.0.1:compile
# +- org.openjfx:javafx-fxml:jar:22.0.1:compile
# +- org.openjfx:javafx-graphics:jar:22.0.1:compile
```

---

## Solution 2: Configure Your IDE

### IntelliJ IDEA

**Step 1: Ensure JavaFX is downloaded**
```bash
mvn clean install
```

**Step 2: Configure Run Configuration**
- Go to: Run → Edit Configurations
- Click "Add new..." → "Application"
- Set Main class: `com.buscompany.BusBookingApplication`
- Set Working directory: `$ProjectFileDir$`
- In "VM options", add:
```
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
```

**Step 3: Find JavaFX SDK Path**

Maven downloads JavaFX to:
```
~/.m2/repository/org/openjfx/javafx-graphics/22.0.1/
```

Or use the full module path in VM options:
```
--module-path $HOME/.m2/repository/org/openjfx/javafx-graphics/22.0.1/:/path/to/other/javafx/jars --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.swing
```

**Step 4: Run**
- Click Run button
- Or press Shift+F10

### Eclipse

**Step 1: Install JavaFX in Eclipse**
- Help → Eclipse Marketplace
- Search: "JavaFX"
- Install: "e(fx)clipse"

**Step 2: Configure Project**
- Right-click project → Properties
- Java Build Path → Libraries
- Add External JARs from: `~/.m2/repository/org/openjfx/`

**Step 3: Configure Run Configuration**
- Run → Run Configurations
- Arguments → VM arguments:
```
--module-path $HOME/.m2/repository/org/openjfx/javafx-graphics/22.0.1/ --add-modules javafx.controls,javafx.fxml
```

**Step 4: Run**
- Click Run button

### VS Code

**Step 1: Install Extension Pack for Java**
- Go to Extensions (Ctrl+Shift+X)
- Install: "Extension Pack for Java"

**Step 2: Create launch configuration**
- Create `.vscode/launch.json`:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Bus Booking System",
            "request": "launch",
            "mainClass": "com.buscompany.BusBookingApplication",
            "cwd": "${workspaceFolder}",
            "vmArgs": "--module-path $HOME/.m2/repository/org/openjfx/javafx-graphics/22.0.1/ --add-modules javafx.controls,javafx.fxml"
        }
    ]
}
```

**Step 3: Run**
- Press F5 or click Run button

---

## Solution 3: Direct Java Command

If you need to run without Maven:

### Windows

```bash
# Build first
mvn clean package

# Find your JavaFX location
echo %USERPROFILE%\.m2\repository\org\openjfx\

# Run with full module path
java --module-path "%USERPROFILE%\.m2\repository\org\openjfx\javafx-graphics\22.0.1" ^^
     --add-modules javafx.controls,javafx.fxml ^^
     -cp "target\bus-booking-system-1.0.0.jar" ^^
     com.buscompany.BusBookingApplication
```

### macOS / Linux

```bash
# Build first
mvn clean package

# Run with full module path
java --module-path ~/.m2/repository/org/openjfx/javafx-graphics/22.0.1/ \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/bus-booking-system-1.0.0.jar \
     com.buscompany.BusBookingApplication
```

---

## Solution 4: Fix Platform Detection

Sometimes Maven can't detect your OS correctly. Edit `pom.xml`:

```xml
<properties>
    <!-- Windows: win, macOS: mac, Linux: linux -->
    <javafx.platform>win</javafx.platform>
</properties>
```

**Auto-detect your platform:**

```bash
# Check your OS
uname -s  # macOS/Linux
# or
systeminfo | findstr /B /C:"OS"  # Windows
```

| OS | Setting |
|----|----------|
| Windows | `<javafx.platform>win</javafx.platform>` |
| macOS (Intel) | `<javafx.platform>mac</javafx.platform>` |
| macOS (M1/M2/M3) | `<javafx.platform>mac</javafx.platform>` |
| Linux (64-bit) | `<javafx.platform>linux</javafx.platform>` |

---

## Solution 5: Quick Verification Checklist

### Check 1: Maven pom.xml
```bash
# Verify pom.xml has these plugins:
grep -A 2 "javafx-maven-plugin" pom.xml

# Should show:
# <groupId>org.openjfx</groupId>
# <artifactId>javafx-maven-plugin</artifactId>
```

### Check 2: Dependencies Downloaded
```bash
mvn dependency:resolve

# Verify all JavaFX dependencies are present:
ls -la ~/.m2/repository/org/openjfx/

# Should show:
# javafx-controls
# javafx-fxml  
# javafx-graphics
# javafx-swing (optional)
```

### Check 3: Java Version
```bash
java -version

# Must be Java 11 or higher (17+ recommended)
# Output should show: openjdk version "17" or similar
```

### Check 4: JavaFX in Project
```bash
# Verify project structure
ls -la src/main/resources/fxml/

# Should show:
# main-scene.fxml
# client-window.fxml
```

---

## Solution 6: Complete Fresh Start

If nothing works, try a complete reset:

```bash
# Step 1: Clean everything
mvn clean
rm -rf ~/.m2/repository/org/openjfx  # Remove cached JavaFX
rm -rf target/

# Step 2: Delete IDE cache
rm -rf .idea/              # IntelliJ
rm -rf .vscode/            # VS Code
rm -rf .classpath .project # Eclipse

# Step 3: Rebuild everything
mvn clean install -U

# Step 4: Run
mvn javafx:run
```

---

## Common Mistakes (Don't Do These) ❌

### ❌ Mistake 1: Wrong Run Command
```bash
# WRONG:
java -jar target/bus-booking-system-1.0.0.jar

# RIGHT:
mvn javafx:run
```

**Why:** The JAR doesn't include JavaFX modules by default.

### ❌ Mistake 2: IDE Run Button
```
If you use IDE "Run" button and get the error:
- Don't use the IDE run button
- Use Maven instead: mvn javafx:run
```

### ❌ Mistake 3: Wrong Java Version
```bash
# Check Java version
java -version

# Must be 11+, preferably 17+
# If you have Java 8, download Java 17+
```

### ❌ Mistake 4: Missing pom.xml Configuration
Make sure your pom.xml has:
- JavaFX dependencies
- javafx-maven-plugin
- Correct compiler settings

### ❌ Mistake 5: Platform Mismatch
If you have both:
- `javafx-graphics-22.0.1-win.jar` (Windows JAR)
- But you're on macOS/Linux

Fix: Let Maven auto-detect or set platform explicitly

---

## Advanced Troubleshooting

### Issue: Dependency Tree Shows Wrong Platform
```bash
mvn dependency:tree | grep javafx

# If you see:
# javafx-graphics-22.0.1-win.jar on macOS
# OR
# javafx-graphics-22.0.1-mac.jar on Windows

# Fix:
mvn clean install -DskipTests -X  # Run with debug
```

### Issue: Module Not Found: javafx.controls
```bash
# This means modules aren't on the module path
# Solution:
mvn javafx:run  # Uses Maven plugin which sets module path
```

### Issue: NoClassDefFoundError
```bash
# This means JAR is missing classes
# Solution:
mvn clean dependency:resolve
mvn javafx:run
```

---

## Verification Test

After fixing, verify the application works:

```bash
# Run the application
mvn javafx:run

# Expected:
# - Window opens with title "Bus Booking System"
# - Routes list shows all routes sorted
# - "Open Client Window" button is clickable
# - No error messages in console
```

---

## Summary of Solutions (By Preference)

| Rank | Solution | Difficulty | Time |
|------|----------|-----------|------|
| 1 | Maven: `mvn javafx:run` | Easy | 30 sec |
| 2 | IDE Configuration | Medium | 5 min |
| 3 | Direct Java Command | Hard | 10 min |
| 4 | Fresh Start | Medium | 5 min |
| 5 | Check Platform Settings | Easy | 1 min |

**Recommended:** Start with Solution 1 (Maven). If it doesn't work, try Solution 4 (Fresh Start).

---

## Still Having Issues?

**Checklist:**
1. ✅ Java 17+ installed? `java -version`
2. ✅ Maven 3.6+ installed? `mvn -version`
3. ✅ pom.xml has JavaFX plugin? Check pom.xml
4. ✅ Dependencies downloaded? `mvn dependency:resolve`
5. ✅ Using Maven to run? `mvn javafx:run`
6. ✅ Not using IDE run button?
7. ✅ Project structure correct? Check src/main/...

If all pass, run:
```bash
mvn clean install -U
mvn javafx:run
```

Should work! 🚀

---

**Last Updated:** January 12, 2026
**Version:** 1.0.0
