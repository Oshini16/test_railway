package menu;

import java.util.ArrayList;
import java.util.Scanner;
import railway.RailwaySystem;

public class RailwayNetworkMenu {
    private final RailwaySystem system;
    private final Scanner scanner;

    public RailwayNetworkMenu(RailwaySystem system, Scanner scanner) {
        this.system = system;
        this.scanner = scanner;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== RAILWAY NETWORK ===");
            System.out.println("1. Stations\n2. Connections\n3. BFS\n4. DFS\n5. Back");
            System.out.print("Choice: ");
            switch (MenuInput.choice(scanner)) {
                case 1:
                    system.getStations().displayStations();
                    break;
                case 2:
                    system.getGraph().displayGraph();
                    break;
                case 3:
                    traverse(true);
                    break;
                case 4:
                    traverse(false);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void traverse(boolean breadthFirst) {
        System.out.print("Starting station ID: ");
        String id = scanner.nextLine();
        ArrayList<String> result = breadthFirst
                ? system.getGraph().bfs(id)
                : system.getGraph().dfs(id);
        System.out.println(result.isEmpty()
                ? "Station not found."
                : String.join(" -> ", result));
    }
}
