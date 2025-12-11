package novik.model;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import novik.constants.SimulationConstants;
import novik.enums.VehicleType;

/**
 * Represents a single car in the simulation
 * Đại diện cho một chiếc xe trong mô phỏng
 */
public class Car {
    private final String id;
    private final String type;
    private final ImageView view;
    private Point2D position;
    private double angle;

    /**
     * Constructor for Car
     * @param id Vehicle ID from SUMO
     * @param type Vehicle type (car, bus, truck, etc.)
     * @param carImage The image to display for this car
     */
    public Car(String id, String type, Image carImage) {
        this.id = id;
        this.type = type;
        this.view = new ImageView(carImage);
        this.position = new Point2D(0, 0);
        this.angle = 0;
        
        configureView(type);
    }
    
    /**
     * Configure ImageView based on vehicle type
     * Cấu hình ImageView dựa trên loại xe
     */
    private void configureView(String type) {
        VehicleType vehicleType = VehicleType.fromId(type);
        
        switch (vehicleType) {
            case BUS:
                view.setFitWidth(SimulationConstants.BUS_WIDTH);
                view.setFitHeight(SimulationConstants.BUS_HEIGHT);
                break;
            case TRUCK:
                view.setFitWidth(SimulationConstants.TRUCK_WIDTH);
                view.setFitHeight(SimulationConstants.TRUCK_HEIGHT);
                break;
            case CAR:
            default:
                view.setFitWidth(SimulationConstants.CAR_WIDTH);
                view.setFitHeight(SimulationConstants.CAR_HEIGHT);
        }
        
        view.setPreserveRatio(true);
        view.setSmooth(true);
    }

    // ========== Getters ==========
    
    /**
     * Get vehicle ID
     * Lấy ID xe
     * @return Vehicle ID from SUMO
     */
    public String getId() {
        return id;
    }

    /**
     * Get the ImageView representing this car
     * Lấy ImageView đại diện cho xe này
     * @return JavaFX ImageView
     */
    public ImageView getView() {
        return view;
    }

    /**
     * Get current position
     * Lấy vị trí hiện tại
     * @return Current position as Point2D
     */
    public Point2D getPosition() {
        return position;
    }

    /**
     * Get current rotation angle
     * Lấy góc xoay hiện tại
     * @return Rotation angle in degrees
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Get vehicle type
     * Lấy loại xe
     * 
     * @return Vehicle type string (e.g., "car", "bus", "truck")
     */
    public String getType() {
        return type;
    }

    /**
     * Update the car's position on screen
     * Cập nhật vị trí xe trên màn hình
     * 
     * @param newPosition New position coordinates
     */
    public void updatePosition(Point2D newPosition) {
        this.position = newPosition;
        double w = view.getFitWidth();
        double h = view.getFitHeight();
        // Center the image at the position
        view.setLayoutX(newPosition.getX() - w / 2.0);
        view.setLayoutY(newPosition.getY() - h / 2.0);
    }

    /**
     * Update the car's rotation angle
     * Cập nhật góc xoay của xe
     * 
     * @param angle Rotation angle in degrees
     */
    public void updateRotation(double angle) {
        this.angle = angle;
        view.setRotate(angle);
    }

    /**
     * Update both position and rotation at once
     * Cập nhật cả vị trí và góc xoay cùng lúc
     * 
     * @param newPosition New position coordinates
     * @param newAngle New rotation angle in degrees
     */
    public void update(Point2D newPosition, double newAngle) {
        updatePosition(newPosition);
        updateRotation(newAngle);
    }
}
