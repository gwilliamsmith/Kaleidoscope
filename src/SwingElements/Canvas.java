/*TODO:
 Stop Using Magic Numbers for JPanel/BufferedImage size
 */
package SwingElements;

import graphvisualizer.Base;
import Listeners.CanvasMouseListener;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    private Base ref;
    public int windowX = 0;
    public int windowY = 0;

    public Canvas(Base in) {
        ref = in;
        this.setBackground(Color.white);
        this.setSize(in.getWidth(), in.getHeight());
        CanvasMouseListener mouseListener = new CanvasMouseListener(ref);
        this.addMouseListener(mouseListener);
        this.addMouseWheelListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
    }//end constructor

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(producePicture(), 0, 0, null);
        ref.updateAverageColor();
    }//end paint

    public BufferedImage producePicture() {
        BufferedImage picture = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = picture.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ref.getGraph().paint(g);
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
        g.dispose();
        return picture;
    }//end updatePicture

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }//end resize
    
    public int getWindowX(){
        return windowX;
    }//end getWindowX
    
    public void modifyWindowX(int mod){
        windowX = windowX + mod;
    }//end modifyWindowX
    
    public int getWindowY(){
        return windowY; 
    }//end getWindowY
    
    public void modifyWindowY(int mod){
        windowY = windowY + mod;
    }//end modifyWindowX
}//end Canvas
