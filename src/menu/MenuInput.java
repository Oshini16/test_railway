package menu;

import java.util.Scanner;

final class MenuInput {
    private MenuInput() {
    }

    static int choice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
