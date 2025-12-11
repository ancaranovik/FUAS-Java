package novik.layer;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import novik.model.TrafficLight;
import novik.util.MapUtil;
import novik.constants.SimulationConstants;
import org.eclipse.sumo.libtraci.Junction;
import org.eclipse.sumo.libtraci.TraCIPosition;

import java.util.*;

/**
 * Layer for displaying traffic lights
 * Lớp hiển thị đèn giao thông
 */
public class TrafficLightLayer extends Layer {
    private final Map<String, TrafficLight> trafficLights;

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
        TraCIPosition sumoPos = Junction.getPosition(tlsId);
        Point2D worldPos = new Point2D(sumoPos.getX(), sumoPos.getY());
        Point2D screenPos = MapUtil.worldToScreen(worldPos);
        
        TrafficLight tl = new TrafficLight(tlsId, screenPos, SimulationConstants.TRAFFIC_LIGHT_RADIUS);
        trafficLights.put(tlsId, tl);
        pane.getChildren().add(tl.getCircle());
        
        return tl;
    }

    /**
     * Update traffic light state from SUMO
     * Cập nhật trạng thái đèn từ SUMO
     */
    private void updateTrafficLightFromSumo(TrafficLight tl) {
        String state = org.eclipse.sumo.libtraci.TrafficLight.getRedYellowGreenState(tl.getTlsId());
        tl.setState(state);
    }

    @Override
    public void update() {
        trafficLights.values().forEach(this::updateTrafficLightFromSumo);
    }

    @Override
    public void rebuild() {
        clear();
        trafficLights.clear();
        
        List<String> tlsIds = org.eclipse.sumo.libtraci.TrafficLight.getIDList();
        tlsIds.forEach(tlsId -> {
            TrafficLight tl = createTrafficLight(tlsId);
            updateTrafficLightFromSumo(tl);
        });
    }

    /**
     * Get the number of traffic lights currently displayed
     * Lấy số lượng đèn giao thông đang được hiển thị
     * 
     * @return Number of traffic lights
     */
    public int getTrafficLightCount() {
        return trafficLights.size();
    }

    /**
     * Get a traffic light by ID
     * Lấy đèn giao thông theo ID
     * 
     * @param tlsId Traffic light system ID
     * @return TrafficLight object or null if not found
     */
    public TrafficLight getTrafficLight(String tlsId) {
        return trafficLights.get(tlsId);
    }

    /**
     * Get all traffic lights
     * Lấy tất cả đèn giao thông
     * 
     * @return Collection of all traffic lights
     */
    public Collection<TrafficLight> getAllTrafficLights() {
        return trafficLights.values();
    }
}
