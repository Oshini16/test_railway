package structure;


//A custom hash-based Set ADT for unique train IDs.
 // Collisions are handled using separate chaining.

public class TrainIdSet {
    private static class IdNode {
        private final String trainId;
        private IdNode next;

        private IdNode(String trainId, IdNode next) {
            this.trainId = trainId;
            this.next = next;
        }
    }

    private final IdNode[] buckets;
    private int size;

    public TrainIdSet(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Set capacity must be positive.");
        }
        buckets = new IdNode[capacity];
    }

    public boolean add(String trainId) {
        String normalizedId = normalize(trainId);
        if (normalizedId.isEmpty() || contains(normalizedId)) {
            return false;
        }

        int index = hash(normalizedId);
        buckets[index] = new IdNode(normalizedId, buckets[index]);
        size++;
        return true;
    }

    public boolean contains(String trainId) {
        String normalizedId = normalize(trainId);
        if (normalizedId.isEmpty()) {
            return false;
        }

        IdNode current = buckets[hash(normalizedId)];
        while (current != null) {
            if (current.trainId.equals(normalizedId)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean remove(String trainId) {
        String normalizedId = normalize(trainId);
        if (normalizedId.isEmpty()) {
            return false;
        }

        int index = hash(normalizedId);
        IdNode current = buckets[index];
        IdNode previous = null;

        while (current != null) {
            if (current.trainId.equals(normalizedId)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    public void displaySet() {
        System.out.println("\n===== TRAIN ID SET =====");
        for (int index = 0; index < buckets.length; index++) {
            System.out.print("Bucket " + index + ": ");
            IdNode current = buckets[index];
            if (current == null) {
                System.out.println("empty");
                continue;
            }
            while (current != null) {
                System.out.print(current.trainId);
                current = current.next;
                if (current != null) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int hash(String trainId) {
        return Math.floorMod(trainId.hashCode(), buckets.length);
    }

    private String normalize(String trainId) {
        return trainId == null ? "" : trainId.trim().toUpperCase();
    }
}
