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
     * Kiểm tra xem bounds đã sẵn sàng chưa
     */
    public static boolean boundsReady = false;

    /**
     * Setup graphics and compute bounds
     * Khởi tạo đồ họa và tính biên
     * 
     * @param _canvasWidth Canvas width
     * @param _canvasHeight Canvas height
     * @param _MARGIN Margin size
     */
    public static void setup(double _canvasWidth, double _canvasHeight, double _MARGIN){
        novik.util.MapUtil.computeBounds(_canvasWidth, _canvasHeight, _MARGIN);
        boundsReady = novik.util.MapUtil.boundsReady;
    }

    /**
     * Convert world coordinates to screen coordinates
     * Chuyển đổi tọa độ thế giới sang tọa độ màn hình
     * 
     * @param point Point in world coordinates
     * @return Point in screen coordinates
     */
    public static Point2D worldToScreen(Point2D point){
        boundsReady = novik.util.MapUtil.boundsReady;
        return novik.util.MapUtil.worldToScreen(point);
    }
    
    /**
     * Get lane polylines in screen coordinates
     * Lấy các polyline lane theo tọa độ màn hình
     * 
     * @return List of lane polylines
     */
    public static List<List<Point2D>> getLanePolylinesScreen() {
        return novik.util.MapUtil.getLanePolylinesScreen();
    }
}


