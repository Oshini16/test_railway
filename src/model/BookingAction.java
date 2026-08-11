package model;

//Describes a booking-related action stored in the recent-action stack.

public class BookingAction {
    private final String actionType;
    private final String username;
    private final String bookingId;
    private final String trainId;

    public BookingAction(String actionType, String username, String bookingId, String trainId) {
        this.actionType = actionType;
        this.username = username;
        this.bookingId = bookingId;
        this.trainId = trainId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getUsername() {
        return username;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getTrainId() {
        return trainId;
    }

    @Override
    public String toString() {
        return actionType + " | User: " + username+ " | Booking: " + bookingId + " | Train: " + trainId;
    }
}
