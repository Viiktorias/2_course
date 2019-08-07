import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public class Solution {

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("in.txt"));
             PrintStream printStream = new PrintStream(("out.txt"))) {
            BinaryTree binaryTree = new BinaryTree(Integer.parseInt(bufferedReader.readLine()));
            String line = bufferedReader.readLine();
            while (line != null) {
                binaryTree.add(Integer.parseInt(line));
                line = bufferedReader.readLine();
            }
            binaryTree.calculate();
            binaryTree.deleteLRT();
            binaryTree.roundTLR(printStream);
        } catch (IOException e) {

        }
    }

    private static class BinaryTree {
        private Node root;
        private int greatestHalfway;
        private long maxCol;

        public BinaryTree(int rootKey) {
            root = new Node(rootKey);
        }

        void roundTLR(PrintStream printStream) {
            if (root != null) {
                Queue<Node> stack = Collections.asLifoQueue(new ArrayDeque<>());
                StringBuilder stringBuilder = new StringBuilder();
                stack.add(root);
                Node temp;
                while (!stack.isEmpty()) {
                    temp = stack.remove();
                    stringBuilder.append(temp.key).append("\r\n");
                    if (temp.right != null) {
                        stack.add(temp.right);
                    }
                    if (temp.left != null) {
                        stack.add(temp.left);
                    }
                }
                printStream.print(stringBuilder.toString());
            }
        }

        void add(int element) {
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
            if (element < prev.key) {
                prev.left = temp;
            } else {
                prev.right = temp;
            }
        }

        void deleteLRT() {
            deleteLRT(null, root);
        }

        private void deleteLRT(Node prev, Node cur) {
            if (cur != null) {
                deleteLRT(cur, cur.left);
                deleteLRT(cur, cur.right);
                if (cur.colFromParent + cur.colRoot == maxCol) {
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
        }

        /*private void deleteRight(int element) {
            if (root == null) {
                return;
            }
            Node prev = null;
            Node cur = root;
            while ((cur != null) && (cur.key != element)) {
                prev = cur;
                if (element < cur.key) {
                    cur = cur.left;
                } else {
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
        }*/

        private void replace(Node parent, Node oldChild, Node newChild) {
            if (parent == null) {
                root = newChild;
            } else if (parent.key > oldChild.key) {
                parent.left = newChild;
            } else parent.right = newChild;
        }

        void calculate() {
            calculateHL(root);
            Queue<Node> stack = Collections.asLifoQueue(new ArrayDeque<>());
            stack.add(root);
            Node temp;
            while (!stack.isEmpty()) {
                temp = stack.remove();
                if (temp.maxHalfway == greatestHalfway) {
                    temp.colRoot = 1;
                    if (temp.right != null) {
                        temp.colRoot *= temp.right.leaves;
                        stack.add(temp.right);
                        if (temp.left != null) {
                            temp.colRoot *= temp.left.leaves;
                            temp.right.colFromParent = temp.left.colFromParent = temp.colRoot;
                            if (temp.right.height > temp.left.height) {
                                temp.right.colFromParent += temp.colFromParent;
                            } else if (temp.right.height < temp.left.height) {
                                temp.left.colFromParent += temp.colFromParent;
                            } else {
                                long col = temp.colFromParent / temp.leaves;
                                temp.left.colFromParent += temp.left.leaves * col;
                                temp.right.colFromParent += temp.right.leaves * col;
                            }
                            stack.add(temp.left);
                        } else {
                            temp.right.colFromParent = temp.colRoot + temp.colFromParent;
                        }
                    } else if (temp.left != null) {
                        temp.colRoot *= temp.left.leaves;
                        temp.left.colFromParent = temp.colRoot + temp.colFromParent;
                        stack.add(temp.left);
                    }
                } else {
                    if (temp.right != null) {
                        stack.add(temp.right);
                        if (temp.left != null) {
                            temp.right.colFromParent = temp.left.colFromParent = temp.colRoot;
                            if (temp.right.height > temp.left.height) {
                                temp.right.colFromParent += temp.colFromParent;
                            } else if (temp.right.height < temp.left.height) {
                                temp.left.colFromParent += temp.colFromParent;
                            } else {
                                long col = temp.colFromParent / temp.leaves;
                                temp.left.colFromParent += temp.left.leaves * col;
                                temp.right.colFromParent += temp.right.leaves * col;
                            }
                            stack.add(temp.left);
                        } else {
                            temp.right.colFromParent = temp.colRoot + temp.colFromParent;
                        }
                    } else if (temp.left != null) {
                        temp.left.colFromParent = temp.colRoot + temp.colFromParent;
                        stack.add(temp.left);
                    }
                }
                if (temp.colFromParent + temp.colRoot > maxCol)
                    maxCol = temp.colFromParent + temp.colRoot;
            }
        }

        private void calculateHL(Node cur) {
            if (cur.left != null) {
                calculateHL(cur.left);
                cur.height = cur.left.height + 1;
                cur.leaves = cur.left.leaves;
                cur.maxHalfway = cur.left.height + 1;
                if (cur.right != null) {
                    calculateHL(cur.right);
                    cur.maxHalfway += cur.right.height + 1;
                    if (cur.right.height == cur.left.height) {
                        cur.leaves += cur.right.leaves;
                    } else if (cur.right.height > cur.left.height) {
                        cur.height = cur.right.height + 1;
                        cur.leaves = cur.right.leaves;
                    } else {
                        cur.height = cur.left.height + 1;
                        cur.leaves = cur.left.leaves;
                    }
                }
                if (cur.maxHalfway > greatestHalfway) {
                    greatestHalfway = cur.maxHalfway;
                }
            } else if (cur.right != null) {
                calculateHL(cur.right);
                cur.maxHalfway = cur.right.height + 1;
                cur.height = cur.right.height + 1;
                cur.leaves = cur.right.leaves;
                if (cur.maxHalfway > greatestHalfway) {
                    greatestHalfway = cur.maxHalfway;
                }
            }
        }

        static class Node {
            final int key;
            Node left;
            Node right;
            int height;
            int leaves;
            int maxHalfway;
            long colRoot;
            long colFromParent;

            Node(int key) {
                this.key = key;
                leaves = 1;
            }
        }
    }
}