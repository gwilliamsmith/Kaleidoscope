package graphvisualizer;

import java.util.Comparator;

public class NodeTuple implements Comparator<NodeTuple>{
    public GraphNode node1;
    public GraphNode node2;
    
    public NodeTuple(){
        
    }//end constructor
    public NodeTuple(GraphNode one, GraphNode two){
        node1 = one;
        node2 = two;
    }//end constructor
    public void print(){
        System.out.println("["+node1.id+","+node2.id+"]");
    }//end print
    @Override
    public String toString(){
        return "["+node1.id+","+node2.id+"]";
    }//end toString

    @Override
    public int compare(NodeTuple n1, NodeTuple n2) {
        int out = -1;
        if((n1.node1.id == n2.node1.id) && (n1.node2.id == n2.node2.id)){
            out =  0;
        }//end if
        if((n1.node1.id == n2.node2.id) && (n1.node2.id == n2.node1.id)){
            out = 0;
        }//end if
        return out;
    }//end compare
}//end NodeTuple
