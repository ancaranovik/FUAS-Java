package novik;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

/**
 * Main JavaFX Application class for SUMO traffic simulation visualization
 * Lớp Application JavaFX chính cho ứng dụng mô phỏng giao thông SUMO
 * 
 * <p>This class handles the application lifecycle including:</p>
 * <ul>
 *   <li>Loading the FXML UI layout</li>
 *   <li>Initializing the main controller</li>
 *   <li>Starting the simulation</li>
 *   <li>Cleaning up resources on shutdown</li>
 * </ul>
 */
public class MainApp extends Application {
    /** The main UI controller */
    private MainController controller;

    /**
     * Application startup method called by JavaFX framework
     * Phương thức khởi động được gọi bởi framework JavaFX
     * 
     * <p>This method:</p>
     * <ol>
     *   <li>Loads the FXML UI layout from resources</li>
     *   <li>Creates the main scene</li>
     *   <li>Gets the controller instance from FXML</li>
     *   <li>Configures and shows the primary stage (window)</li>
     *   <li>Starts the simulation</li>
     * </ol>
     * 
     * @param primaryStage The primary stage (main window) provided by JavaFX
     * @throws Exception if FXML loading fails or other initialization errors occur
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML UI layout from resources
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/primaryPane.fxml"));
        // Create the scene with loaded content
        Scene scene = new Scene(loader.load());
        // Get controller instance from FXML
        controller = loader.getController();

        // Configure and show the window
        primaryStage.setTitle("Traffic Flow Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initialize simulation
        controller.startup();
    }

    /**
     * Application shutdown method called by JavaFX when window is closed
     * Phương thức tắt được gọi bởi JavaFX khi cửa sổ đóng
     * 
     * <p>This method cleanly shuts down the simulation and releases resources
     * including SUMO connection and JavaFX timers.</p>
     */
    @Override
    public void stop() {
        if (controller != null) {
            controller.shutdown();
        }
    }

    /**
     * Application entry point
     * Điểm vào của ứng dụng
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
