package structure;

import model.Train;

public class TrainAVLTree {
    private Node root;

    private static class Node {
        Train train;
        Node left;
        Node right;
        int height = 1;

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
        } else {
            return node;
        }

        updateHeight(node);
        int balance = balance(node);

        // Left-left and left-right cases
        if (balance > 1) {
            if (train.getTrainId().compareToIgnoreCase(
                    node.left.train.getTrainId()) > 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        // Right-right and right-left cases
        if (balance < -1) {
            if (train.getTrainId().compareToIgnoreCase(
                    node.right.train.getTrainId()) < 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
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

        updateHeight(node);
        return rebalance(node);
    }

    private Node minimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node rebalance(Node node) {
        int nodeBalance = balance(node);

        if (nodeBalance > 1) {
            if (balance(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (nodeBalance < -1) {
            if (balance(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node oldRoot) {
        Node newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;

        updateHeight(oldRoot);
        updateHeight(newRoot);
        return newRoot;
    }

    private Node rotateLeft(Node oldRoot) {
        Node newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;

        updateHeight(oldRoot);
        updateHeight(newRoot);
        return newRoot;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(
                height(node.left), height(node.right));
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int balance(Node node) {
        return height(node.left) - height(node.right);
    }

    public void displayInOrder() {
        if (root == null) {
            System.out.println("AVL tree is empty.");
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
            System.out.println("AVL tree is empty.");
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
            System.out.println("AVL tree is empty.");
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
        System.out.println("\n===== AVL TREE SHAPE =====");
        if (root == null) {
            System.out.println("AVL tree is empty.");
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
}
