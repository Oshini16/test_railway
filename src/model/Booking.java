package model;

public class Booking {
    private static int nextBookingNumber = 1;

    private final String bookingId;
    private final String username;
    private final Train train;

    public Booking(String username, Train train) {
        this.bookingId = String.format("B%04d", nextBookingNumber++);
        this.username = username;
        this.train = train;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Train getTrain() {
        return train;
    }

    @Override
    public String toString() {
        return bookingId + " | " + train.getTrainId() + " - " + train.getTrainName()
                + " | " + train.getStartStation() + " -> " + train.getDestination();
    }
}
