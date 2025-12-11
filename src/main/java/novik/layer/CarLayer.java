package novik.layer;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import novik.model.Car;
import novik.util.MapUtil;
import org.eclipse.sumo.libtraci.TraCIPosition;
import org.eclipse.sumo.libtraci.Vehicle;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Layer responsible for displaying and updating cars
 * Lớp chịu trách nhiệm hiển thị và cập nhật xe
 */
public class CarLayer extends Layer {
    private final Map<String, Car> cars;
    private final Map<String, Image> carImages;
    private static final AtomicLong vehicleCounter = new AtomicLong(0);

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
        URL resource = getClass().getResource("/" + filename);
        if (resource == null) {
            throw new IllegalArgumentException("Image not found: " + filename);
        }
        return new Image(resource.toExternalForm());
    }

    /**
     * Create a new car and add it to the layer
     * Tạo xe mới và thêm vào lớp
     */
    private Car createCar(String carId, String type) {
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
        TraCIPosition sumoPos = Vehicle.getPosition(carId, false);
        Point2D javaPos = MapUtil.worldToScreen(new Point2D(sumoPos.getX(), sumoPos.getY()));
        double angle = Vehicle.getAngle(carId);
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
        List<String> activeIds = Vehicle.getIDList();
        Set<String> activeCarIds = new HashSet<>(activeIds);
        Set<String> newCarIds = new HashSet<>(activeCarIds);

        // Update existing cars and mark inactive ones for removal
        List<Car> carsToRemove = new ArrayList<>();
        for (Map.Entry<String, Car> entry : cars.entrySet()) {
            String carId = entry.getKey();
            Car car = entry.getValue();

            if (activeCarIds.contains(carId)) {
                newCarIds.remove(carId);
                updateCarFromSumo(car);
            } else {
                carsToRemove.add(car);
            }
        }
        
        // Remove inactive cars
        carsToRemove.forEach(this::removeCar);

        // Create and initialize new cars
        for (String carId : newCarIds) {
            String type = Vehicle.getTypeID(carId);
            Car car = createCar(carId, type);
            updateCarFromSumo(car);
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
     * 
     * @return Number of cars
     */
    public int getCarCount() {
        return cars.size();
    }

    /**
     * Get a car by ID
     * Lấy xe theo ID
     * 
     * @param carId Vehicle ID
     * @return Car object or null if not found
     */
    public Car getCar(String carId) {
        return cars.get(carId);
    }

    /**
     * Get all cars
     * Lấy tất cả xe
     * 
     * @return Collection of all cars
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
        String vehicleId = "veh_" + vehicleCounter.incrementAndGet();
        
        try {
            Vehicle.add(vehicleId, route, type, "now", "first", "base", "max");
        } catch (Exception e) {
            System.err.println("Failed to add vehicle: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
