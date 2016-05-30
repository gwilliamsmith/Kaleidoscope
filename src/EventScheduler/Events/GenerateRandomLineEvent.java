package EventScheduler.Events;

import SwingElements.Base;
import graphvisualizer.Graph;
import graphvisualizer.GraphTupleInfo;
import java.util.Random;


public class GenerateRandomLineEvent extends Event{

    public GenerateRandomLineEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in) {
        super(stepTargetIn, eventNameIn, repeatIn, in);
    }//end constructor

    @Override
    public void takeAction() {
        GraphTupleInfo temp = GraphTupleInfo.generateRandomGTI(ref);
        Random rand = new Random();
        Graph graph = ref.getGraph();
        int x1 = rand.nextInt(graph.getGraphWidth());
        int y1 = rand.nextInt(graph.getGraphHeight());
        int x2 = Math.max(0,rand.nextInt(x1-(x1-1)+1)+(x1-1));
        int y2 = Math.max(0,rand.nextInt(y1-(y1-1)+1)+(y1-1));
        graph.connector(graph.getNode(x1, y1), graph.getNode(x2, y2), temp);
    }//end takeAction
    
}//end RunEvent class
