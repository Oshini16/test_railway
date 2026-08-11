package service;

import java.util.Iterator;
import model.Booking;
import model.BookingAction;
import model.Train;
import model.User;
import structure.ActionStack;
import structure.TrainLinkedList;
import structure.WaitingListQueue;
import structure.WaitingListQueue.WaitingEntry;

public class BookingService {
    private final TrainLinkedList trains;
    private final ActionStack actions;
    private final WaitingListQueue waitingList;

    public BookingService(TrainLinkedList trains, ActionStack actions,
                          WaitingListQueue waitingList) {
        this.trains = trains;
        this.actions = actions;
        this.waitingList = waitingList;
    }

    public Booking book(User user, String trainId) {
        Train train = trains.searchTrainById(trainId == null ? "" : trainId.trim());
        return createBooking(user, train);
    }

    public Booking book(User user, String trainId, String startStation,
                        String destination) {
        Train train = findTrainForRoute(trainId, startStation, destination);
        return createBooking(user, train);
    }

    private Booking createBooking(User user, Train train) {
        if (user == null || train == null || !train.bookSeat()) {
            return null;
        }
        Booking booking = new Booking(user.getUsername(), train);
        user.addBooking(booking);
        actions.push(new BookingAction(
                "BOOKED",
                user.getUsername(),
                booking.getBookingId(),
                train.getTrainId()
        ));
        return booking;
    }

    public boolean joinWaitingList(User user, String trainId) {
        Train train = trains.searchTrainById(trainId == null ? "" : trainId.trim());
        return addToWaitingList(user, train);
    }

    public boolean joinWaitingList(User user, String trainId, String startStation,
                                   String destination) {
        Train train = findTrainForRoute(trainId, startStation, destination);
        return addToWaitingList(user, train);
    }

    private boolean addToWaitingList(User user, Train train) {
        return user != null && train != null && train.getAvailableSeats() == 0
                && waitingList.enqueue(user, train);
    }

    public Train findTrainForRoute(String trainId, String startStation,
                                   String destination) {
        return trains.searchTrainByRoute(
                clean(trainId),
                clean(startStation),
                clean(destination)
        );
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }

    public boolean cancel(User user, String bookingId) {
        if (user == null || bookingId == null) {
            return false;
        }
        Iterator<Booking> iterator = user.getBookings().iterator();
        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (booking.getBookingId().equalsIgnoreCase(bookingId.trim())
                    && booking.getTrain().cancelSeat()) {
                iterator.remove();
                actions.push(new BookingAction(
                        "CANCELLED",
                        user.getUsername(),
                        booking.getBookingId(),
                        booking.getTrain().getTrainId()
                ));
                assignSeatToNextUser(booking.getTrain());
                return true;
            }
        }
        return false;
    }

    private void assignSeatToNextUser(Train train) {
        WaitingEntry next = waitingList.dequeueForTrain(train.getTrainId());
        if (next == null || !train.bookSeat()) {
            return;
        }

        Booking booking = new Booking(next.getUser().getUsername(), train);
        next.getUser().addBooking(booking);
        actions.push(new BookingAction(
                "BOOKED_FROM_QUEUE",
                next.getUser().getUsername(),
                booking.getBookingId(),
                train.getTrainId()
        ));
    }
}
