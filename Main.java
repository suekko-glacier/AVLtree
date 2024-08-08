import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int in_d;         // 入力データ
        in_d = scan.nextInt();
        AVL n = AVL.create(in_d);
        System.out.println("\n\n ---- " + in_d +" 挿入 ----");

        do {
            in_d = scan.nextInt();
            if (in_d > 0) {
                System.out.println("\n\n ---- " + in_d +" 挿入 ----");
                n.insert(in_d); 
            } else if(in_d < 0){
                System.out.println("\n\n ---- " + -in_d + "削除 ----");
                n.delete(-in_d); 
            }
            System.out.println("\n");
            
            n.display(n.getNode(), 0);
            System.out.println("\n  葉の数: " + n.num_leaves(n.getNode()) + ", 木の高さ: " + n.height(n.getNode()) + "\n");
            
        } while(in_d != 0);
        scan.close();
    }
}
