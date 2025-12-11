package novik.layer;

import javafx.scene.layout.Pane;

/**
 * Abstract base class for all visualization layers
 * Lớp cơ sở trừu tượng cho tất cả các lớp hiển thị
 */
public abstract class Layer {
    protected final Pane pane;

    /**
     * Constructor
     * @param pane The JavaFX Pane where this layer will render
     */
    public Layer(Pane pane) {
        if (pane == null) {
            throw new IllegalArgumentException("Pane cannot be null");
        }
        this.pane = pane;
        initialize();
    }

    /**
     * Initialize the layer (called in constructor)
     * Khởi tạo lớp (được gọi trong constructor)
     */
    protected void initialize() {
        pane.getChildren().clear();
    }

    /**
     * Update the layer's content (must be implemented by subclasses)
     * Cập nhật nội dung lớp (phải được implement bởi các lớp con)
     */
    public abstract void update();

    /**
     * Rebuild the entire layer from scratch
     * Xây dựng lại toàn bộ lớp từ đầu
     */
    public abstract void rebuild();

    /**
     * Clear all content from this layer
     * Xóa tất cả nội dung khỏi lớp này
     */
    public void clear() {
        pane.getChildren().clear();
    }

    /**
     * Get the pane
     * Lấy pane
     */
    public Pane getPane() {
        return pane;
    }
}
