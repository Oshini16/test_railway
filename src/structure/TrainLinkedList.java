package structure;

import model.Train;

class TrainNode {
    Train train;
    TrainNode next;

    public TrainNode(Train train) {
        this.train = train;
        this.next = null;
    }
}

public class TrainLinkedList {
    private TrainNode head;
    private int size;

    public TrainLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Add train to the end of the custom linked list
    public void addTrain(Train train) {
        TrainNode newNode = new TrainNode(train);
        if (head == null) {
            head = newNode;
        } else {
            TrainNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        System.out.println("Train " + train.getTrainId() + " (" + train.getTrainName() + ") added successfully!");
    }

    // Display all trains in the system
    public void displayAllTrains() {
        if (head == null) {
            System.out.println("No trains available in the system.");
            return;
        }
        System.out.println("\n=================== LIST OF ALL TRAINS ===================");
        TrainNode current = head;
        while (current != null) {
            current.train.displayTrain();
            current = current.next;
        }
        System.out.println("==========================================================");
    }

    // Display only trains that travel between the requested stations.
    public boolean displayTrainsByRoute(String startStation, String destination) {
        TrainNode current = head;
        boolean found = false;

        System.out.println("\n============= AVAILABLE TRAINS =============");
        while (current != null) {
            Train train = current.train;
            if (train.getStartStation().equalsIgnoreCase(startStation)
                    && train.getDestination().equalsIgnoreCase(destination)) {
                train.displayTrain();
                found = true;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("No trains found for this route.");
        }
        System.out.println("============================================");
        return found;
    }

    // Search by ID while also ensuring that the train belongs to the selected route.
    public Train searchTrainByRoute(String trainId, String startStation,
                                    String destination) {
        TrainNode current = head;
        while (current != null) {
            Train train = current.train;
            if (train.getTrainId().equalsIgnoreCase(trainId)
                    && train.getStartStation().equalsIgnoreCase(startStation)
                    && train.getDestination().equalsIgnoreCase(destination)) {
                return train;
            }
            current = current.next;
        }
        return null;
    }

    // Search train by Train ID (Linear Search implementation)
    public Train searchTrainById(String trainId) {
        TrainNode current = head;
        while (current != null) {
            if (current.train.getTrainId().equalsIgnoreCase(trainId)) {
                return current.train;
            }
            current = current.next;
        }
        return null;
    }

    // Delete train by Train ID
    public boolean deleteTrainById(String trainId) {
        if (head == null) {
            return false;
        }

        // If head node contains the target train
        if (head.train.getTrainId().equalsIgnoreCase(trainId)) {
            head = head.next;
            size--;
            return true;
        }

        TrainNode current = head;
        while (current.next != null) {
            if (current.next.train.getTrainId().equalsIgnoreCase(trainId)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
