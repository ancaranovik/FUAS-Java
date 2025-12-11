package novik;

// This class is deprecated. Use novik.layer.CarLayer instead.
// Class này đã lỗi thời. Sử dụng novik.layer.CarLayer thay thế.

import javafx.scene.layout.Pane;

/**
 * @deprecated Use {@link novik.layer.CarLayer} instead for better OOP design
 */
@Deprecated
public class CarLayer extends novik.layer.CarLayer {
    
    /**
     * Constructor: receives the Pane where cars will be displayed
     * Hàm khởi tạo: nhận Pane để hiển thị xe
     * 
     * @param carLayerPane The pane for displaying cars
     */
    public CarLayer(Pane carLayerPane) {
        super(carLayerPane);
    }

    /**
     * Update all cars: add new, update position, remove disappeared
     * Cập nhật tất cả xe: thêm mới, cập nhật vị trí, xóa xe biến mất
     * 
     * @deprecated Use {@link #update()} instead
     */
    @Deprecated
    public void updateCars() {
        update();
    }

    /**
     * Add a new vehicle to SUMO
     * Thêm xe mới vào SUMO
     * @param type Vehicle type
     * @param route Route ID
     */
    public void addVehicle(String type, String route) {
        // Delegate to new implementation
        super.addVehicle(type, route);
    }
}