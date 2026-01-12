# 🚘 Quick Fix - JavaFX Runtime Components Error

## The Problem
```
Error: JavaFX runtime components are missing, and are required to run this application
```

## The Solution (30 seconds)

### Step 1: Update pom.xml ✅
✅ **Already done!** (I updated it for you)

The pom.xml now has:
- Proper JavaFX plugin configuration
- Module path settings
- All required dependencies

### Step 2: Clean and Rebuild
```bash
mvn clean install
```

**This will:**
- Download all dependencies
- Compile your code
- Prepare everything for running

**Time:** ~1-2 minutes (first time)

### Step 3: Run the Application
```bash
mvn javafx:run
```

✅ **That's it!** The window should appear.

---

## If It Still Doesn't Work

### Fix 1: Force Update
```bash
mvn clean install -U
mvn javafx:run
```

The `-U` flag forces Maven to re-download all dependencies.

### Fix 2: Check Java Version
```bash
java -version
```

Must be Java 11 or higher (17+ recommended).

**If Java version is wrong:**
- Download Java 17: https://adoptium.net/
- Set JAVA_HOME environment variable
- Restart terminal

### Fix 3: Verify pom.xml
Make sure you have the **latest pom.xml**:
```bash
git pull origin main
```

### Fix 4: Clear Cache
```bash
rm -rf ~/.m2/repository/org/openjfx
mvn clean install
mvn javafx:run
```

### Fix 5: Try Direct Command

**If Maven command still fails**, try:

**Windows:**
```bash
cd target
java --module-path %USERPROFILE%\.m2\repository\org\openjfx\javafx-graphics\22.0.1 --add-modules javafx.controls,javafx.fxml -jar bus-booking-system-1.0.0.jar
```

**macOS/Linux:**
```bash
cd target
java --module-path ~/.m2/repository/org/openjfx/javafx-graphics/22.0.1 --add-modules javafx.controls,javafx.fxml -jar bus-booking-system-1.0.0.jar
```

---

## The Key Command

❌ **Don't use:**
```bash
java -jar target/bus-booking-system-1.0.0.jar    # ✗ Wrong
right-click Run in IDE                             # ✗ Wrong
```

✅ **Always use:**
```bash
mvn javafx:run    # ✓ Correct
```

---

## Why This Happens

**JavaFX is modular** (Java 9+). It needs:
1. Modules on the module path
2. JVM arguments to load them
3. Proper Maven plugin configuration

**Maven's JavaFX plugin** handles all of this automatically. That's why `mvn javafx:run` works.

---

## Verification

After running `mvn javafx:run`, you should see:

```
[INFO] Running Bus Booking System...
```

Then a window opens with:
- "Bus Booking System" title
- List of routes
- "Open Client Window" button

If you see this ✅ **Success!**

---

## Most Common Fix

**99% of the time, this works:**

```bash
cd /path/to/exam-trial
mvn clean install
mvn javafx:run
```

If that doesn't work, see "If It Still Doesn't Work" section above.

---

## Need More Help?

Read the full guide: **JAVAFX_FIX.md** (this repository)

It has:
- IDE-specific instructions
- Advanced troubleshooting
- Platform-specific fixes
- All possible solutions

---

**Created:** January 12, 2026  
**Version:** 1.0.0  
**Status:** ✅ Ready to Use
