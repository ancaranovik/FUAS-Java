package novik;

import org.eclipse.sumo.libtraci.Simulation;
import org.eclipse.sumo.libtraci.StringVector;
import java.nio.file.Path;

/**
 * Bridge class for communication with SUMO simulation
 * Lớp cầu nối để giao tiếp với mô phỏng SUMO
 */
public class SumoBridge {
    private static boolean serverActive = false;

    private SumoBridge() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Check if SUMO server is active
     * Kiểm tra xem SUMO server có đang chạy không
     * 
     * @return true if SUMO server is running, false otherwise
     */
    public static boolean isServerActive() {
        return serverActive;
    }

    /**
     * Start SUMO simulation with configuration file
     * Khởi động mô phỏng SUMO với file cấu hình
     * 
     * @param sumoCfgPath Path to SUMO configuration file (.sumocfg)
     */
    static void startSUMO(Path sumoCfgPath) {
        Simulation.preloadLibraries();
        StringVector args = new StringVector(new String[] {
                "sumo",
                "-c", sumoCfgPath.toAbsolutePath().toString(),
                "--start",
                "--no-step-log", "true",
                "--time-to-teleport", "-1"
        });
        Simulation.start(args);
        serverActive = true;
    }

    /**
     * Stop SUMO simulation
     * Dừng mô phỏng SUMO
     */
    static void stopSUMO() {
        Simulation.close();
        serverActive = false;
    }

    /**
     * Advance simulation by one step
     * Tiến mô phỏng thêm một bước
     */
    static void step() {
        Simulation.step();
    }
}
