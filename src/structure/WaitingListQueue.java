package structure;

import model.Train;
import model.User;

/** A custom FIFO queue for users waiting for seats on full trains. */
public class WaitingListQueue {
    public static class WaitingEntry {
        private final User user;
        private final Train train;

        private WaitingEntry(User user, Train train) {
            this.user = user;
            this.train = train;
        }

        public User getUser() {
            return user;
        }

        public Train getTrain() {
            return train;
        }

        @Override
        public String toString() {
            return user.getUsername() + " | " + train.getTrainId()
                    + " - " + train.getTrainName();
        }
    }

    private static class QueueNode {
        private final WaitingEntry entry;
        private QueueNode next;

        private QueueNode(WaitingEntry entry) {
            this.entry = entry;
        }
    }

    private QueueNode front;
    private QueueNode rear;
    private int size;

    public boolean enqueue(User user, Train train) {
        if (user == null || train == null || contains(user, train.getTrainId())) {
            return false;
        }

        QueueNode newNode = new QueueNode(new WaitingEntry(user, train));
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
        return true;
    }

    /**
     * Removes the earliest queued user for the specified train.
     */
    public WaitingEntry dequeueForTrain(String trainId) {
        QueueNode current = front;
        QueueNode previous = null;

        while (current != null) {
            if (current.entry.getTrain().getTrainId().equalsIgnoreCase(trainId)) {
                if (previous == null) {
                    front = current.next;
                } else {
                    previous.next = current.next;
                }
                if (current == rear) {
                    rear = previous;
                }
                size--;
                return current.entry;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    public WaitingEntry dequeue() {
        if (isEmpty()) {
            return null;
        }

        WaitingEntry entry = front.entry;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return entry;
    }

    public WaitingEntry peek() {
        return isEmpty() ? null : front.entry;
    }

    public boolean contains(User user, String trainId) {
        QueueNode current = front;
        while (current != null) {
            if (current.entry.getUser() == user
                    && current.entry.getTrain().getTrainId().equalsIgnoreCase(trainId)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void displayForUser(User user) {
        QueueNode current = front;
        int position = 0;
        boolean found = false;

        System.out.println("\n===== MY WAITING LIST =====");
        while (current != null) {
            if (current.entry.getUser() == user) {
                position++;
                System.out.println(position + ". " + current.entry);
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("You are not waiting for any train.");
        }
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}
