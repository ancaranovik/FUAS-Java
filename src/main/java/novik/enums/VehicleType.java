package novik.enums;

import java.util.Random;

/**
 * Enum for vehicle types in the simulation
 * Enum cho các loại xe trong mô phỏng
 */
public enum VehicleType {
    /** Car vehicle type */
    CAR("car"),
    /** Bus vehicle type */
    BUS("bus"),
    /** Truck vehicle type */
    TRUCK("truck");
    
    private final String id;
    private static final Random RANDOM = new Random();
    
    VehicleType(String id) {
        this.id = id;
    }
    
    /**
     * Get the vehicle type ID string
     * Lấy chuỗi ID loại xe
     * 
     * @return Vehicle type ID (e.g., "car", "bus", "truck")
     */
    public String getId() {
        return id;
    }
    
    /**
     * Get a random vehicle type
     * Lấy một loại xe ngẫu nhiên
     * 
     * @return Randomly selected VehicleType
     */
    public static VehicleType random() {
        VehicleType[] types = values();
        return types[RANDOM.nextInt(types.length)];
    }
    
    /**
     * Get VehicleType from string ID
     * Lấy VehicleType từ string ID
     * 
     * @param id Vehicle type ID string (e.g., "car", "bus", "truck")
     * @return Matching VehicleType, defaults to CAR if not found
     */
    public static VehicleType fromId(String id) {
        for (VehicleType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return CAR; // Default
    }
}
