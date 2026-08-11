package structure;

import model.User;

public class UserHashTable {

    /*
     * A node stores one username-user pair.
     * The next reference is used for collision handling.
     */
    private static class UserNode {
        private String key;
        private User user;
        private UserNode next;

        public UserNode(String key, User user) {
            this.key = key;
            this.user = user;
            this.next = null;
        }
    }

    private final UserNode[] buckets;
    private int size;

    public UserHashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Hash table capacity must be greater than zero."
            );
        }

        buckets = new UserNode[capacity];
        size = 0;
    }


    private int hash(String key) {
        if (key == null) {
            throw new IllegalArgumentException(
                    "Username cannot be null."
            );
        }

        return Math.floorMod(
                key.toLowerCase().hashCode(),
                buckets.length
        );
    }


     // Inserts a user into the hash table.

     //If the username already exists, the existing
     //user value is updated.
    public void put(String key, User user) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Username cannot be empty."
            );
        }

        if (user == null) {
            throw new IllegalArgumentException(
                    "User cannot be null."
            );
        }

        String normalizedKey = key.trim();
        int index = hash(normalizedKey);

        UserNode current = buckets[index];

        // Check whether the username already exists
        while (current != null) {
            if (current.key.equalsIgnoreCase(normalizedKey)) {
                current.user = user;
                return;
            }

            current = current.next;
        }

        // Collision handling using separate chaining
        UserNode newNode =
                new UserNode(normalizedKey, user);

        newNode.next = buckets[index];
        buckets[index] = newNode;

        size++;
    }


     // Searches for and returns a user.
     // Returns null if the username does not exist.

    public User get(String key) {
        if (key == null || key.trim().isEmpty()) {
            return null;
        }

        String normalizedKey = key.trim();
        int index = hash(normalizedKey);

        UserNode current = buckets[index];

        while (current != null) {
            if (current.key.equalsIgnoreCase(normalizedKey)) {
                return current.user;
            }

            current = current.next;
        }

        return null;
    }


    // Returns true when a username is already stored.

    public boolean containsKey(String key) {
        return get(key) != null;
    }


     // Removes a user by username.

    public boolean remove(String key) {
        if (key == null || key.trim().isEmpty()) {
            return false;
        }

        String normalizedKey = key.trim();
        int index = hash(normalizedKey);

        UserNode current = buckets[index];
        UserNode previous = null;

        while (current != null) {
            if (current.key.equalsIgnoreCase(normalizedKey)) {

                if (previous == null) {
                    // Remove first node in bucket
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

    /*
     * Displays all buckets and collision chains.
     */
    public void displayTable() {
        System.out.println(
                "\n========== USER HASH TABLE =========="
        );

        for (int i = 0; i < buckets.length; i++) {
            System.out.print("Bucket " + i + ": ");

            UserNode current = buckets[i];

            if (current == null) {
                System.out.println("empty");
                continue;
            }

            while (current != null) {
                System.out.print(
                        "[" + current.key + "]"
                );

                if (current.next != null) {
                    System.out.print(" -> ");
                }

                current = current.next;
            }

            System.out.println();
        }

        System.out.println("Registered users: " + size);
        System.out.println(
                "====================================="
        );
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return buckets.length;
    }
}
