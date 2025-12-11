package novik.layer;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import novik.model.TrafficLight;
import novik.util.MapUtil;
import org.eclipse.sumo.libtraci.Junction;
import org.eclipse.sumo.libtraci.TraCIPosition;

import java.util.*;

/**
 * Layer for displaying traffic lights
 * Lớp hiển thị đèn giao thông
 */
public class TrafficLightLayer extends Layer {
    private final Map<String, TrafficLight> trafficLights;
    private static final double CIRCLE_RADIUS = 8.0;

    /**
     * Constructor
     * @param pane The pane where traffic lights will be displayed
     */
    public TrafficLightLayer(Pane pane) {
        super(pane);
        this.trafficLights = new HashMap<>();
    }

    /**
     * Create a new traffic light and add it to the layer
     * Tạo đèn giao thông mới và thêm vào lớp
     */
    private TrafficLight createTrafficLight(String tlsId) {
        // Get position from SUMO using Junction
        // Lấy vị trí từ SUMO qua Junction
        TraCIPosition sumoPos = Junction.getPosition(tlsId);
        Point2D worldPos = new Point2D(sumoPos.getX(), sumoPos.getY());
        Point2D screenPos = MapUtil.worldToScreen(worldPos);
        
        // Create traffic light
        // Tạo đèn giao thông
        TrafficLight tl = new TrafficLight(tlsId, screenPos, CIRCLE_RADIUS);
        trafficLights.put(tlsId, tl);
        pane.getChildren().add(tl.getCircle());
        
        return tl;
    }

    /**
     * Update traffic light state from SUMO
     * Cập nhật trạng thái đèn từ SUMO
     */
    private void updateTrafficLightFromSumo(TrafficLight tl) {
        String tlsId = tl.getTlsId();
        String state = org.eclipse.sumo.libtraci.TrafficLight.getRedYellowGreenState(tlsId);
        tl.setState(state);
    }

    @Override
    public void update() {
        // Update all existing traffic lights
        // Cập nhật tất cả đèn giao thông hiện có
        for (TrafficLight tl : trafficLights.values()) {
            updateTrafficLightFromSumo(tl);
        }
    }

    @Override
    public void rebuild() {
        clear();
        trafficLights.clear();
        
        // Get all traffic light IDs from SUMO
        // Lấy tất cả ID đèn giao thông từ SUMO
        List<String> tlsIds = org.eclipse.sumo.libtraci.TrafficLight.getIDList();
        
        // Create traffic lights for all IDs
        // Tạo đèn giao thông cho tất cả ID
        for (String tlsId : tlsIds) {
            TrafficLight tl = createTrafficLight(tlsId);
            updateTrafficLightFromSumo(tl);
        }
    }

    /**
     * Get the number of traffic lights currently displayed
     * Lấy số lượng đèn giao thông đang được hiển thị
     */
    public int getTrafficLightCount() {
        return trafficLights.size();
    }

    /**
     * Get a traffic light by ID
     * Lấy đèn giao thông theo ID
     */
    public TrafficLight getTrafficLight(String tlsId) {
        return trafficLights.get(tlsId);
    }

    /**
     * Get all traffic lights
     * Lấy tất cả đèn giao thông
     */
    public Collection<TrafficLight> getAllTrafficLights() {
        return trafficLights.values();
    }
}
