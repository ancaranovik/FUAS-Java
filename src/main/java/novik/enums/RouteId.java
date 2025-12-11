package novik.enums;

import java.util.Random;

/**
 * Enum for route IDs in the simulation
 * Enum cho các ID tuyến đường trong mô phỏng
 */
public enum RouteId {
    /** Route 1 */
    ROUTE_1("r1"),
    /** Route 2 */
    ROUTE_2("r2");
    
    private final String id;
    private static final Random RANDOM = new Random();
    
    RouteId(String id) {
        this.id = id;
    }
    
    /**
     * Get the route ID string
     * Lấy chuỗi ID tuyến đường
     * @return Route ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Get a random route
     * Lấy một tuyến đường ngẫu nhiên
     * 
     * @return Randomly selected RouteId
     */
    public static RouteId random() {
        RouteId[] routes = values();
        return routes[RANDOM.nextInt(routes.length)];
    }
}
