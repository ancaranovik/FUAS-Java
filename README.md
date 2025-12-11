# Traffic Flow Simulation - Mô phỏng giao thông với SUMO

Ứng dụng mô phỏng giao thông sử dụng JavaFX và SUMO (Simulation of Urban MObility).

## 🚀 Quick Start

**For detailed installation guide, see [`INSTALL.md`](INSTALL.md)**

### Prerequisites
1. **Java 17+** - [Download](https://adoptium.net/)
2. **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
3. **SUMO 1.20+** - [Download](https://eclipse.dev/sumo/)

### Run
```bash
git clone <repository-url>
cd FUAS-Java
mvn clean install
mvn javafx:run
```

**IMPORTANT:** SUMO must be installed and in your system PATH!

---

## 📚 Documentation

- **[`INSTALL.md`](INSTALL.md)** - Complete installation guide for new users
- **[`TROUBLESHOOT_SUMO.md`](TROUBLESHOOT_SUMO.md)** - Fix SUMO connection issues
- **[`SETUP_GUIDE_VI.md`](SETUP_GUIDE_VI.md)** - Vietnamese setup guide
- **[`QUICK_FIX.md`](QUICK_FIX.md)** - Recent fixes and changes
- **[`COMPARISON_DETAILED.md`](COMPARISON_DETAILED.md)** - Comparing with other projects

---

## Yêu cầu hệ thống / System Requirements

### 1. Java Development Kit (JDK)
- **Yêu cầu:** JDK 17 hoặc cao hơn
- **Tải xuống:** [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) hoặc [OpenJDK](https://adoptium.net/)
- **Kiểm tra:** Mở terminal/command prompt và chạy:
  ```bash
  java -version
  ```

### 2. Apache Maven
- **Yêu cầu:** Maven 3.6 hoặc cao hơn
- **Tải xuống:** [Apache Maven](https://maven.apache.org/download.cgi)
- **Kiểm tra:**
  ```bash
  mvn -version
  ```

### 3. SUMO (Simulation of Urban MObility)
- **Yêu cầu:** SUMO 1.25.0 (khuyến nghị) hoặc phiên bản tương thích
- **Tải xuống:** [SUMO Download](https://eclipse.dev/sumo/)
- **Cài đặt cho Windows:**
  1. Tải file cài đặt từ trang chủ SUMO
  2. Cài đặt vào thư mục mặc định (ví dụ: `C:\Program Files (x86)\Eclipse\Sumo`)
  3. Thêm SUMO vào PATH:
     - Mở `System Properties` → `Advanced` → `Environment Variables`
     - Thêm đường dẫn SUMO bin vào PATH (ví dụ: `C:\Program Files (x86)\Eclipse\Sumo\bin`)
  4. Kiểm tra:
     ```bash
     sumo --version
     ```

### 4. Thư viện libtraci
- File `libs/libtraci-1.25.0.jar` đã được đính kèm trong project
- **Lưu ý:** Đảm bảo file này tồn tại trong thư mục `libs/`

## Cài đặt và chạy / Installation & Running

### Bước 1: Clone project
```bash
git clone <repository-url>
cd FUAS-Java
```

### Bước 2: Kiểm tra file libtraci
Đảm bảo file `libs/libtraci-1.25.0.jar` tồn tại. Nếu không có:
1. Tải SUMO version 1.25.0
2. Tìm file `libtraci-1.25.0.jar` trong thư mục cài đặt SUMO
3. Copy vào thư mục `libs/` của project

### Bước 3: Setup SUMO environment (Linux only)
```bash
# Make script executable and run
chmod +x setup-sumo-linux.sh
source setup-sumo-linux.sh

# This will set SUMO_HOME and LD_LIBRARY_PATH
```

**Windows users:** 
- Run `setup-sumo-windows.bat` to check SUMO installation
- Edit `pom.xml`: uncomment Windows library path, comment Linux path

### Bước 4: Build project
```bash
# Option 1: Build and install to local Maven repository
mvn clean install

# Option 2: Build and package to JAR file
mvn clean package
# JAR file will be created in target/ directory
```

**Explanation:**
- `mvn clean install`: Clean old builds, compile, run tests, install to local Maven repo
- `mvn clean package`: Clean old builds, compile, run tests, create JAR in `target/`
- `mvn javafx:run`: Run JavaFX application directly (no JAR needed)

### Bước 5: Chạy ứng dụng
```bash
mvn javafx:run
```

**Expected output:**
```
✅ SUMO started successfully!
```

If you see `❌ Failed to start SUMO`, read the error messages and check:
1. SUMO is installed: `sumo --version`
2. Config file exists: `ls sumo/simple/simple.sumocfg`
3. Library path is correct in `pom.xml`

## Khắc phục sự cố / Troubleshooting

### Lỗi: "Could not connect to TraCI server"
**Hiện:** `Could not connect to TraCI server at localhost:42067`
- SUMO chưa được cài đặt hoặc không có trong PATH
- Kiểm tra: `sumo --version`
- Cài đặt: Xem phần "SUMO" ở trên
- Đảm bảo file `sumo/simple/simple.sumocfg` tồn tại trong project

### Lỗi: "SUMO not found"
- Kiểm tra SUMO đã được cài đặt và thêm vào PATH
- Thử chạy `sumo --version` trong terminal

### Lỗi: "libtraci-1.25.0.jar not found"
- Kiểm tra file jar có trong thư mục `libs/`
- Đảm bảo đường dẫn trong `pom.xml` đúng

### Lỗi: "Java version mismatch"
- Kiểm tra Java version: `java -version`
- Cần JDK 17 trở lên

### Lỗi build Maven
```bash
# Xóa cache và build lại
mvn clean
mvn install -U
```

### Error: Tests failed during build
```bash
# Skip tests if needed (not recommended for production)
mvn clean package -DskipTests

# Or check test details
mvn clean test
```

## Cấu trúc project / Project Structure

```
FUAS-Java/
├── libs/                          # Thư viện local
│   └── libtraci-1.25.0.jar       # SUMO TraCI library
├── src/
│   └── main/
│       ├── java/novik/           # Source code
│       │   ├── MainApp.java      # Entry point
│       │   ├── MainController.java
│       │   ├── layer/            # Visualization layers
│       │   ├── model/            # Data models
│       │   └── util/             # Utilities
│       └── resources/            # UI resources & images
├── sumo/simple/                  # SUMO configuration files
│   ├── simple.sumocfg            # Main SUMO config
│   ├── simple.net.xml            # Network definition
│   ├── simple.rou.xml            # Routes
│   └── ...
└── pom.xml                       # Maven configuration
```

## Chạy với IDE

### IntelliJ IDEA
1. Mở project (File → Open → chọn thư mục FUAS-Java)
2. Maven sẽ tự động import dependencies
3. Chạy class `MainApp.java` (phải cấu hình VM options cho JavaFX nếu cần)
4. Hoặc dùng Maven tool window: chạy `javafx:run`

### Eclipse
1. Import project: File → Import → Existing Maven Projects
2. Chọn thư mục FUAS-Java
3. Maven sẽ tự động cấu hình
4. Right-click project → Run As → Maven build... → Goals: `javafx:run`

### VS Code
1. Cài đặt extensions:
   - Extension Pack for Java
   - Maven for Java
2. Mở folder project
3. Mở terminal trong VS Code và chạy: `mvn javafx:run`

## Liên hệ / Contact

Nếu gặp vấn đề, hãy tạo issue trên GitHub hoặc liên hệ team phát triển.

## License

[Thêm license của bạn ở đây]
