package novik;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.eclipse.sumo.libtraci.Junction;
import org.eclipse.sumo.libtraci.TraCIPosition;
import org.eclipse.sumo.libtraci.TrafficLight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Path;

import novik.util.MapUtil;

/**
 * TrafficLightLayer: quản lý hiển thị và cập nhật trạng thái đèn giao thông SUMO trên JavaFX.
 * TrafficLightLayer: manages display and state updates of SUMO traffic lights on JavaFX.
 */
public class TrafficLightLayer {

    // --- Styling constants (tách riêng để dễ thay đổi) ---
    // --- Styling constants (isolated for easy tweaking) ---
    private static final double CIRCLE_RADIUS = 5.0;          // Bán kính hình tròn / Circle radius (px)
    private static final Color CIRCLE_STROKE = Color.BLACK;    // Màu viền / Stroke color
    private static final double CIRCLE_STROKE_WIDTH = 0.5;    // Độ dày viền / Stroke width

    // Pane nơi các node đèn sẽ được thêm vào / Pane where traffic light nodes are added
    private final Pane trafficLightLayer;
    // Map ánh xạ ID đèn -> Circle node để cập nhật màu nhanh / Map from traffic light ID -> Circle for quick updates
    private final Map<String, Circle> trafficLightCircles = new HashMap<>();

    /**
     * Khởi tạo lớp hiển thị đèn giao thông.
     * Initialize the traffic light display layer.
     *
     * @param trafficLightLayer Pane để chứa các node đèn / Pane to contain traffic light nodes
     * @param netFilePath       Đường dẫn file mạng SUMO (không dùng trực tiếp) / SUMO net file path (not used directly)
     */
    public TrafficLightLayer(Pane trafficLightLayer, Path netFilePath) {
        // Phòng lỗi: Pane không được null / Defensive: Pane must not be null
        if (trafficLightLayer == null) {
            throw new IllegalArgumentException("trafficLightLayer Pane must not be null");
        }
        this.trafficLightLayer = trafficLightLayer;

        // Xây dựng các node đèn ngay khi lớp được tạo (sau khi MapUtil sẵn sàng) / Build nodes on init (once MapUtil is ready)
        buildTrafficLightCircles();
    }

    /**
     * Tạo node JavaFX cho từng đèn giao thông dựa trên vị trí từ SUMO.
     * Build a JavaFX Circle node for each traffic light using positions from SUMO.
     *
     * Gọi một lần sau khi MapUtil đã tính xong transform (boundsReady).
     * Called once after MapUtil computed transform (boundsReady).
     */
    private void buildTrafficLightCircles() {
        // Đảm bảo transform sẵn sàng (có scale/bounds) / Ensure transform ready (have scale/bounds)
        if (!MapUtil.boundsReady) {
            System.err.println("TrafficLightLayer: MapUtil chưa sẵn sàng. / MapUtil not ready.");
            return;
        }

        // Dọn dẹp trước khi xây lại / Clear before rebuilding
        trafficLightLayer.getChildren().clear();
        trafficLightCircles.clear();

        // Lấy danh sách tất cả ID đèn giao thông từ SUMO / Get all traffic light IDs from SUMO
        List<String> trafficLightIds = TrafficLight.getIDList();

        for (String trafficLightId : trafficLightIds) {
            try {
                // Lấy vị trí nút giao (junction) của đèn / Get junction position of the light
                TraCIPosition sumoPos = Junction.getPosition(trafficLightId);
                if (sumoPos == null) {
                    // Không có vị trí -> bỏ qua / No position -> skip
                    System.err.printf("TrafficLightLayer: vị trí null cho '%s'%n", trafficLightId);
                    continue;
                }

                // Chuyển world -> screen để đặt node trên UI / Convert world -> screen to place on UI
                Point2D screenPos = MapUtil.worldToScreen(new Point2D(sumoPos.getX(), sumoPos.getY()));

                // Tạo Circle nhỏ để biểu diễn đèn trên map / Create small Circle to represent light on the map
                Circle circle = new Circle(screenPos.getX(), screenPos.getY(), CIRCLE_RADIUS);
                circle.setStroke(CIRCLE_STROKE);
                circle.setStrokeWidth(CIRCLE_STROKE_WIDTH);
                circle.setStrokeType(StrokeType.OUTSIDE);
                circle.setFill(Color.GRAY); // Màu mặc định trước khi cập nhật trạng thái / Default fill before state update

                // Thêm vào scene graph và lưu vào Map / Add to scene graph and store in Map
                trafficLightLayer.getChildren().add(circle);
                trafficLightCircles.put(trafficLightId, circle);

            } catch (Exception e) {
                // Bắt mọi lỗi khi tạo node để không làm vỡ vòng lặp / Catch any node creation error to keep loop healthy
                System.err.printf("TrafficLightLayer: Không tạo được node cho đèn '%s': %s%n", trafficLightId, e.getMessage());
            }
        }

        // Sau khi tạo xong tất cả node, cập nhật màu theo trạng thái hiện tại / After creation, update colors by current states
        updateTrafficLightStates();
    }

    /**
     * Cập nhật màu cho tất cả đèn theo trạng thái từ SUMO.
     * Update fill color of all lights based on SUMO state string.
     *
     * Nên gọi mỗi frame (AnimationTimer) để hiển thị trạng thái mới nhất.
     * Should be called each frame (AnimationTimer) to reflect latest state.
     */
    public void updateTrafficLightStates() {
        for (Map.Entry<String, Circle> entry : trafficLightCircles.entrySet()) {
            final String trafficLightId = entry.getKey();
            final Circle circle = entry.getValue();

            try {
                // Chuỗi trạng thái ví dụ: "Gr", "ry", "rG", ... (mỗi ký tự là một tín hiệu cho hướng/nhóm)
                // State string example: "Gr", "ry", "rG", ... (each char is a signal for a direction/group)
                String state = TrafficLight.getRedYellowGreenState(trafficLightId);

                // Trường hợp thiếu trạng thái / Missing or invalid state
                if (state == null || state.isEmpty()) {
                    circle.setFill(Color.GRAY);
                    continue;
                }

                // Heuristic: dùng ký tự thứ 2 để quyết định màu chính (tùy mô hình tlLogic)
                // Heuristic: use 2nd character to decide main color (depends on tlLogic design)
                char signal = Character.toLowerCase(state.charAt(Math.min(1, state.length() - 1)));

                switch (signal) {
                    case 'g': // green
                        circle.setFill(Color.GREEN);
                        break;
                    case 'y': // yellow
                        circle.setFill(Color.YELLOW);
                        break;
                    case 'r': // red
                        circle.setFill(Color.DARKRED);
                        break;
                    default:
                        // Ký tự không xác định -> màu xám / Unknown char -> gray
                        circle.setFill(Color.GRAY);
                        break;
                }

            } catch (Exception e) {
                // Lỗi khi lấy trạng thái: log và tô màu đen để nổi bật / Error fetching state: log and set black to highlight
                System.err.printf("TrafficLightLayer: Không cập nhật trạng thái cho '%s': %s%n", trafficLightId, e.getMessage());
                circle.setFill(Color.BLACK);
            }
        }
    }
}