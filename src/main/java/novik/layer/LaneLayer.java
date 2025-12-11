package novik.layer;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import novik.util.MapUtil;
import novik.constants.SimulationConstants;

import java.util.List;

/**
 * Layer for displaying road lanes
 * Lớp hiển thị làn đường
 */
public class LaneLayer extends Layer {
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
    }

    @Override
    public void rebuild() {
        clear();
        
        if (!MapUtil.boundsReady) {
            return;
        }

        List<List<Point2D>> lanePolylinesScreen = MapUtil.getLanePolylinesScreen();

        for (List<Point2D> polyline : lanePolylinesScreen) {
            if (polyline.isEmpty()) {
                continue;
            }

            double[] points = convertPolylineToArray(polyline);
            Polyline line = createPolyline(points);
            pane.getChildren().add(line);
        }
    }
    
    /**
     * Convert polyline points to array
     * Chuyển đổi điểm polyline thành mảng
     */
    private double[] convertPolylineToArray(List<Point2D> polyline) {
        double[] points = new double[polyline.size() * 2];
        for (int i = 0; i < polyline.size(); i++) {
            Point2D p = polyline.get(i);
            points[i * 2] = p.getX();
            points[i * 2 + 1] = p.getY();
        }
        return points;
    }
    
    /**
     * Create a polyline with default styling
     * Tạo polyline với style mặc định
     */
    private Polyline createPolyline(double[] points) {
        Polyline line = new Polyline(points);
        line.setStroke(LANE_COLOR);
        line.setStrokeWidth(SimulationConstants.LANE_STROKE_WIDTH);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setFill(null);
        return line;
    }

    /**
     * Set lane color
     * Đặt màu làn đường
     * 
     * @param color New color for lanes
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
     * 
     * @param width New stroke width in pixels
     */
    public void setLaneStrokeWidth(double width) {
        pane.getChildren().forEach(node -> {
            if (node instanceof Polyline) {
                ((Polyline) node).setStrokeWidth(width);
            }
        });
    }
}
