package novik;

// Import AnimationTimer for game/simulation loop
// Import AnimationTimer để tạo vòng lặp mô phỏng hoặc game
import javafx.animation.AnimationTimer;
// Import FXML annotation for linking UI components
// Import annotation FXML để liên kết các thành phần giao diện
import javafx.fxml.FXML;
// Import Canvas for drawing graphics
// Import Canvas để vẽ đồ họa
import javafx.scene.canvas.Canvas;
// Import GraphicsContext for drawing on Canvas
// Import GraphicsContext để vẽ lên Canvas
import javafx.scene.canvas.GraphicsContext;
// Import Pane for layer containers
// Import Pane để làm lớp chứa các thành phần giao diện
import javafx.scene.layout.Pane;
// Import VBox for vertical layout (used for info panels)
// Import VBox để bố trí các thành phần theo chiều dọc (panel thông tin)
import javafx.scene.layout.VBox;
// Import Label for displaying text
// Import Label để hiển thị văn bản
import javafx.scene.control.Label;

// Import file and path utilities for file existence checks
// Import các tiện ích file và đường dẫn để kiểm tra file tồn tại
import java.nio.file.Files;
import java.nio.file.Path;

// Import new layer classes (not deprecated)
// Import các class layer mới (không bị deprecated)
import novik.layer.CarLayer;
import novik.layer.LaneLayer;
import novik.layer.TrafficLightLayer;
import novik.util.MapUtil;
import novik.constants.SimulationConstants;
import novik.enums.VehicleType;
import novik.enums.RouteId;

/**
 * Main controller for the SUMO traffic simulation visualization
 * Controller chính cho ứng dụng mô phỏng giao thông SUMO
 */
public class MainController {
    // ========== FXML Components - General ==========
    /** Canvas for drawing background */
    @FXML private Canvas canvas;

    // ========== FXML Components - Traffic Lights ==========
    /** Pane for displaying traffic lights */
    @FXML private Pane trafficLightLayer;
    /** Info box for traffic light details */
    @FXML private VBox tlsInfoBox;
    /** Label for traffic light ID */
    @FXML private Label tlsIdLabel;
    /** Label for traffic light state */
    @FXML private Label tlsStateLabel;
    /** Label for traffic light phase */
    @FXML private Label tlsPhaseLabel;
    /** Label for traffic light feedback */
    @FXML private Label tlsFeedbackLabel;
    /** Container for traffic light phases */
    @FXML private VBox tlsPhasesContainer;

    // ========== FXML Components - Lanes & Cars ==========
    /** Pane for displaying lanes */
    @FXML private Pane laneLayer;
    /** Pane for displaying cars */
    @FXML private Pane carLayer;

    // ========== FXML Components - Buttons ==========
    /** Button to add vehicles */
    @FXML private javafx.scene.control.Button addVehicleBtn;

    // ========== Rendering ==========
    /** Graphics context for canvas drawing */
    private GraphicsContext graphicsContext;

    // ========== Simulation State ==========
    /** Main simulation loop timer */
    private AnimationTimer timer;
    
    /** Flag indicating if simulation is running */
    private boolean running = false;
    
    /** Car layer instance */
    private CarLayer carLayerInstance;
    
    /** Traffic light layer instance */
    private TrafficLightLayer trafficLightLayerInstance;
    
    /** Info panel instance */
    private InfoPanel infoPanelInstance;
    
    /** Lane layer instance */
    private LaneLayer laneLayerInstance;

    // ========== SUMO Configuration ==========
    /** SUMO configuration directory */
    private final Path sumoDir = Path.of("sumo");
    
    /** Path to SUMO configuration file */
    private final Path sumoCfgPath  = sumoDir.resolve("./simple/simple.sumocfg");
    
    /** Path to SUMO network file */
    private final Path sumoNetPath = sumoDir.resolve("./simple/simple.net.xml");

