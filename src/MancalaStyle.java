/**
 * MancalaStyle is the interface that will be implements by sub class for 
 * different type of UI of the Mancala game
 */

import java.awt.Color;
import java.awt.geom.RectangularShape;

public interface MancalaStyle {
    /**
     * unimplemented method of the interface
     * use for get shape of the game
     * @return shape
     */
	public RectangularShape getShape();
	/**
     * unimplemented method of the interface
     * use for get color of the board of the game
     * @return color
     */
    public Color getBoardColor();
    /**
     * unimplemented method of the interface
     * use for get color of the marble of the game
     * @return color
     */
    public Color getMarbleColor();
}
