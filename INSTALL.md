# Installation Guide for Anyone Cloning This Project

## 🚀 Quick Start (3 Steps)

### Step 1: Prerequisites

Install these 3 things first:

**1. Java 17 or higher**
```bash
# Check Java version
java -version
```
Download from: https://adoptium.net/

**2. Maven 3.6 or higher**
```bash
# Check Maven version
mvn -version
```
Download from: https://maven.apache.org/download.cgi

**3. SUMO 1.20 or higher**
```bash
# Check SUMO
sumo --version
```

**Installation:**
- **Windows:** Download from https://eclipse.dev/sumo/ (check "Add to PATH" during installation)
- **Linux/Ubuntu:**
  ```bash
  sudo add-apt-repository ppa:sumo/stable
  sudo apt-get update
  sudo apt-get install sumo sumo-tools sumo-doc
  ```
- **macOS:**
  ```bash
  brew install sumo
  ```

---

### Step 2: Clone and Setup

```bash
# Clone the repository
git clone <repository-url>
cd FUAS-Java

# Verify files exist
ls -la sumo/simple/simple.sumocfg
ls -la libs/libtraci-1.25.0.jar
```

---

### Step 3: Build and Run

**IMPORTANT:** SUMO must be in your system PATH!

```bash
# Build the project
mvn clean install

# Run the application
mvn javafx:run
```

**Expected output:**
```
✅ SUMO started successfully!
```

A window titled "Traffic Flow Simulation" will open showing:
- ✅ Road network
- ✅ Moving vehicles
- ✅ Traffic lights (changing colors)

---

## ⚠️ Troubleshooting

### Issue 1: "Could not connect to TraCI server"

**Cause:** SUMO is not installed or not in PATH

**Fix:**
```bash
# Linux/Mac
which sumo
export PATH="/usr/share/sumo/bin:$PATH"

# Windows
where sumo
# Add C:\Program Files (x86)\Eclipse\Sumo\bin to PATH
```

### Issue 2: "SUMO config not found"

**Cause:** Working directory is wrong

**Fix:**
```bash
# Make sure you're in the FUAS-Java directory
cd FUAS-Java
pwd  # Should show .../FUAS-Java
```

### Issue 3: "JavaFX runtime components are missing"

**Cause:** Running MainApp directly instead of using Maven

**Fix:** Always use `mvn javafx:run` (never run MainApp.main() directly from IDE)

### Issue 4: "UnsatisfiedLinkError: no libtracijni"

**Cause:** SUMO native libraries not found

**Fix for Linux/Mac:**
```bash
# Add SUMO to library path
export LD_LIBRARY_PATH=/usr/share/sumo/bin:$LD_LIBRARY_PATH
export SUMO_HOME=/usr/share/sumo

# Or run with library path
mvn javafx:run -Djava.library.path=/usr/share/sumo/bin
```

**Fix for Windows:**
```cmd
REM Make sure SUMO bin is in PATH
set PATH=%PATH%;C:\Program Files (x86)\Eclipse\Sumo\bin

REM Or run with library path
mvn javafx:run -Djava.library.path="C:\Program Files (x86)\Eclipse\Sumo\bin"
```

---

## 🔧 IDE Setup (Optional)

### IntelliJ IDEA

1. Open project: `File → Open → Select FUAS-Java folder`
2. Maven will auto-import dependencies
3. Run using Maven tool window:
   - `View → Tool Windows → Maven`
   - `FUAS-Java → Plugins → javafx → javafx:run`

### Eclipse

1. Import project: `File → Import → Existing Maven Projects`
2. Select FUAS-Java folder
3. Right-click project → `Run As → Maven build...`
4. Goals: `javafx:run`

### VS Code

1. Open folder: `File → Open Folder → FUAS-Java`
2. Install extensions: "Extension Pack for Java", "Maven for Java"
3. Run in terminal: `mvn javafx:run`

---

## 📋 Complete Checklist

Before asking for help, verify all these:

- [ ] Java 17+ installed: `java -version`
- [ ] Maven 3.6+ installed: `mvn -version`
- [ ] SUMO installed: `sumo --version`
- [ ] SUMO in PATH: `which sumo` (Linux/Mac) or `where sumo` (Windows)
- [ ] In correct directory: `pwd` shows `.../FUAS-Java`
- [ ] File exists: `ls sumo/simple/simple.sumocfg`
- [ ] File exists: `ls libs/libtraci-1.25.0.jar`
- [ ] Build successful: `mvn clean install` → `BUILD SUCCESS`
- [ ] Using correct command: `mvn javafx:run` (not running MainApp directly)

---

## 💡 Key Points

### ✅ DO:
- ✅ Use `mvn javafx:run` to run the app
- ✅ Make sure SUMO is in PATH
- ✅ Run from FUAS-Java root directory

### ❌ DON'T:
- ❌ Don't run MainApp.main() directly from IDE
- ❌ Don't run `java -jar target/...jar`
- ❌ Don't modify pom.xml or code (unless you know what you're doing)

---

## 🌍 Platform-Specific Notes

### Linux
- SUMO typically installs to `/usr/share/sumo` or `/usr/bin`
- Use `source` for environment setup scripts: `source setup.sh`

### Windows
- SUMO typically installs to `C:\Program Files (x86)\Eclipse\Sumo`
- Use forward slashes or double backslashes in paths
- Run scripts: `setup.bat`

### macOS
- SUMO installed via Homebrew goes to `/opt/homebrew/bin/sumo`
- May need to allow app in Security settings

---

## 📞 Getting Help

If you still have issues:

1. **Check existing documentation:**
   - `TROUBLESHOOT_SUMO.md` - SUMO connection issues
   - `COMPARISON_DETAILED.md` - Comparing with other projects
   - `QUICK_FIX.md` - Common fixes

2. **Gather information:**
   ```bash
   # Run these and save output
   java -version
   mvn -version
   sumo --version
   pwd
   ls -la sumo/simple/
   mvn javafx:run 2>&1 | tee error.log
   ```

3. **Create an issue on GitHub with:**
   - Your OS and versions (from step 2)
   - Full error log
   - What you've already tried

---

## 🎯 Success Criteria

You'll know it's working when:

1. ✅ No errors in console
2. ✅ Window opens with title "Traffic Flow Simulation"
3. ✅ Map is visible with roads
4. ✅ Vehicles are moving
5. ✅ Traffic lights change colors (red → yellow → green)

---

## 📝 Notes for Developers

### Project Structure
```
FUAS-Java/
├── src/main/java/novik/     # Source code
├── src/main/resources/       # FXML, CSS, images
├── sumo/simple/              # SUMO configuration
├── libs/                     # libtraci JAR
├── pom.xml                   # Maven configuration
└── README.md                 # This file
```

### Key Files
- `MainApp.java` - Application entry point
- `MainController.java` - Main controller logic
- `SumoBridge.java` - SUMO integration
- `pom.xml` - Maven dependencies and plugins

### Making Changes
- Always test with `mvn clean install` before pushing
- Run `mvn javafx:run` to verify application still works
- Don't commit absolute paths or machine-specific configs

---

**License:** [Add your license]

**Contributors:** [Add contributors]

**Last Updated:** December 11, 2025
