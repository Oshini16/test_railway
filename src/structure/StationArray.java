package structure;
// The railway station is fixe
public class StationArray {
    private final String[] stations;
    private int size;

    public StationArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Station capacity must be positive.");
        }
        stations = new String[capacity];
    }

    public boolean addStation(String stationName) {
        if (stationName == null || stationName.trim().isEmpty()
                || isFull() || searchStation(stationName) != null) {
            return false;
        }
        stations[size++] = stationName.trim();
        return true;
    }

    public String searchStation(String stationName) {
        if (stationName == null) {
            return null;
        }
        for (int index = 0; index < size; index++) {
            if (stations[index].equalsIgnoreCase(stationName.trim())) {
                return stations[index];
            }
        }
        return null;
    }

    public void displayStations() {
        if (size == 0) {
            System.out.println("No stations available.");
            return;
        }
        System.out.println("\n===== Fixed Station Array =====");
        for (int index = 0; index < size; index++) {
            System.out.println((index + 1) + ". " + stations[index]);
        }
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return stations.length;
    }

    public boolean isFull() {
        return size == stations.length;
    }
}
