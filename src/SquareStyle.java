/**
 * SquareStyle is the class implements from interface MancalaStyle 
 * it contains the squarestyle of the game
 */
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;


class SquareStyle implements MancalaStyle {

    /**
     * getShape method set the game UI as square style
     */
    public RectangularShape getShape() {
        return new Rectangle2D.Double();
    }

    /**
     * getBoardColor returns the color of the board for the game
     */
    public Color getBoardColor() {
        return new Color(152,202,237);
    }  
    
    /**
     * getMarbleColor returns the color of marble for the game
     */
    public Color getMarbleColor() {
        return new Color(97,28,123);
    }  
    
}
