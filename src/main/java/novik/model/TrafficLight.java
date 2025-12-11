package novik.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a traffic light in the simulation
 * Đại diện cho một đèn giao thông trong mô phỏng
 */
public class TrafficLight {
    private final String tlsId;
    private final Circle circle;
    private Point2D position;
    private String state;

    /**
     * Constructor
     * @param tlsId Traffic light system ID
     * @param position Position on screen
     * @param radius Circle radius
     */
    public TrafficLight(String tlsId, Point2D position, double radius) {
        this.tlsId = tlsId;
        this.position = position;
        this.state = "";
        
        // Create visual representation
        // Tạo hình ảnh đại diện
        this.circle = new Circle(radius);
        circle.setCenterX(position.getX());
        circle.setCenterY(position.getY());
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);
        updateColor();
    }

    // Getters
    public String getTlsId() {
        return tlsId;
    }

    public Circle getCircle() {
        return circle;
    }

    public Point2D getPosition() {
        return position;
    }

    public String getState() {
        return state;
    }

    /**
     * Update the traffic light state
     * Cập nhật trạng thái đèn giao thông
     */
    public void setState(String newState) {
        this.state = newState;
        updateColor();
    }

    /**
     * Update circle color based on state
     * Cập nhật màu vòng tròn dựa trên trạng thái
     */
    private void updateColor() {
        if (state == null || state.isEmpty()) {
            circle.setFill(Color.GRAY);
            return;
        }

        char firstChar = state.charAt(0);
        switch (firstChar) {
            case 'G':
            case 'g':
                circle.setFill(Color.GREEN);
                break;
            case 'y':
                circle.setFill(Color.YELLOW);
                break;
            case 'r':
                circle.setFill(Color.RED);
                break;
            default:
                circle.setFill(Color.GRAY);
        }
    }

    /**
     * Update position
     * Cập nhật vị trí
     */
    public void updatePosition(Point2D newPosition) {
        this.position = newPosition;
        circle.setCenterX(newPosition.getX());
        circle.setCenterY(newPosition.getY());
    }
}
