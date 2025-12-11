package novik.layer;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import novik.util.MapUtil;

import java.util.List;

/**
 * Layer for displaying road lanes
 * Lớp hiển thị làn đường
 */
public class LaneLayer extends Layer {
    private static final double LANE_STROKE_WIDTH = 2.0;
    private static final Color LANE_COLOR = Color.LIGHTGRAY;

    /**
     * Constructor
     * @param pane The pane where lanes will be displayed
     */
    public LaneLayer(Pane pane) {
        super(pane);
    }

    @Override
    public void update() {
        // Lanes are static, no need to update
        // Làn đường là tĩnh, không cần cập nhật
    }

    @Override
    public void rebuild() {
        clear();
        
        // Check if bounds are ready
        // Kiểm tra xem bounds đã sẵn sàng chưa
        if (!MapUtil.boundsReady) {
            System.err.println("LaneLayer: MapUtil not ready.");
            return;
        }

        // Get all lane polylines in screen coordinates
        // Lấy tất cả polyline làn đường theo tọa độ màn hình
        List<List<Point2D>> lanePolylinesScreen = MapUtil.getLanePolylinesScreen();

        // Create and add polyline for each lane
        // Tạo và thêm polyline cho mỗi làn đường
        for (List<Point2D> polyline : lanePolylinesScreen) {
            if (polyline.isEmpty()) {
                continue;
            }

            // Convert points to array of doubles [x1, y1, x2, y2, ...]
            // Chuyển đổi điểm thành mảng số thực [x1, y1, x2, y2, ...]
            double[] points = new double[polyline.size() * 2];
            for (int i = 0; i < polyline.size(); i++) {
                Point2D p = polyline.get(i);
                points[i * 2] = p.getX();
                points[i * 2 + 1] = p.getY();
            }

            // Create JavaFX Polyline
            // Tạo Polyline JavaFX
            Polyline line = new Polyline(points);
            line.setStroke(LANE_COLOR);
            line.setStrokeWidth(LANE_STROKE_WIDTH);
            line.setStrokeLineCap(StrokeLineCap.ROUND);
            line.setFill(null); // No fill, just stroke
            // Không tô màu, chỉ vẽ đường

            // Add to pane
            // Thêm vào pane
            pane.getChildren().add(line);
        }
    }

    /**
     * Set lane color
     * Đặt màu làn đường
     */
    public void setLaneColor(Color color) {
        pane.getChildren().forEach(node -> {
            if (node instanceof Polyline) {
                ((Polyline) node).setStroke(color);
            }
        });
    }

    /**
     * Set lane stroke width
     * Đặt độ dày làn đường
     */
    public void setLaneStrokeWidth(double width) {
        pane.getChildren().forEach(node -> {
            if (node instanceof Polyline) {
                ((Polyline) node).setStrokeWidth(width);
            }
        });
    }
}