    /**
     * Startup method called by MainApp to initialize the simulation
     * Phương thức khởi động được gọi bởi MainApp để khởi tạo mô phỏng
     * 
     * @throws IllegalStateException if SUMO configuration files are missing
     */
    public void startup() {
        validateConfiguration();
        SumoBridge.startSUMO(sumoCfgPath);
        initializeGraphics();
        initializeLayers();
        startLoop();
        setupAddVehicleButton();
    }
    
    /**
     * Validate that required configuration files exist
     * Xác thực rằng các file cấu hình cần thiết đã tồn tại
     * 
     * @throws IllegalStateException if configuration files are not found
     */
    private void validateConfiguration() {
        if (!Files.exists(sumoCfgPath)) {
            throw new IllegalStateException("SUMO config not found: " + sumoCfgPath.toAbsolutePath());
        }
        if (!Files.exists(sumoNetPath)) {
            throw new IllegalStateException("SUMO net file not found: " + sumoNetPath.toAbsolutePath());
        }
    }
    
    /**
     * Initialize graphics context and map bounds
     * Khởi tạo graphics context và biên bản đồ
     */
    private void initializeGraphics() {
        MapUtil.computeBounds(canvas.getWidth(), canvas.getHeight(), SimulationConstants.SCREEN_MARGIN);
        graphicsContext = canvas.getGraphicsContext2D();
    }
    
    /**
     * Initialize all visualization layers
     * Khởi tạo tất cả các lớp hiển thị
     */
    private void initializeLayers() {
        carLayerInstance = new CarLayer(carLayer);
        laneLayerInstance = new LaneLayer(laneLayer);
        trafficLightLayerInstance = new TrafficLightLayer(trafficLightLayer);
        infoPanelInstance = new InfoPanel();

        laneLayerInstance.rebuild();
        trafficLightLayerInstance.rebuild();
    }

    /**
     * Shutdown method called by MainApp to cleanup resources
     * Phương thức tắt được gọi bởi MainApp để dọn dẹp tài nguyên
     */
    public void shutdown() {
        if (timer != null) {
            timer.stop();
        }
        if (SumoBridge.isServerActive()) {
            SumoBridge.stopSUMO();
        }
    }

    /**
     * Pause the simulation
     * Tạm dừng mô phỏng
     */
    @FXML
    private void pause() {
        running = false;
    }

    /**
     * Resume the simulation
     * Tiếp tục mô phỏng
     */
    @FXML
    private void resume() {
        running = true;
    }

    /**
     * Start the main simulation loop
     * Khởi động vòng lặp mô phỏng chính
     */
    private void startLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (running) {
                    SumoBridge.step();
                    drawFrame();
                    if (MapUtil.boundsReady) {
                        updateLayers();
                    }
                }
            }
        };
        timer.start();
        running = true;
    }
    
    /**
     * Update all visualization layers
     * Cập nhật tất cả các lớp hiển thị
     */
    private void updateLayers() {
        carLayerInstance.update();
        trafficLightLayerInstance.update();
        infoPanelInstance.updatePanel(carLayerInstance, trafficLightLayerInstance, laneLayerInstance);
    }

    /**
     * Draw the background frame
     * Vẽ khung nền
     */
    private void drawFrame() {
        graphicsContext.setFill(javafx.scene.paint.Color.web(SimulationConstants.BACKGROUND_COLOR));
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Setup button handler to add random vehicles
     * Thiết lập xử lý nút để thêm xe ngẫu nhiên
     */
    private void setupAddVehicleButton() {
        if (addVehicleBtn != null) {
            addVehicleBtn.setOnAction(event -> addRandomVehicle());
        }
    }
    
    /**
     * Add a random vehicle to the simulation
     * Thêm một xe ngẫu nhiên vào mô phỏng
     */
    private void addRandomVehicle() {
        VehicleType type = VehicleType.random();
        RouteId route = RouteId.random();
        carLayerInstance.addVehicle(type.getId(), route.getId());
    }
}
