package novik.model;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        
        // Configure ImageView properties based on vehicle type
        // Cấu hình thuộc tính ImageView dựa trên loại xe
        switch (type) {
            case "bus":
                view.setFitWidth(20);
                view.setFitHeight(50);
                break;
            case "truck":
                view.setFitWidth(20);
                view.setFitHeight(35);
                break;
            default: // car and others
                view.setFitWidth(20);
                view.setFitHeight(30);
        }
        view.setPreserveRatio(true);
        view.setSmooth(true);
    }

    // Getters
    public String getId() {
        return id;
    }

    public ImageView getView() {
        return view;
    }

    public Point2D getPosition() {
        return position;
    }

    public double getAngle() {
        return angle;
    }

    /**
     * Get vehicle type
     * Lấy loại xe
     */
    public String getType() {
        return type;
    }

    /**
     * Update the car's position on screen
     * Cập nhật vị trí xe trên màn hình
     */
    public void updatePosition(Point2D newPosition) {
        this.position = newPosition;
        double w = view.getFitWidth();
        double h = view.getFitHeight();
        view.setLayoutX(newPosition.getX() - w / 2.0);
        view.setLayoutY(newPosition.getY() - h / 2.0);
    }

    /**
     * Update the car's rotation angle
     * Cập nhật góc xoay của xe
     */
    public void updateRotation(double angle) {
        this.angle = angle;
        view.setRotate(angle);
    }

    /**
     * Update both position and rotation at once
     * Cập nhật cả vị trí và góc xoay cùng lúc
     */
    public void update(Point2D newPosition, double newAngle) {
        updatePosition(newPosition);
        updateRotation(newAngle);
    }
}
