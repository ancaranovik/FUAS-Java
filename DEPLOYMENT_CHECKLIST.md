# Pre-Deployment Checklist

Before sharing this project with others or deploying, verify:

## ✅ Files and Configuration

- [ ] `libs/libtraci-1.25.0.jar` exists and is committed to Git
- [ ] `sumo/simple/simple.sumocfg` and related SUMO files exist
- [ ] `.gitignore` properly excludes build artifacts but includes `libs/`
- [ ] `README.md` is up-to-date
- [ ] `INSTALL.md` has complete installation instructions
- [ ] No absolute paths in code (e.g., `C:\Users\...`, `/home/username/...`)
- [ ] No hardcoded machine-specific configurations

## ✅ Dependencies

- [ ] `pom.xml` has all required dependencies
- [ ] Java version is set to 17 (not 25 or other uncommon versions)
- [ ] Maven plugins are properly configured
- [ ] No `<options>` with hardcoded library paths in `pom.xml`

## ✅ Code Quality

- [ ] No debug `System.out.println()` statements (or properly commented)
- [ ] No commented-out code blocks (clean them up)
- [ ] Proper error handling in `SumoBridge.java`
- [ ] All import statements are used
- [ ] No compiler warnings

## ✅ Documentation

- [ ] README explains what the project does
- [ ] INSTALL.md has step-by-step instructions
- [ ] Troubleshooting guides are available
- [ ] Comments in code are clear and helpful
- [ ] License file exists (if required)

## ✅ Testing

Test on different platforms:

### Windows Testing
- [ ] Clone fresh copy to new directory
- [ ] Install prerequisites (Java, Maven, SUMO)
- [ ] Run `mvn clean install` → SUCCESS
- [ ] Run `mvn javafx:run` → Application opens and works
- [ ] No hardcoded Windows paths in logs

### Linux Testing
- [ ] Clone fresh copy
- [ ] Install prerequisites
- [ ] Run `mvn clean install` → SUCCESS
- [ ] Run `mvn javafx:run` → Application opens and works
- [ ] No hardcoded Linux paths in logs

### macOS Testing (if possible)
- [ ] Clone fresh copy
- [ ] Install prerequisites
- [ ] Run `mvn clean install` → SUCCESS
- [ ] Run `mvn javafx:run` → Application opens and works

## ✅ Git Repository

- [ ] All necessary files are committed
- [ ] Large files (like `libtraci-1.25.0.jar`) are tracked correctly
- [ ] `.gitignore` is working properly
- [ ] No sensitive information in commits (passwords, keys, etc.)
- [ ] Commit messages are clear and descriptive
- [ ] Branch is up-to-date with main/master

## ✅ Performance

- [ ] Application starts within 10 seconds
- [ ] No memory leaks (test by running for 5+ minutes)
- [ ] Simulation runs smoothly
- [ ] No excessive console output

## ✅ User Experience

- [ ] Window title is appropriate
- [ ] UI is responsive and doesn't freeze
- [ ] Error messages are helpful (not cryptic stack traces)
- [ ] Application can be closed properly (no hanging processes)

## 🔍 Final Verification

Run these commands as a final check:

```bash
# Clean everything
mvn clean
rm -rf target/

# Fresh build
mvn clean install

# Run application
mvn javafx:run
```

**Expected results:**
1. ✅ Build SUCCESS (no errors)
2. ✅ Application window opens
3. ✅ Map displays correctly
4. ✅ Vehicles move
5. ✅ Traffic lights change colors
6. ✅ No errors in console

## 📝 Post-Deployment

After deploying:

- [ ] Update documentation with any new findings
- [ ] Respond to user issues promptly
- [ ] Keep track of common problems
- [ ] Update troubleshooting guides as needed

---

**Last Checked:** December 11, 2025
**Checked By:** [Your Name]
**Status:** [ ] Ready / [ ] Needs Work
