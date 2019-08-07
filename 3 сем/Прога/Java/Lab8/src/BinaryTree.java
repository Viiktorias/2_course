import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class BinaryTree<T extends Comparable<? super T>> {
    private Node<T> root;

    private static <T extends Comparable<? super T>> Node<T> add(Node<T> node, T element) {
        if (node == null) {
            return new Node<>(element);
        }
        if (element.compareTo(node.key) < 0) {
            node.left = add(node.left, element);
        } else if (element.compareTo(node.key) > 0) {
            node.right = add(node.right, element);
        }
        return node;
    }

    private static <T extends Comparable<? super T>> Node<T> delete(Node<T> node, T element) {
        if (node == null) {
            return null;
        }
        if (element.compareTo(node.key) < 0) {
            node.left = delete(node.left, element);
        } else if (element.compareTo(node.key) > 0) {
            node.right = delete(node.right, element);
        } else {
            if ((node.left == null) && (node.right == null)) {
                return null;
            } else if (node.left != null) {
                if (node.right == null) {
                    return node.left;
                } else {
                    Node<T> temp = node.left;
                    if (temp.right == null) {
                        temp.right = node.right;
                        return temp;
                    } else {
                        while (temp.right.right != null)
                            temp = temp.right;
                        Node<T> max = temp.right;
                        temp.right = max.left;
                        max.right = node.right;
                        max.left = node.left;
                        return max;
                    }
                }
            } else {
                return node.right;
            }
        }
        return node;
    }

    private static <T extends Comparable<? super T>> Node<T> find(Node<T> node, T key) {
        if (node == null)
            return null;
        if (key.compareTo(node.key) < 0) {
            return find(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return find(node.right, key);
        } else return node;
    }

    private static <T extends Comparable<? super T>> int height(Node<T> node) {
        if (node != null) {
            return Integer.max(height(node.right), height(node.left)) + 1;
        }
        return 0;
    }

    private static <T extends Comparable<? super T>> void roundLTR(Node<T> node, List<T> list) {
        if (node != null) {
            roundLTR(node.left, list);
            list.add(node.key);
            roundLTR(node.right, list);
        }
    }

    private static <T extends Comparable<? super T>> void roundLRT(Node<T> node, List<T> list) {
        if (node != null) {
            roundLRT(node.left, list);
            roundLRT(node.right, list);
            list.add(node.key);
        }
    }

    public T find(T key) {
        Node<T> founded = find(root, key);
        if (founded != null)
            return founded.key;
        return null;
    }

    public List<T> roundLTR() {
        List<T> list = new ArrayList<>();
        roundLTR(root, list);
        return list;
    }

    public List<T> roundRTL() {
        List<T> list = new ArrayList<>();
        Stack<Node<T>> stack = new Stack<>();
        stack.push(root);
        Node<T> cur;
        while (!stack.empty()) {
            cur = stack.pop();
            if (cur != null) {
                stack.push(cur);
                stack.push(cur.right);
            } else if (!stack.empty()) {
                cur = stack.pop();
                list.add(cur.key);
                stack.push(cur.left);
            }
        }
        return list;
    }

    public List<T> roundTLR() {
        List<T> list = new ArrayList<>();
        Stack<Node<T>> stack = new Stack<>();
        stack.push(root);
        Node<T> cur;
        while (!stack.empty()) {
            cur = stack.pop();
            if (cur != null) {
                stack.push(cur.right);
                stack.push(cur.left);
                list.add(cur.key);
            }
        }
        return list;
    }

    public List<T> roundLRT() {
        List<T> list = new ArrayList<>();
        roundLRT(root, list);
        return list;
    }

    private int height() {
        return height(root);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BinaryTree\n");
        Stack<Node<T>> globalStack = new Stack<>();
        globalStack.push(root);
        int nBlanks = (int) Math.pow(2, this.height());
        boolean isRowEmpty = false;
        while (!isRowEmpty) {
            Stack<Node<T>> localStack = new Stack<>();
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++)
                sb.append(' ');
            while (!globalStack.isEmpty()) {
                Node<T> temp = globalStack.pop();
                if (temp != null) {
                    sb.append(temp.key);
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if (temp.left != null ||
                            temp.right != null)
                        isRowEmpty = false;
                } else {
                    sb.append("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++)
                    sb.append(' ');
            }
            sb.append("\n");
            nBlanks /= 2;
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop());
        }
        return sb.toString();
    }

    public void add(T element) {
        root = add(root, element);
    }

    public void delete(T element) {
        root = delete(root, element);
    }

    static class Node<T> {
        T key;
        Node<T> left;
        Node<T> right;

        Node(T key) {
            this.key = key;
        }
    }
}