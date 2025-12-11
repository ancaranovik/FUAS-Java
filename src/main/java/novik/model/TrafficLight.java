package novik.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import novik.constants.SimulationConstants;

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
        
        this.circle = createCircle(position, radius);
        updateColor();
    }
    
    /**
     * Create the visual circle representation
     * Tạo hình tròn đại diện
     */
    private Circle createCircle(Point2D position, double radius) {
        Circle c = new Circle(radius);
        c.setCenterX(position.getX());
        c.setCenterY(position.getY());
        c.setStroke(Color.BLACK);
        c.setStrokeWidth(SimulationConstants.TRAFFIC_LIGHT_STROKE_WIDTH);
        return c;
    }

    // ========== Getters ==========
    
    /**
     * Get traffic light system ID
     * Lấy ID hệ thống đèn giao thông
     * @return Traffic light ID
     */
    public String getTlsId() {
        return tlsId;
    }

    /**
     * Get the Circle shape representing this traffic light
     * Lấy hình Circle đại diện cho đèn giao thông này
     * @return JavaFX Circle
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Get current position
     * Lấy vị trí hiện tại
     * @return Position as Point2D
     */
    public Point2D getPosition() {
        return position;
    }

    /**
     * Get current state
     * Lấy trạng thái hiện tại
     * @return State string (e.g., "GGrrr")
     */
    public String getState() {
        return state;
    }

    /**
     * Update the traffic light state
     * Cập nhật trạng thái đèn giao thông
     * 
     * @param newState New state string (e.g., "GGrrr")
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
     * 
     * @param newPosition New position coordinates
     */
    public void updatePosition(Point2D newPosition) {
        this.position = newPosition;
        circle.setCenterX(newPosition.getX());
        circle.setCenterY(newPosition.getY());
    }
}
