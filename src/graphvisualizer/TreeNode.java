package graphvisualizer;

import java.util.ArrayList;

public class TreeNode<T> {
    T data;
    ArrayList<TreeNode> children = new ArrayList<>();
    TreeNode<T> parent;
    public TreeNode(T input){
        data = input;
    }//end constructor
    public TreeNode(T input, TreeNode<T> parent){
        data = input;
        this.parent = parent;
    }//end constructor
    public boolean hasParent(){
        if(parent != null){
            return true;
        }//end if
        return false;
    }//end hasParent
    public void addChild(TreeNode<T> child){
        child.parent = this;
        children.add(child);
    }//end addChild
    public void addChild(T input){
        TreeNode<T> temp = new TreeNode<>(input);
        temp.parent = this;
        children.add(temp);
    }//end addChild
}//end TreeNode
