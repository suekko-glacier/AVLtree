public class AVLImpl implements AVL{
    Node root;
    AVLImpl(int n) {
        root = new Node(n);
    }

    public Node getNode() {
        return root;
    }

    //入力された値の挿入
    public void insert(int val) {
        Node current, parent = null, new_node;
        
        current = root;
        while(current != null) { 
          if( val == current.value ) {
            return ;
          }  
          if( val < current.value ){ 
            parent = current;
            current = current.left;
          } else { 
            parent = current;
            current = current.right;
          }
        }

        //valの登録 
        new_node = new Node(val);
        new_node.parent  = parent;
        new_node.left  = null;
        new_node.right = null;
        new_node.balance = 0;
        if (parent != null) {                     // 木が空でない場合
            if( val < parent.value ){
                parent.left = new_node;
                parent.balance--;                     // 親の balance の更新
            } else {
                parent.right = new_node;
                parent.balance++;                     // 親の balance の更新
            }

            // 先祖の balance の更新　回転
            for (current = parent; 
                    (current.parent != null && current.balance != 0) || 
                        (current.parent == null && (current.balance == -2 || current.balance == 2)) ; 
                    current = current.parent) {
                if (current.balance == -1 || current.balance == 1) {
                    // 回転不要 current.parent のバランスの更新
                    if (current.parent.right == current)
                        current.parent.balance++;
                    else
                        current.parent.balance--;
                } else if (current.balance == -2) {
                    // tooleft の場合
                    Node q;
                    q = current.left;
                    System.out.println("\n【too left】 ");
                    if (q.balance == -1) {
                        // 一重回転 (tooleft)
                        System.out.println("\n一重回転\n\n=== 回転前の木の内容 ===\n");
                        display(root,0);                          // 二分探索木の内容表示
                        root = rot_left1(current, q);
                    } else if (q.balance == 1) {
                        // 二重回転 (tooleft)
                        System.out.println("\n二重回転\n\n=== 回転前の木の内容 ===\n");
                        display(root,0);                          // 二分探索木の内容表示
                        root = rot_left2(current, q);
                    } else {
                        throw new IllegalArgumentException("error");
                    }
                    break;

                } else if (current.balance == 2) {
                    // tooright の場合
                    Node q;
                    q = current.right;
                    System.out.println("\n【too right】 ");
                    if (q.balance == 1) {
                        // 一重回転 (tooright)
                        System.out.println("一重回転\n\n=== 回転前の木の内容 ===\n");
                        display(root,0);                          // 二分探索木の内容表示
                        root = rot_right1(current, q);
                    } else if (q.balance == -1) {
                        // 二重回転 (tooright)
                        System.out.println("二重回転\n\n=== 回転前の木の内容 ===\n");
                        display(root,0);                          // 二分探索木の内容表示
                        root = rot_right2(current, q);
                    } else {
                        throw new IllegalArgumentException("error");
                    }
                    break;
                }
            }
        } else {                                 // 木が空の場合
            root = new_node;
        }
        
    }
    
    //入力された値の探索
    public Node search(int val) {
        Node current = root;
        while( current != null ){
            if( val == current.value ) { 
                return(current);
            }
            if( val < current.value ){
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return(null);
    }

    //入力された値の削除
    public void delete(int val) {
        Node del_node;

          // 二分探索木が空の場合
        if( root == null ) {
            return ;
        }

        // val を保持するノードを検索
        del_node = search(val);
  
        // valを保持したノードが存在しない場合
        if( del_node == null ) {
            return ;
        }

        //  削除対象ノードが子供を持っていない場合       
        if( del_node.left == null && del_node.right == null ) {
            if( del_node == root ){        // 削除対象ノードが根のとき    
                root = null;
            } else {                       // 削除対象ノードが根でないとき
                if( val < del_node.parent.value ){
                    del_node.parent.left = null;
                } else {
                    del_node.parent.right = null;
                }
            }
        } else

        // 削除対象ノードが右側の子だけを持つ（左の子を持たない) 場合 
        if( del_node.left == null && del_node.right != null ) {
            if( del_node == root ) {       // 削除対象ノードが根のとき
                root = del_node.right;
                del_node.right.parent = null;
            } else {                      // 削除対象ノードが根でないとき
                if( val < del_node.parent.value ) {
                    del_node.parent.left = del_node.right;
                } else {
                    del_node.parent.right = del_node.right;
                }
                del_node.right.parent = del_node.parent;
            }
        } else
            
        // 削除対象ノードが左側の子だけを持つ（右の子を持たない) 場合 
        if( del_node.left != null && del_node.right == null ) {
            if( del_node == root ) {       // 削除対象ノードが根のとき
                root = del_node.left;
                del_node.left.parent = null;
            } else {                      // 削除対象ノードが根でないとき
                if( val < del_node.parent.value ) {
                    del_node.parent.left = del_node.left;
                } else {
                    del_node.parent.right = del_node.left;
                }
                del_node.left.parent = del_node.parent;
            }
        } else {
            
        // 削除対象ノードが左右の子を持つ場合 
            Node substitute, sub_parent;
            
            // val（削除対象のデータ要素）の 左の子を根とする部分木で最大のノード substitute を求める
            substitute = del_node.left;
            sub_parent = del_node; 
            while( substitute.right != null ) {
                sub_parent = substitute;
                substitute = substitute.right;
            }

            // substitute を二分探索木から取り外す
            if( sub_parent == del_node ) {
                sub_parent.left = substitute.left;
            } else {
                sub_parent.right = substitute.left;
                if (substitute.left != null) {
                    substitute.left.parent = sub_parent; // subustitute の左の子を親の子に
                }
            }

            // 削除対象ノードを substitute に置き換える
            substitute.parent  = del_node.parent;
            substitute.left  = del_node.left;
            substitute.right = del_node.right;
            if( root == del_node ) {
                root = substitute;
            } else {
                if( val < del_node.parent.value ) {
                    del_node.parent.left = substitute;
                } else {
                    del_node.parent.right = substitute;
                }
            }

            if (del_node.left != null) {
                del_node.left.parent = substitute;
            }
            if (del_node.right != null) {
                del_node.right.parent = substitute;
            }
        }
        
    }

    //葉の個数を返す
    public int num_leaves(Node root) {
        if(root == null) {
            return 0;
        }
        if(root.left == null && root.right == null) {
            return 1;
        }
        return num_leaves(root.left) + num_leaves(root.right);
    }

    //木の高さを返す
    public int height(Node root) {
        if(root == null) {
            return 0;
        }
        if(root.left == null && root.right == null) {
            return 0;
        }

        int leftDepth = height(root.left);
        int rightDepth = height(root.right);
        
        return Math.max(leftDepth, rightDepth) + 1;
    }

    //  一重回転関数 (tooleft) 
    public Node rot_left1 (Node current, Node q ) {
        System.out.print("\n=== 一重回転 (tooleft) ===");

        // q の右部分木を current の左部分木に
        current.left = q.right; 
        if (q.right != null) {
            q.right.parent = current;
        }
        // current を根とする部分木を q の右部分木に
        q.right = current; 
        q.parent = current.parent; 

        // q を根とする部分木を current.parent の部分木に
        if (current.parent != null) {
            if (current.parent.left == current) {
                current.parent.left = q;  // current が親の左の子
            } else {
                current.parent.right = q; // current が親の右の子
            }
        } else {
            root = q; // current が根の場合
        }
        current.parent = q;

        // balance の更新
        q.balance = 0;
        current.balance = 0;
        return(root);
    }
    
    //  二重回転関数 (tooleft) 
    public Node rot_left2 (Node current, Node q ) {
        System.out.print("\n=== 二重回転 (tooleft) ===");
        Node new_root = q.right; 
        int old_balance = new_root.balance;

        // new_root の左部分木を q の右部分木に
        q.right = new_root.left; 
        if (q.right != null) {
            q.right.parent = q; 
        }

        // new_root の右部分木を current の左部分木に
        current.left = new_root.right; 
        if (current.left != null) {
            current.left.parent = current; 
        }

        // new_root を今見ている部分木の根に
        new_root.parent = current.parent;
        if (new_root.parent != null) {
            if (new_root.parent.left == current) {
                new_root.parent.left = new_root;  // current が親の左の子
            } else {
                new_root.parent.right = new_root; // current が親の右の子
            }
        } else {
            root = new_root; // current が根の場合
        }
        new_root.left = q; 
        q.parent = new_root;
        new_root.right = current; 
        current.parent = new_root;

        // balance の更新
        new_root.balance = 0;
        if (current.right != null) {
            if (old_balance == -1) {
                q.balance = 0;
                current.balance = 1;
            } else {
                q.balance = -1;
                current.balance = 0;
            }
        } else {
            q.balance = 0;
            current.balance = 0;
        }
        return(root);
    }
    
    //  一重回転関数 (tooright) 
    public Node rot_right1 (Node current, Node q ) {
        System.out.print("\n=== 一重回転 (tooright) ==="); 

        // q の左部分木を current の右部分木に
        current.right = q.left; 
        if (q.left != null) {
            q.left.parent = current;
        }

        // current を根とする部分木を q の左部分木に
        q.left = current; 
        q.parent = current.parent; 

        // q を根とする部分木を current.parent の部分木に
        if (current.parent != null) {
            if (current.parent.right == current) {
                current.parent.right = q;  // current が親の右の子
            } else {
                current.parent.left = q; // current が親の左の子
            }
        } else {
            root = q; // current が根の場合
        }
        current.parent = q;

        // balance の更新
        q.balance = 0;
        current.balance = 0;
        return(root);
    }
    
    //  二重回転関数 (tooright) 
    public Node rot_right2 (Node current, Node q ) {
        System.out.print("\n=== 二重回転 (tooright) ===");
        Node new_root = q.left; 
        int old_balance = new_root.balance;

        // new_root の右部分木を q の左部分木に
        q.left = new_root.right; 

        if (q.left != null) {
            q.left.parent = q;
        }

        // new_root の左部分木を current の右部分木に
        current.right = new_root.left; 
        if (current.right != null) {
            current.right.parent = current; 
        }

        // new_root を今見ている部分木の根に
        new_root.parent = current.parent;
        if (new_root.parent != null) {
            if (new_root.parent.right == current) {
                new_root.parent.right = new_root;  // current が親の右の子
            } else {
                new_root.parent.left = new_root; // current が親の左の子
            }
        } else {
            root = new_root; // current が根の場合
        }
        new_root.right = q; 
        q.parent = new_root;
        new_root.left = current; 
        current.parent = new_root;

        // balance の更新
        new_root.balance = 0;
        if (current.left != null) {
            if (old_balance == 1) {
                q.balance = 0;
                current.balance = -1;
            } else {
                q.balance = 1;
                current.balance = 0;
            }
        } else {
            q.balance = 0;
            current.balance = 0;
        }
        return(root);
    }


    public void display(Node root,int level) {
        int i;
        if(root != null){
            if( level == 0 ){
                System.out.println(root.value);
            } else {
                for(i=0;i<level;i++) {
                    System.out.print("\t"); 
                }
                System.out.println(root.value);
            }
            display(root.left,level+1);            // 左部分木
            display(root.right,level+1);           // 右部分木
        }
    }
}
