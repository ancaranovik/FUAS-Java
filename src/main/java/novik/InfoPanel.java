package novik;

import novik.layer.CarLayer;
import novik.layer.TrafficLightLayer;
import novik.layer.LaneLayer;

/**
 * Empty panel class kept for backward compatibility
 * Lớp panel trống được giữ lại để tương thích ngược
 * 
 * @deprecated This class no longer provides any functionality
 */
@Deprecated
public class InfoPanel {
    /**
     * Default constructor
     * Constructor mặc định
     */
    public InfoPanel() {}
    
    /**
     * Update panel (no longer performs any action)
     * Cập nhật panel (không còn thực hiện hành động nào)
     * 
     * @param carLayer Car layer
     * @param trafficLightLayer Traffic light layer
     * @param laneLayer Lane layer
     */
    public void updatePanel(CarLayer carLayer, TrafficLightLayer trafficLightLayer, LaneLayer laneLayer) {
        // No operation
    }
}

