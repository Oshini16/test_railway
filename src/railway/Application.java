package railway;

import java.util.Scanner;
import menu.MainMenu;

public class Application {
    public static void main(String[] args) {
        RailwaySystem system = new RailwaySystem();
        system.initialize();

        try (Scanner scanner = new Scanner(System.in)) {
            new MainMenu(system, scanner).show();
        }
    }
}

