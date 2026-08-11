package structure;

import model.BookingAction;

/** A custom linked stack that stores recent booking-related actions. */
public class ActionStack {
    private static class ActionNode {
        private final BookingAction action;
        private ActionNode next;

        private ActionNode(BookingAction action) {
            this.action = action;
        }
    }

    private ActionNode top;
    private int size;

    public void push(BookingAction action) {
        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null.");
        }
        size++;

        ActionNode newNode = new ActionNode(action);
        newNode.next = top;
        top = newNode;
    }

    public BookingAction pop() {
        if (isEmpty()) {
            return null;
        }

        BookingAction action = top.action;
        top = top.next;
        size--;
        return action;
    }

    public BookingAction peek() {
        return isEmpty() ? null : top.action;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    public void displayActionsFor(String username) {
        ActionNode current = top;
        boolean found = false;

        System.out.println("\n===== RECENT ACTIONS =====");
        while (current != null) {
            if (current.action.getUsername().equalsIgnoreCase(username)) {
                System.out.println(current.action);
                found = true;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("No recent actions.");
        }
    }
}
