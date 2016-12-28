/**
 * CircleStyle is the class implements from interface MancalaStyle 
 * it contains the circlestyle of the game
 */
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

public class CircleStyle implements MancalaStyle {
    
	/**
     * getShape method set the game UI as circle style
     */
	public RectangularShape getShape() {
        return new Ellipse2D.Double();
    }

    /**
     * getBoardColor set the color of the board for the game
     */
    public Color getBoardColor() 
    {
        return new Color(250,184,152);
    }
    
    /**
     * getMarbleColor set the color of marble for the game
     */
    public Color getMarbleColor() 
    {
        return new Color(24,131,46);
    }
}
