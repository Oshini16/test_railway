package menu;

import java.util.Scanner;
import model.User;
import railway.RailwaySystem;
import service.AuthenticationService;

public class MainMenu {
    private final RailwaySystem system;
    private final Scanner scanner;
    private final AuthenticationService authentication;

    public MainMenu(RailwaySystem system, Scanner scanner) {
        this.system = system;
        this.scanner = scanner;
        this.authentication = new AuthenticationService(system.getUsers());
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== SRI LANKA RAILWAYS ===");
            System.out.println("1. Admin\n2. User\n3. Exit");
            System.out.print("Choice: ");
            switch (MenuInput.choice(scanner)) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    userAuthentication();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void adminLogin() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Admin password: ");
            if ("admin123".equals(scanner.nextLine())) {
                new AdminMenu(system, scanner).show();
                return;
            }
            System.out.println("Incorrect password.");
        }
    }

    private void userAuthentication() {
        boolean running = true;
        while (running) {
            System.out.println("\n1. Log in\n2. Sign up\n3. Back");
            System.out.print("Choice: ");
            switch (MenuInput.choice(scanner)) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        User user = authentication.login(username, scanner.nextLine());
        if (user == null) {
            System.out.println("Invalid username or password.");
        } else {
            new UserMenu(system, scanner, user).show();
        }
    }

    private void register() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String error = authentication.register(username, scanner.nextLine());
        System.out.println(error == null ? "Registration successful." : error);
    }
}
