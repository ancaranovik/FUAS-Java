package novik;

import javafx.geometry.Point2D;
import java.util.List;

/**
 * Wrapper class for backward compatibility
 * Class wrapper để tương thích ngược
 * 
 * @deprecated Use {@link novik.util.MapUtil} instead
 */
@Deprecated
public class MapUtil {
    
    /**
     * Check if bounds are ready
     */
    public static boolean boundsReady = false;

    // Setup graphics: set canvas size, margin, load network, compute bounds
    // Khởi tạo đồ họa: đặt kích thước canvas, margin, load mạng lưới, tính biên
    public static void setup(double _canvasWidth, double _canvasHeight, double _MARGIN){
        novik.util.MapUtil.computeBounds(_canvasWidth, _canvasHeight, _MARGIN);
        boundsReady = novik.util.MapUtil.boundsReady;
    }

    // Convert a point from world (SUMO) coordinates to screen (JavaFX) coordinates
    // Chuyển một điểm từ tọa độ thế giới (SUMO) sang tọa độ màn hình (JavaFX)
    public static Point2D worldToScreen(Point2D point){
        boundsReady = novik.util.MapUtil.boundsReady;
        return novik.util.MapUtil.worldToScreen(point);
    }
    
    /**
     * Get lane polylines in screen coordinates
     */
    public static List<List<Point2D>> getLanePolylinesScreen() {
        return novik.util.MapUtil.getLanePolylinesScreen();
    }
}


