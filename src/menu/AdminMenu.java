package menu;

import java.util.Scanner;
import model.Train;
import model.User;
import railway.RailwaySystem;
import service.TrainService;
import service.UserService;

public class AdminMenu {
    private final RailwaySystem system;
    private final Scanner scanner;
    private final TrainService trains;
    private final UserService users;

    public AdminMenu(RailwaySystem system, Scanner scanner) {
        this.system = system;
        this.scanner = scanner;
        this.trains = new TrainService(
                system.getTrains(),
                system.getTrainIds(),
                system.getTrainBST(),
                system.getTrainAVLTree()
        );
        this.users = new UserService(system.getUsers());
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Manage trains\n2. Manage users\n3. Railway network\n4. Report\n5. Logout");
            System.out.print("Choice: ");
            switch (MenuInput.choice(scanner)) {
                case 1:
                    manageTrains();
                    break;
                case 2:
                    manageUsers();
                    break;
                case 3:
                    new RailwayNetworkMenu(system, scanner).show();
                    break;
                case 4:
                    report();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageTrains() {
        boolean running = true;
        while (running) {
            System.out.println("\n1. Add\n2. View all\n3. Search\n4. Delete");
            System.out.println("5. Count\n6. Display train ID set");
            System.out.println("7. BST operations");
            System.out.println("8. AVL operations");
            System.out.println("9. Back");
            System.out.print("Choice: ");
            switch (MenuInput.choice(scanner)) {
                case 1:
                    addTrain();
                    break;
                case 2:
                    system.getTrains().displayAllTrains();
                    break;
                case 3:
                    showTrain();
                    break;
                case 4:
                    deleteTrain();
                    break;
                case 5:
                    System.out.println("Total trains: " + system.getTrains().getSize());
                    break;
                case 6:
                    system.getTrainIds().displaySet();
                    break;
                case 7:
                    manageBST();
                    break;
                case 8:
                    manageAVL();
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageBST() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== BST OPERATIONS ===");
            System.out.println("1. Display inorder");
            System.out.println("2. Display preorder");
            System.out.println("3. Display postorder");
            System.out.println("4. Back");
            System.out.print("Choice: ");

            switch (MenuInput.choice(scanner)) {
                case 1:
                    System.out.println("\n===== BST INORDER =====");
                    system.getTrainBST().displayInOrder();
                    break;
                case 2:
                    System.out.println("\n===== BST PREORDER =====");
                    system.getTrainBST().displayPreOrder();
                    break;
                case 3:
                    System.out.println("\n===== BST POSTORDER =====");
                    system.getTrainBST().displayPostOrder();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageAVL() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== AVL OPERATIONS ===");
            System.out.println("1. Display inorder");
            System.out.println("2. Display preorder");
            System.out.println("3. Display postorder");
            System.out.println("4. Back");
            System.out.print("Choice: ");

            switch (MenuInput.choice(scanner)) {
                case 1:
                    System.out.println("\n===== AVL INORDER =====");
                    system.getTrainAVLTree().displayInOrder();
                    break;
                case 2:
                    System.out.println("\n===== AVL PREORDER =====");
                    system.getTrainAVLTree().displayPreOrder();
                    break;
                case 3:
                    System.out.println("\n===== AVL POSTORDER =====");
                    system.getTrainAVLTree().displayPostOrder();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addTrain() {
        System.out.print("Train ID: ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Start station: ");
        String start = scanner.nextLine();
        System.out.print("Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Total seats: ");
        int seats = MenuInput.choice(scanner);
        String error = trains.add(id, name, start, destination, seats);
        if (error != null) {
            System.out.println(error);
        }
    }

    private void showTrain() {
        System.out.print("Train ID: ");
        Train train = trains.find(scanner.nextLine());
        if (train == null) {
            System.out.println("Train not found.");
        } else {
            train.displayTrain();
        }
    }

    private void deleteTrain() {
        System.out.print("Train ID: ");
        System.out.println(trains.delete(scanner.nextLine()) ? "Train deleted." : "Train not found.");
    }

    private void manageUsers() {
        System.out.println("\n1. Display table\n2. Search\n3. Delete\n4. Count");
        System.out.print("Choice: ");
        switch (MenuInput.choice(scanner)) {
            case 1:
                system.getUsers().displayTable();
                break;
            case 2:
                System.out.print("Username: ");
                User user = users.find(scanner.nextLine());
                System.out.println(user == null ? "User not found."
                        : user.getUsername() + " | bookings: " + user.getBookings().size());
                break;
            case 3:
                System.out.print("Username: ");
                System.out.println(users.delete(scanner.nextLine()) ? "User deleted." : "User not found.");
                break;
            case 4:
                System.out.println("Registered users: " + system.getUsers().size());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void report() {
        System.out.println("\n=== SYSTEM REPORT ===");
        System.out.println("Total trains: " + system.getTrains().getSize());
        System.out.println("Registered users: " + system.getUsers().size());
        System.out.println("Stations: " + system.getStations().getSize());
    }
}
