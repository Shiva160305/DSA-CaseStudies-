import java.util.*;

/**
 * CO1 Case Study: Blood Bank Priority Unit Indexing using AVL Tree
 * Inserts emergency priority codes, searches existing/missing keys,
 * and reports predecessor/successor for a missing key.
 */
public class CO1_BloodBankAVL {
    static class Node {
        int key, height;
        Node left, right;

        Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

    static class AVLTree {
        Node root;

        int height(Node node) {
            return node == null ? 0 : node.height;
        }

        int balance(Node node) {
            return node == null ? 0 : height(node.left) - height(node.right);
        }

        void updateHeight(Node node) {
            if (node != null) {
                node.height = 1 + Math.max(height(node.left), height(node.right));
            }
        }

        Node rotateRight(Node y) {
            Node x = y.left;
            Node t2 = x.right;

            x.right = y;
            y.left = t2;

            updateHeight(y);
            updateHeight(x);

            return x;
        }

        Node rotateLeft(Node x) {
            Node y = x.right;
            Node t2 = y.left;

            y.left = x;
            x.right = t2;

            updateHeight(x);
            updateHeight(y);

            return y;
        }

        void insert(int key) {
            root = insert(root, key);
        }

        Node insert(Node node, int key) {
            if (node == null) return new Node(key);

            if (key < node.key) node.left = insert(node.left, key);
            else if (key > node.key) node.right = insert(node.right, key);
            else return node;

            updateHeight(node);
            int bf = balance(node);

            if (bf > 1 && key < node.left.key) return rotateRight(node);       // LL
            if (bf < -1 && key > node.right.key) return rotateLeft(node);      // RR
            if (bf > 1 && key > node.left.key) {                               // LR
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
            if (bf < -1 && key < node.right.key) {                             // RL
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }

            return node;
        }

        boolean search(int key) {
            return search(root, key);
        }

        boolean search(Node node, int key) {
            if (node == null) return false;
            if (node.key == key) return true;
            return key < node.key ? search(node.left, key) : search(node.right, key);
        }

        String searchPath(int key) {
            StringBuilder path = new StringBuilder();
            Node current = root;

            while (current != null) {
                if (path.length() > 0) path.append(" -> ");
                path.append(current.key);

                if (key == current.key) return path + " (found)";
                current = key < current.key ? current.left : current.right;
            }

            return path + " -> null (missing)";
        }

        Integer predecessor(int key) {
            Node current = root;
            Integer pred = null;

            while (current != null) {
                if (key > current.key) {
                    pred = current.key;
                    current = current.right;
                } else {
                    current = current.left;
                }
            }

            return pred;
        }

        Integer successor(int key) {
            Node current = root;
            Integer succ = null;

            while (current != null) {
                if (key < current.key) {
                    succ = current.key;
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            return succ;
        }

        void inorder() {
            inorder(root);
            System.out.println();
        }

        void inorder(Node node) {
            if (node == null) return;
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }

        int heightInEdges() {
            return height(root) - 1;
        }
    }

    public static void main(String[] args) {
        int[] codes = {38, 22, 58, 14, 31, 46, 72, 9, 18, 27, 34, 42, 51, 64, 79};
        AVLTree tree = new AVLTree();

        for (int code : codes) {
            tree.insert(code);
        }

        System.out.println("CO1 - Blood Bank AVL Priority Index");
        System.out.println("-----------------------------------");
        System.out.print("Inorder Traversal: ");
        tree.inorder();
        System.out.println("Root Key: " + tree.root.key);
        System.out.println("Height in Edges: " + tree.heightInEdges());
        System.out.println("Root Balance Factor: " + tree.balance(tree.root));

        int key1 = 51;
        int key2 = 65;

        System.out.println("\nSearch Path for " + key1 + ": " + tree.searchPath(key1));
        System.out.println("Search Path for " + key2 + ": " + tree.searchPath(key2));
        System.out.println("Predecessor of " + key2 + ": " + tree.predecessor(key2));
        System.out.println("Successor of " + key2 + ": " + tree.successor(key2));

        System.out.println("\nTime Complexity: Search/Insert = O(log n)");
    }
}
