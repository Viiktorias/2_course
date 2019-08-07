import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("input.txt"));
             PrintStream printStream = new PrintStream(("output.txt"))) {
            BinaryTree binaryTree = new BinaryTree();
            int key = scanner.nextInt();
            while (scanner.hasNext()) {
                binaryTree.add(scanner.nextInt());
            }
            binaryTree.deleteRight(key);
            binaryTree.roundTLR(printStream);
        } catch (FileNotFoundException e) {

        }
    }
}

class BinaryTree {
    private Node root;

    public void roundTLR(PrintStream printStream) {
        if (root != null) {
            Deque<Node> deque = new ArrayDeque<>();
            StringBuilder stringBuilder = new StringBuilder();
            deque.push(root);
            Node temp;
            while (!deque.isEmpty()) {
                temp = deque.pop();
                stringBuilder.append(temp.key).append("\r\n");
                if (temp.right != null) {
                    deque.push(temp.right);
                }
                if (temp.left != null) {
                    deque.push(temp.left);
                }
            }
            printStream.print(stringBuilder.toString());
        }
    }

    public void add(int element) {
        Node prev = null;
        Node cur = root;
        while (cur != null) {
            prev = cur;
            if (element < cur.key) {
                cur = cur.left;
            } else if (element > cur.key) {
                cur = cur.right;
            } else {
                return;
            }
        }
        Node temp = new Node(element);
        if (prev == null) {
            root = temp;
        } else if (element < prev.key) {
            prev.left = temp;
        } else if (element > prev.key) {
            prev.right = temp;
        }
    }

    public void deleteRight(int element) {
        if (root == null) {
            return;
        }
        Node prev = null;
        Node cur = root;
        while ((cur != null) && (cur.key != element)) {
            prev = cur;
            if (element < cur.key) {
                cur = cur.left;
            } else if (element > cur.key) {
                cur = cur.right;
            }
        }

        if (cur != null) {
            if ((cur.left == null) && (cur.right == null)) {
                replace(prev, cur, null);
            } else if (cur.left != null) {
                if (cur.right == null) {
                    replace(prev, cur, cur.left);
                } else {
                    Node temp = cur.right;
                    if (temp.left == null) {
                        temp.left = cur.left;
                        replace(prev, cur, temp);
                    } else {
                        while (temp.left.left != null)
                            temp = temp.left;
                        Node min = temp.left;
                        temp.left = min.right;
                        min.left = cur.left;
                        min.right = cur.right;
                        replace(prev, cur, min);
                    }
                }
            } else {
                replace(prev, cur, cur.right);
            }
        }
    }

    private void replace(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.key > oldChild.key) {
            parent.left = newChild;
        } else parent.right = newChild;
    }

    static class Node {
        int key;
        Node left;
        Node right;

        Node(int key) {
            this.key = key;
        }
    }
}