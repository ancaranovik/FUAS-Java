package novik;

import novik.layer.CarLayer;
import novik.layer.TrafficLightLayer;
import novik.layer.LaneLayer;

public class InfoPanel {
    // Đã bỏ toàn bộ chức năng panel đèn giao thông, giữ lại class rỗng để tránh lỗi tham chiếu nếu còn gọi tới
    public InfoPanel() {}
    public void updatePanel(CarLayer carLayer, TrafficLightLayer trafficLightLayer, LaneLayer laneLayer) {
        // Không làm gì cả
    }
}

