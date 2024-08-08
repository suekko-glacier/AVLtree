interface AVL {
    public static AVL create(int n) {
        return new AVLImpl(n);
    }

    Node getNode(); 

    void insert(int val);       //値の挿入
    Node search(int val);       //値の探索
    void delete(int val);       //値の削除
    int num_leaves(Node root);  //葉の個数を返す
    int height(Node root);      //木の高さを返す

    Node rot_left1(Node current, Node q );  // 一重 (tooleft)
    Node rot_left2(Node current, Node q );  // 二重 (tooleft)
    Node rot_right1(Node current, Node q ); // 一重 (tooright)
    Node rot_right2(Node current, Node q ); // 二重 (tooright)

    void display(Node root, int level); //二分木の表示
}
