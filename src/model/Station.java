package model;


public class Station {
    private final String stationId;
    private final String stationName;

    public Station(String stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public String toString() {
        return stationId + " - " + stationName;
    }
}
