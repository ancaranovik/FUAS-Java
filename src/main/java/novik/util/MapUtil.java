package novik.util;

import javafx.geometry.Point2D;
import org.eclipse.sumo.libtraci.Lane;
import org.eclipse.sumo.libtraci.TraCIPosition;
import org.eclipse.sumo.libtraci.TraCIPositionVector;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Utility class for coordinate conversion between SUMO and JavaFX
 * Lớp tiện ích chuyển đổi tọa độ giữa SUMO và JavaFX
 */
public final class MapUtil {
    // List of all lane polylines in world coordinates
    // Danh sách tất cả polyline lane theo tọa độ thế giới
    private static final List<List<Point2D>> lanePolylinesWorld = new ArrayList<>();
    
    // World bounds and scale
    // Biên và tỉ lệ của thế giới
    private static double minX, maxX, minY, maxY;
    private static double scale; // from meters in SUMO to pixels in JavaFX
    // từ mét trong SUMO sang pixel trong JavaFX
    private static double canvasWidth, canvasHeight, MARGIN;
    public static boolean boundsReady = false; // true if bounds/scale are set
    // true nếu đã tính được biên/tỉ lệ
    
    // Private constructor to prevent instantiation
    // Constructor private để ngăn việc tạo instance
    private MapUtil() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Compute map bounds and scale based on all lanes in SUMO
     * Tính toán biên và tỉ lệ dựa trên tất cả lane trong SUMO
     */
    public static void computeBounds(double canvasW, double canvasH, double margin) {
        canvasWidth = canvasW;
        canvasHeight = canvasH;
        MARGIN = margin;
        lanePolylinesWorld.clear();

        // Get all lane IDs from SUMO
        // Lấy tất cả ID lane từ SUMO
        List<String> laneIds = Lane.getIDList();
        List<Double> allX = new ArrayList<>();
        List<Double> allY = new ArrayList<>();

        // For each lane, get its shape (polyline) and store points
        // Với mỗi lane, lấy hình dạng (polyline) và lưu các điểm
        for (String laneId : laneIds) {
            TraCIPositionVector shape = Lane.getShape(laneId);
            List<Point2D> polyline = new ArrayList<>();
            for (TraCIPosition pos : shape.getValue()) {
                double x = pos.getX();
                double y = pos.getY();
                polyline.add(new Point2D(x, y));
                allX.add(x);
                allY.add(y);
            }
            lanePolylinesWorld.add(polyline);
        }

        // Compute bounding box from all points
        // Tính hộp bao từ tất cả các điểm
        DoubleSummaryStatistics statsX = allX.stream().mapToDouble(Double::doubleValue).summaryStatistics();
        DoubleSummaryStatistics statsY = allY.stream().mapToDouble(Double::doubleValue).summaryStatistics();

        minX = statsX.getMin();
        maxX = statsX.getMax();
        minY = statsY.getMin();
        maxY = statsY.getMax();

        double worldW = maxX - minX;
        double worldH = maxY - minY;

        // Compute scale to fit the map in canvas with margins
        // Tính tỉ lệ để vừa bản đồ vào canvas với margin
        double availableW = canvasWidth - 2 * MARGIN;
        double availableH = canvasHeight - 2 * MARGIN;

        double scaleX = availableW / worldW;
        double scaleY = availableH / worldH;
        scale = Math.min(scaleX, scaleY);

        boundsReady = true;
    }

    /**
     * Convert SUMO world coordinates to JavaFX screen coordinates
     * Chuyển đổi tọa độ thế giới SUMO sang tọa độ màn hình JavaFX
     * 
     * @param worldPoint Point in SUMO world coordinates
     * @return Point in JavaFX screen coordinates
     */
    public static Point2D worldToScreen(Point2D worldPoint) {
        double worldX = worldPoint.getX();
        double worldY = worldPoint.getY();

        // Translate to origin and scale
        // Dịch chuyển về gốc và scale
        double relX = worldX - minX;
        double relY = worldY - minY;

        double screenX = MARGIN + relX * scale;
        // Flip Y axis (SUMO Y increases upward, JavaFX Y increases downward)
        // Đảo trục Y (SUMO Y tăng lên trên, JavaFX Y tăng xuống dưới)
        double screenY = canvasHeight - (MARGIN + relY * scale);

        return new Point2D(screenX, screenY);
    }

    /**
     * Get all lane polylines in screen coordinates
     * Lấy tất cả polyline lane theo tọa độ màn hình
     */
    public static List<List<Point2D>> getLanePolylinesScreen() {
        List<List<Point2D>> result = new ArrayList<>();
        for (List<Point2D> worldPolyline : lanePolylinesWorld) {
            List<Point2D> screenPolyline = new ArrayList<>();
            for (Point2D wp : worldPolyline) {
                screenPolyline.add(worldToScreen(wp));
            }
            result.add(screenPolyline);
        }
        return result;
    }

    /**
     * Setup method for backward compatibility
     * Phương thức setup để tương thích ngược
     * 
     * @deprecated Use {@link #computeBounds(double, double, double)} instead
     */
    @Deprecated
    public static void setup(double canvasW, double canvasH, double margin) {
        computeBounds(canvasW, canvasH, margin);
    }
    
    /**
     * Get canvas width
     */
    public static double getCanvasWidth() {
        return canvasWidth;
    }

    /**
     * Get canvas height
     */
    public static double getCanvasHeight() {
        return canvasHeight;
    }
}
