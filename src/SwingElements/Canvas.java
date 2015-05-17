package SwingElements;

import graphvisualizer.Base;
import Listeners.CanvasMouseListener;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    private Base ref;

    public Canvas(Base in) {
        ref = in;
        this.setBackground(Color.white);
        this.setSize(500, 500);
        this.addMouseListener(new CanvasMouseListener(ref));
    }//end constructor

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ref.graph.paint(g);
        FontMetrics fontSize = g.getFontMetrics();
        g.setColor(Color.black);
        int textWidth = fontSize.stringWidth("Action: " + ref.getActionString());
        g.drawString("Action: " + ref.getActionString(), this.getWidth() - textWidth, 11);
        textWidth = fontSize.stringWidth("Steps taken:" + ref.getStepCount());
        g.drawString("Steps taken: " + ref.getStepCount(), this.getWidth() - textWidth - 5, this.getHeight() - 11);
        if (ref.getCycleBase() > 0) {
            textWidth = fontSize.stringWidth("Cycles: " + ref.getCycleCount());
            g.drawString("Cycles: " + ref.getCycleCount(), this.getWidth() - textWidth - 5, this.getHeight() - 22);
        }//end if
        ref.updateAverageColor();
    }//end paint

}//end Canvas
