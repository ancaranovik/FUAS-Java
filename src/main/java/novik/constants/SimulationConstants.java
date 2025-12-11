package novik.constants;

/**
 * Central location for all simulation constants
 * Nơi tập trung tất cả các hằng số của mô phỏng
 * 
 * <p>This class contains all magic numbers and configuration values used
 * throughout the simulation, organized into categories:</p>
 * <ul>
 *   <li>Screen &amp; Canvas - Display settings</li>
 *   <li>Traffic Light - Visual properties</li>
 *   <li>Lane - Road rendering settings</li>
 *   <li>Vehicle Dimensions - Size of different vehicle types</li>
 * </ul>
 */
public final class SimulationConstants {
    
    /**
     * Private constructor to prevent instantiation
     * Constructor private để ngăn việc tạo instance
     */
    private SimulationConstants() {
        throw new AssertionError("Cannot instantiate constants class");
    }
    
    // ========== Screen & Canvas ==========
    /** Screen margin in pixels / Lề màn hình (pixel) */
    public static final double SCREEN_MARGIN = 30.0;
    
    /** Background color hex code / Mã màu nền dạng hex */
    public static final String BACKGROUND_COLOR = "#f4f4f4";
    
    // ========== Traffic Light ==========
    /** Traffic light circle radius in pixels / Bán kính vòng tròn đèn giao thông (pixel) */
    public static final double TRAFFIC_LIGHT_RADIUS = 8.0;
    
    /** Traffic light circle stroke width / Độ dày viền vòng tròn đèn */
    public static final double TRAFFIC_LIGHT_STROKE_WIDTH = 1.0;
    
    // ========== Lane ==========
    /** Lane line stroke width in pixels / Độ dày đường làn (pixel) */
    public static final double LANE_STROKE_WIDTH = 2.0;
    
    // ========== Vehicle Dimensions ==========
    /** Car width in pixels / Chiều rộng xe hơi (pixel) */
    public static final double CAR_WIDTH = 20.0;
    
    /** Car height in pixels / Chiều dài xe hơi (pixel) */
    public static final double CAR_HEIGHT = 30.0;
    
    /** Bus width in pixels / Chiều rộng xe buýt (pixel) */
    public static final double BUS_WIDTH = 20.0;
    
    /** Bus height in pixels / Chiều dài xe buýt (pixel) */
    public static final double BUS_HEIGHT = 50.0;
    
    /** Truck width in pixels / Chiều rộng xe tải (pixel) */
    public static final double TRUCK_WIDTH = 20.0;
    
    /** Truck height in pixels / Chiều dài xe tải (pixel) */
    public static final double TRUCK_HEIGHT = 35.0;
}
