
package graphvisualizer;

import java.awt.Color;

public enum GraphNodeColors {
    DEFAULT_COLOR (Color.BLUE),
    DEFAULT_EDGE_COLOR (Color.BLACK),
    DEFAULT_MIDDLE_COLOR (Color.DARK_GRAY),
    SELECTED_COLOR (Color.GREEN),
    SELECTED_ADJACENT_COLOR (Color.CYAN),
    LINE_EVENT_NODE1_COLOR (Color.YELLOW),
    LINE_EVENT_NODE1_ADJACENT_COLOR (new Color (200,200,0)),
    LINE_EVENT_NODE2_COLOR (Color.ORANGE),
    WHITE (Color.WHITE);
    
    private Color color;

    private GraphNodeColors(Color in) {
        color = in;
    }//end GraphNodeColors constructor
    
    public Color getColor(){
        return color;
    }//end getColor
}//end GraphNodeColors enum
