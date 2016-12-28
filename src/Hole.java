/**
 * this class paints each hole for the players, in this case, 6 for each. 
 * take initial format from user(3 or 4 marbles each hole), and fill in the board
 */
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import javax.swing.JPanel;

public class Hole extends JPanel {
    
    private int width;
    private int height;
    private int numOfMarbles = 0;
    RectangularShape shape;
    Color Boardcolor, marbleColor;
    
    public boolean selected = false;    
    
    /**
     * constructor: take parameters and set initial view and format
     * @param w - width
     * @param h - height
     * @param marbles - number of marbles
     */
    public Hole(int w, int h, int marbles) {
        width = w;
        height = h;
        setPreferredSize(new Dimension(w, h));
        this.setLocation(0, 0);
        numOfMarbles = marbles;
    }
    
    /**
     * set UI, and repaint the view
     * @param m - UI object, square/circle
     */
    public void setStyle(MancalaStyle m) {
        shape = m.getShape();
        Boardcolor = m.getBoardColor();
        marbleColor = m.getMarbleColor();
    }
    
    /**
     * paint marbles for each hole
     */
    public void paint(Graphics g) 
    {
        final Graphics2D g2 = (Graphics2D) g;
        
        shape.setFrame(0, 0, width, height);
        g2.setColor(Boardcolor);
        g2.fill(shape);
        g2.setPaint(marbleColor);
        final Ellipse2D.Double marble = new Ellipse2D.Double(30, 20, 20, 20);
        for (int i = 0; i < numOfMarbles; i++) {
            if (i%4 == 0) {
                marble.y += 20;
                marble.x = 30;
            } else {
                marble.x += 20;
            }
            
            g2.fill(marble);
            g2.draw(marble);
        }   
    }   
}
