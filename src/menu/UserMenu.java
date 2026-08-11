package menu;

import java.util.Scanner;
import model.Booking;
import model.User;
import railway.RailwaySystem;
import service.BookingService;

public class UserMenu {
    private final RailwaySystem system;
    private final Scanner scanner;
    private final User user;
    private final BookingService bookings;

    public UserMenu(RailwaySystem system, Scanner scanner, User user) {
        this.system = system;
        this.scanner = scanner;
        this.user = user;
        this.bookings = new BookingService(
                system.getTrains(),
                system.getActions(),
                system.getWaitingList()
        );
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== USER MENU - " + user.getUsername() + " ===");
            System.out.println("1. View trains\n2. Book ticket\n3. My bookings\n4. Cancel booking");
            System.out.println("5. Profile\n6. Railway network\n7. Recent actions");
            System.out.println("8. My waiting list\n9. Logout");
            System.out.print("Choice: ");
            switch (MenuInput.choice(scanner)) {
                case 1:
                    system.getTrains().displayAllTrains();
                    break;
                case 2:
                    book();
                    break;
                case 3:
                    displayBookings();
                    break;
                case 4:
                    cancel();
                    break;
                case 5:
                    System.out.println("Username: " + user.getUsername());
                    System.out.println("Bookings: " + user.getBookings().size());
                    break;
                case 6:
                    new RailwayNetworkMenu(system, scanner).show();
                    break;
                case 7:
                    system.getActions().displayActionsFor(user.getUsername());
                    break;
                case 8:
                    system.getWaitingList().displayForUser(user);
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void book() {
        System.out.print("Start station: ");
        String startStation = scanner.nextLine().trim();
        System.out.print("Destination: ");
        String destination = scanner.nextLine().trim();

        if (startStation.isEmpty() || destination.isEmpty()) {
            System.out.println("Start station and destination are required.");
            return;
        }
        if (startStation.equalsIgnoreCase(destination)) {
            System.out.println("Start station and destination cannot be the same.");
            return;
        }
        if (!system.getTrains().displayTrainsByRoute(startStation, destination)) {
            return;
        }

        System.out.print("Train ID: ");
        String trainId = scanner.nextLine();
        Booking booking = bookings.book(user, trainId, startStation, destination);
        if (booking != null) {
            System.out.println("Booking successful: " + booking);
        } else if (bookings.joinWaitingList(
                user, trainId, startStation, destination)) {
            System.out.println("Train is full. You were added to the waiting list.");
        } else if (bookings.findTrainForRoute(
                trainId, startStation, destination) == null) {
            System.out.println("Invalid train ID for the selected route.");
        } else if (system.getWaitingList().contains(user, trainId.trim())) {
            System.out.println("You are already waiting for this train.");
        } else {
            System.out.println("Booking could not be completed.");
        }
    }

    private void displayBookings() {
        if (user.getBookings().isEmpty()) {
            System.out.println("You do not have any bookings.");
            return;
        }
        user.getBookings().forEach(System.out::println);
    }

    private void cancel() {
        displayBookings();
        if (user.getBookings().isEmpty()) {
            return;
        }
        System.out.print("Booking ID: ");
        System.out.println(bookings.cancel(user, scanner.nextLine())
                ? "Booking cancelled."
                : "Booking not found.");
    }
}
