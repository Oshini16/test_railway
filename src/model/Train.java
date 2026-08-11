package model;

public class Train {
    private String trainId;
    private String trainName;
    private String startStation;
    private String destination;
    private int totalSeats;
    private int availableSeats;

    public Train(String trainId, String trainName, String startStation, String destination, int totalSeats, int availableSeats) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.startStation = startStation;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    // Attempts to book one seat. Returns true if successful, false if train is full.
    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    // Frees up one seat, but never goes above totalSeats.
    public boolean cancelSeat() {
        if (availableSeats < totalSeats) {
            availableSeats++;
            return true;
        }
        return false;
    }

    public void displayTrain() {
        System.out.println("--------------------------------");
        System.out.println("Train ID : " + trainId);
        System.out.println("Train Name : " + trainName);
        System.out.println("Start : " + startStation);
        System.out.println("Destination : " + destination);
        System.out.println("Seats : " + availableSeats + "/" + totalSeats);
    }

    @Override
    public String toString() {
        return trainId + " | " + trainName + " | " + startStation + " -> " + destination
                + " | Seats: " + availableSeats + "/" + totalSeats;
    }
}
