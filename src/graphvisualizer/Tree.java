package graphvisualizer;

public class Tree<T> {

    TreeNode<T> head;

    public Tree() {
        head = null;
    }//end constructor

    public TreeNode<T> get(T data) {
        if (head != null) {
            TreeNode<T> temp = head;
            for (TreeNode<T> tn : temp.children) {
                if (temp.data == data) {
                    return temp;
                }//end if
                return (get(data,tn));
            }//end while
        }//end if
        return null;
    }//end get

    private TreeNode<T> get(T data, TreeNode<T> start) {
        TreeNode<T> temp = start;
        System.out.println("At: " + temp    );
        for (TreeNode<T> tn : temp.children) {
            if (temp.data == data) {
                return temp;
            }//end if
            return (get(data,tn));
        }//end while
        return null;
    }//end get
}//end Tree class
