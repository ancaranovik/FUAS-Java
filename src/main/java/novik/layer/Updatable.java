package novik.layer;

/**
 * Interface for objects that can be updated in the simulation loop
 * Interface cho các đối tượng có thể được cập nhật trong vòng lặp mô phỏng
 */
public interface Updatable {
    /**
     * Update the object's state
     * Cập nhật trạng thái của đối tượng
     */
    void update();
    
    /**
     * Rebuild the object from scratch
     * Xây dựng lại đối tượng từ đầu
     */
    void rebuild();
}
