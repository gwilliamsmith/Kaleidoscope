package graphvisualizer;

import java.awt.Color;

/**
 * An enum, defining possible colors for a {@link GraphNode}.
 */
public enum GraphNodeColors {

    DEFAULT_COLOR(Color.BLUE), //The default color for most nodes
    DEFAULT_EDGE_COLOR(Color.BLACK), //The default color for a node on the edge of the Graph rectangle
    DEFAULT_MIDDLE_COLOR(Color.DARK_GRAY), //The default color for a node in the middle row or column of the Graph
    SELECTED_COLOR(Color.GREEN), //Used when the user has clicked on a node to create a connection
    SELECTED_ADJACENT_COLOR(Color.CYAN), //Used to highlight nodes eligable for GraphTuple connection by the user
    LINE_EVENT_NODE1_COLOR(Color.YELLOW), //The first node being selected when setting up a PlaceLineEvent
    LINE_EVENT_NODE1_ADJACENT_COLOR(new Color(200, 200, 0)), //Highlights nodes adjacent to Line_Event_Node1
    LINE_EVENT_NODE2_COLOR(Color.ORANGE), //The second node being selected when setting up a PlaceLineEvent
    WHITE(Color.WHITE);                                                         //White...yeah this one's obvious

    private Color color;

    /**
     * Private constructor, so that the enum can have {@link java.awt.Color}s
     * associated with them.
     *
     * @param in The color to associate with an enum value
     */
    private GraphNodeColors(Color in) {
        color = in;
    }//end GraphNodeColors constructor

    /**
     * Retrieves the color associated with the enum value
     *
     * @return The {@link java.awt.Color} object describing the color for the
     * enum value
     */
    public Color getColor() {
        return color;
    }//end getColor
}//end GraphNodeColors enum
