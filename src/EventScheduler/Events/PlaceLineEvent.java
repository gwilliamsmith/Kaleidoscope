package EventScheduler.Events;

import SwingElements.Base;
import graphvisualizer.GraphNode;
import graphvisualizer.GraphTupleInfo;

public class PlaceLineEvent extends Event {

    private GraphNode node1;
    private GraphNode node2;
    private GraphTupleInfo gti;

    public PlaceLineEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in, GraphNode gn1, GraphNode gn2, GraphTupleInfo gtiIn) {
        super(stepTargetIn, eventNameIn, repeatIn, in);
        node1 = gn1;
        node2 = gn2;
        gti = gtiIn;
    }//end PlaceLineEvent

    @Override
    public void takeAction() {
        ref.getGraph().connector(node1, node2, gti);
    }//end takeAction
    
    public GraphNode getNode1(){
        return node1;
    }//end getNode1
    
    public void setNode1(GraphNode in){
        node1 = in;
    }//end setNode1
    
    public GraphNode getNode2(){
        return node2;
    }//end getNode2
    
    public void setNode2(GraphNode in){
        node2 = in;
    }//end setNode2
    
    public GraphTupleInfo getGti(){
        return gti;
    }//end getGti
    
    public void setGti(GraphTupleInfo in){
        gti = in;
    }//end setGti

}//end PlaceLineEvent class
