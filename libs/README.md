# libs/libtraci-1.25.0.jar

This file is required for SUMO integration and should be committed to the repository.

## How to obtain this file

If the file is missing after cloning:

### Option 1: From SUMO Installation

**Linux:**
```bash
# Find the file
find /usr -name "libtraci*.jar" 2>/dev/null

# Copy to project
cp /path/to/libtraci-1.25.0.jar libs/
```

**Windows:**
```cmd
REM Find the file
dir "C:\Program Files (x86)\Eclipse\Sumo" /s /b | findstr libtraci

REM Copy to project
copy "C:\Program Files (x86)\Eclipse\Sumo\bin\libtraci-1.25.0.jar" libs\
```

**macOS:**
```bash
find /opt/homebrew -name "libtraci*.jar" 2>/dev/null
cp /path/to/libtraci-1.25.0.jar libs/
```

### Option 2: Download from SUMO

1. Visit: https://sumo.dlr.de/releases/1.25.0/
2. Download appropriate package for your OS
3. Extract and find `libtraci-1.25.0.jar`
4. Copy to `libs/` folder

### Option 3: Use Git LFS (if configured)

```bash
git lfs pull
```

## File Information

- **Version:** 1.25.0
- **Purpose:** Java bindings for SUMO TraCI (Traffic Control Interface)
- **Size:** ~5-10 MB
- **Required:** Yes, application will not run without it

## Verification

Check if file exists:
```bash
ls -lh libs/libtraci-1.25.0.jar
```

Should show file size around 5-10 MB.
