package novik.layer;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import novik.model.Car;
import novik.util.MapUtil;
import org.eclipse.sumo.libtraci.TraCIPosition;
import org.eclipse.sumo.libtraci.Vehicle;

import java.util.*;

/**
 * Layer responsible for displaying and updating cars
 * Lớp chịu trách nhiệm hiển thị và cập nhật xe
 */
public class CarLayer extends Layer {
    // Map to store cars with their ID
    // Bản đồ lưu trữ các xe với ID
    private final Map<String, Car> cars;
    
    // Map to store different vehicle type images
    // Bản đồ lưu trữ hình ảnh cho từng loại xe
    private final Map<String, Image> carImages;

    /**
     * Constructor
     * @param pane The pane where cars will be displayed
     */
    public CarLayer(Pane pane) {
        super(pane);
        this.cars = new HashMap<>();
        this.carImages = new HashMap<>();
        loadCarImages();
    }

    /**
     * Load all car images from resources
     * Load tất cả hình xe từ thư mục resources
     */
    private void loadCarImages() {
        carImages.put("car", loadImage("carblack.png"));
        carImages.put("bus", loadImage("bus.png"));
        carImages.put("truck", loadImage("truck.png"));
    }

    /**
     * Load a single car image from resources
     * Load một hình xe từ thư mục resources
     */
    private Image loadImage(String filename) {
        String absolutePath = getClass().getResource("/" + filename).toExternalForm();
        return new Image(absolutePath);
    }

    /**
     * Create a new car and add it to the layer
     * Tạo xe mới và thêm vào lớp
     * @param carId Vehicle ID
     * @param type Vehicle type (car, bus, truck)
     */
    private Car createCar(String carId, String type) {
        // Get image for this vehicle type, default to "car" if not found
        // Lấy hình ảnh cho loại xe này, mặc định là "car" nếu không tìm thấy
        Image img = carImages.getOrDefault(type, carImages.get("car"));
        Car car = new Car(carId, type, img);
        cars.put(carId, car);
        pane.getChildren().add(car.getView());
        return car;
    }

    /**
     * Update car position and rotation from SUMO
     * Cập nhật vị trí và góc xoay của xe từ SUMO
     */
    private void updateCarFromSumo(Car car) {
        String carId = car.getId();
        
        // Get position from SUMO
        // Lấy vị trí từ SUMO
        TraCIPosition sumoPos = Vehicle.getPosition(carId, false);
        Point2D javaPos = MapUtil.worldToScreen(
            new Point2D(sumoPos.getX(), sumoPos.getY())
        );
        
        // Get angle from SUMO
        // Lấy góc quay từ SUMO
        double angle = Vehicle.getAngle(carId);
        
        // Update car
        // Cập nhật xe
        car.update(javaPos, angle);
    }

    /**
     * Remove a car from the layer
     * Xóa xe khỏi lớp
     */
    private void removeCar(Car car) {
        pane.getChildren().remove(car.getView());
        cars.remove(car.getId());
    }

    @Override
    public void update() {
        // 1. Get all active vehicle IDs from SUMO
        // 1. Lấy tất cả ID xe đang hoạt động từ SUMO
        List<String> activeIds = Vehicle.getIDList();
        Set<String> activeCarIds = new HashSet<>(activeIds);
        Set<String> newCarIds = new HashSet<>(activeCarIds);

        // 2. Update existing cars and remove inactive ones
        // 2. Cập nhật các xe hiện có và xóa xe không còn hoạt động
        List<Car> carsToRemove = new ArrayList<>();
        for (Map.Entry<String, Car> entry : cars.entrySet()) {
            String carId = entry.getKey();
            Car car = entry.getValue();

            if (activeCarIds.contains(carId)) {
                // Car still exists - update it
                // Xe vẫn còn - cập nhật nó
                newCarIds.remove(carId);
                updateCarFromSumo(car);
            } else {
                // Car no longer exists - mark for removal
                // Xe không còn - đánh dấu để xóa
                carsToRemove.add(car);
            }
        }
        
        // Remove all inactive cars
        // Xóa tất cả xe không còn hoạt động
        for (Car car : carsToRemove) {
            removeCar(car);
        }

        // 3. Create and initialize new cars
        // 3. Tạo và khởi tạo xe mới
        for (String carId : newCarIds) {
            // Get vehicle type from SUMO
            // Lấy loại xe từ SUMO
            String type = Vehicle.getTypeID(carId);
            System.out.println("Creating new car: ID=" + carId + ", Type=" + type);
            Car car = createCar(carId, type);
            updateCarFromSumo(car);
            System.out.println("Car created and added to display");
        }
    }

    @Override
    public void rebuild() {
        clear();
        cars.clear();
        update();
    }

    /**
     * Get the number of cars currently displayed
     * Lấy số lượng xe đang được hiển thị
     */
    public int getCarCount() {
        return cars.size();
    }

    /**
     * Get a car by ID
     * Lấy xe theo ID
     */
    public Car getCar(String carId) {
        return cars.get(carId);
    }

    /**
     * Get all cars
     * Lấy tất cả xe
     */
    public Collection<Car> getAllCars() {
        return cars.values();
    }

    /**
     * Add a new vehicle to SUMO and update display
     * Thêm xe mới vào SUMO và cập nhật hiển thị
     * @param type Vehicle type (car, bus, truck)
     * @param route Route ID (r1, r2, etc.)
     */
    public void addVehicle(String type, String route) {
        // Generate unique vehicle ID
        // Tạo ID xe duy nhất
        String vehicleId = "veh_" + System.currentTimeMillis();
        
        try {
            System.out.println("=== Adding vehicle ===");
            System.out.println("ID: " + vehicleId);
            System.out.println("Type: " + type);
            System.out.println("Route: " + route);
            
            // Add vehicle to SUMO via TraCI
            // Thêm xe vào SUMO qua TraCI
            // Correct parameter order: vehicleId, routeId, typeId, depart, departLane, departPos, departSpeed
            Vehicle.add(vehicleId, route, type, "now", "first", "base", "max");
            
            System.out.println("Vehicle added successfully!");
            System.out.println("Current vehicle count in SUMO: " + Vehicle.getIDList().size());
            
            // Update will automatically detect and add the new vehicle
            // Update sẽ tự động phát hiện và thêm xe mới
            // No need to manually create the car here
        } catch (Exception e) {
            System.err.println("Failed to add vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
