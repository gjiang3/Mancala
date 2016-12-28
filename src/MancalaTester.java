/**
 * Contains Main method, entry of the program
 */
import java.awt.Dimension;
import java.awt.Toolkit;


public class MancalaTester {

    public static void main(String[] args) 
    {
        MainView MV = new MainView();
        MV.setSize(new Dimension(1440, 500));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        MV.setLocation(dim.width/2-MV.getSize().width/2, dim.height/2-MV.getSize().height/2);   
        GameControllor c = new GameControllor(3, MV);
        MV.setData(c);   
    }
}
