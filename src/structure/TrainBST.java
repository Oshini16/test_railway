package structure;

import model.Train;

public class TrainBST {
    private Node root;

    private static class Node {
        Train train;
        Node left;
        Node right;

        Node(Train train) {
            this.train = train;
        }
    }

    public void insert(Train train) {
        if (train != null) {
            root = insert(root, train);
        }
    }

    private Node insert(Node node, Train train) {
        if (node == null) {
            return new Node(train);
        }

        int result = train.getTrainId()
                .compareToIgnoreCase(node.train.getTrainId());

        if (result < 0) {
            node.left = insert(node.left, train);
        } else if (result > 0) {
            node.right = insert(node.right, train);
        }

        return node;
    }

    public Train search(String trainId) {
        if (trainId == null) {
            return null;
        }

        Node current = root;

        while (current != null) {
            int result = trainId.compareToIgnoreCase(
                    current.train.getTrainId());

            if (result == 0) {
                return current.train;
            }

            current = result < 0 ? current.left : current.right;
        }

        return null;
    }

    public boolean delete(String trainId) {
        if (search(trainId) == null) {
            return false;
        }
        root = delete(root, trainId);
        return true;
    }

    private Node delete(Node node, String trainId) {
        if (node == null) {
            return null;
        }

        int result = trainId.compareToIgnoreCase(node.train.getTrainId());
        if (result < 0) {
            node.left = delete(node.left, trainId);
        } else if (result > 0) {
            node.right = delete(node.right, trainId);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            Node successor = minimum(node.right);
            node.train = successor.train;
            node.right = delete(node.right, successor.train.getTrainId());
        }
        return node;
    }

    private Node minimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void displayInOrder() {
        if (root == null) {
            System.out.println("BST is empty.");
            return;
        }

        displayInOrder(root);
    }

    private void displayInOrder(Node node) {
        if (node == null) {
            return;
        }

        displayInOrder(node.left);
        System.out.println(node.train);
        displayInOrder(node.right);
    }

    public void displayPreOrder() {
        if (root == null) {
            System.out.println("BST is empty.");
            return;
        }
        displayPreOrder(root);
    }

    private void displayPreOrder(Node node) {
        if (node == null) {
            return;
        }

        System.out.println(node.train);
        displayPreOrder(node.left);
        displayPreOrder(node.right);
    }

    public void displayPostOrder() {
        if (root == null) {
            System.out.println("BST is empty.");
            return;
        }
        displayPostOrder(root);
    }

    private void displayPostOrder(Node node) {
        if (node == null) {
            return;
        }

        displayPostOrder(node.left);
        displayPostOrder(node.right);
        System.out.println(node.train);
    }

    /**
     * Prints the tree sideways. Right children appear above their parents and
     * left children appear below them.
     */
    public void displayShape() {
        System.out.println("\n===== BST SHAPE =====");
        if (root == null) {
            System.out.println("BST is empty.");
            return;
        }

        displayShape(root, 0);
        System.out.println("Root: " + root.train.getTrainId());
        System.out.println("Height: " + height(root));
    }

    private void displayShape(Node node, int level) {
        if (node == null) {
            return;
        }

        displayShape(node.right, level + 1);
        System.out.println("    ".repeat(level) + node.train.getTrainId());
        displayShape(node.left, level + 1);
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
