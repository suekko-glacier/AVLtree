public class Node {
    int value;
    Node parent = null;
    Node left = null;
    Node right = null;
    int balance = 0;

    Node() {

    }
    Node(int value0) {
        value = value0;
    }
}
