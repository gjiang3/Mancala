/**
 * This class prints a mancala board. Whenever there is a change, the data updates the board, and 
 * display changes. 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainView extends JFrame
{
    
    private GameControllor controllor;
    private final JPanel board;
    private final double SCALE = 1.8;
    private final JLabel displayMessage = new JLabel();
    private final JButton undo;
    private final ButtonGroup bg1;
    private final JRadioButton three;
    private final JRadioButton four;
    private MancalaStyle userInt = new CircleStyle();
    private final JLabel player1 = new JLabel("Player 1");
    private final JLabel player2 = new JLabel("Player 2");
    private JLabel player1Undo;
    private JLabel player2Undo;
    private final JButton newGame;
    
    /**
     * Constructor: initialize object, set initial view, and buttons
     */
    public MainView() 
    {
        setLayout(new BorderLayout());
        
        board = new JPanel();
        
        board.setPreferredSize(new Dimension((int)(800 * SCALE), (int)(250 * SCALE)));
        add(board, BorderLayout.CENTER);
        
        displayMessage.setText("Player 1");
        displayMessage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        add(displayMessage, BorderLayout.SOUTH);
        
        JPanel buttons = new JPanel();
        JPanel temp = new JPanel(new BorderLayout());
        
        undo = new JButton("Undo");
        undo.setBackground(Color.LIGHT_GRAY);
        undo.setEnabled(false);
        undo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	controllor.undo();
                undo.setEnabled(false);
            }
        });
        temp.add(undo, BorderLayout.EAST);
        
        
        newGame = new JButton("New Game");
        newGame.setBackground(Color.RED);
        newGame.setForeground(Color.WHITE);
        
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                if (three.isSelected())
                {
                	controllor.newGame(3);
                } 
                else 
                {
                	controllor.newGame(4);
                }
                undo.setEnabled(false);
            }
        });

        JLabel numOfMarbles = new JLabel("Number of marbles: ");
        bg1 = new ButtonGroup();
        three = new JRadioButton("3");
        three.setSelected(true);
        four = new JRadioButton("4");
        bg1.add(three);
        bg1.add(four);
        buttons.add(numOfMarbles);
        buttons.add(three);
        buttons.add(four);
        
        
        JButton circle = new JButton("Circle sytle");
        circle.setBackground(Color.WHITE);
        circle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userInt = new CircleStyle();
                setupBoard();
            }
        });
        buttons.add(circle);
        JButton square = new JButton("Square sytle");
        square.setBackground(Color.WHITE);
        square.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userInt = new SquareStyle();
                setupBoard();
            }
        });
        buttons.add(square);
        buttons.add(newGame);
        temp.add(buttons, BorderLayout.CENTER);
        add(temp, BorderLayout.NORTH);
        
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * setup board, and format of data, and object be seen in the board
     */
    public void setupBoard() {
        board.removeAll();
        board.revalidate();
        board.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);

        for (int i = 0; i < 14; i++) 
        {
            final Hole hole;

            if (i == 0) {
                c.gridheight = 2;
                c.gridx = 0;
                c.gridy = 0;
                hole = new Hole((int)(80 * SCALE), (int)(160 * SCALE), controllor.getMancalaB());
                hole.setName("6");
            } else if(i == 13) {
                c.gridheight = 2;
                c.gridx = 7;
                c.gridy = 0;
                hole = new Hole((int)(80 * SCALE), (int)(160 * SCALE), controllor.getMancalaA());
                hole.setName("13");
            } else if (i > 6) {
                c.gridheight = 1;
                c.gridx = i - 6;
                c.gridy = 1 ;
                
                hole = new Hole((int)(80 * SCALE), (int)(80 * SCALE), controllor.getPlayerAMarble().get(i-7));
                hole.setName(i + "");
            } else {
                c.gridheight = 1;
                c.gridx = i;
                c.gridy = 0;
                hole = new Hole((int)(80 * SCALE), (int)(80 * SCALE), controllor.getPlayerBMarble().get(i-1));
                hole.setName((6-i) + "");
            }
            
            hole.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) 
                {
                	if (controllor.checkWinState() == 4) 
                	{
                        
                        int state = controllor.makeMove(Integer.parseInt(hole.getName()));
                        if(state == 0)	
                        {
                            String temp = displayMessage.getText();
                            displayMessage.setText(temp.split(": ")[0] + ": Invalid move! The macalas are not clickable");
                        }
                        else if(state == 1)	
                        {
                            String temp = displayMessage.getText();
                            displayMessage.setText(temp.split(": ")[0] + ": Invalid move! You can click on opponent's board");
                        }
                        else if(state == 2)	
                        {
                            String temp = displayMessage.getText();
                            displayMessage.setText(temp.split(":")[0] + ": Invalid move! The pit is empty");
                        }
                        
                        
                        undo.setEnabled(true);
                    }
                }      
            });
            
            hole.setStyle(userInt);
            board.add(hole, c);   
        }
        
        player2.setFont(new Font("Serif", Font.BOLD, 18));
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 3;
        
        board.add(player2, c);
        
        String tem = "Undo remaining: " + (3 -controllor.getUndoCounterB());
        player2Undo = new JLabel(tem);
        player2Undo.setFont(new Font("Serif", Font.BOLD, 18));
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 4;
        board.add(player2Undo, c);
        
        player1.setFont(new Font("Serif", Font.BOLD, 18));
        c.gridheight = 1;
        c.gridx = 7;
        c.gridy = 3;
        
        board.add(player1, c);
        
        tem = "Undo remaining: " + (3 -controllor.getUndoCounterA());
        player1Undo = new JLabel(tem);
        player1Undo.setFont(new Font("Serif", Font.BOLD, 18));
        c.gridheight = 1;
        c.gridx = 7;
        c.gridy = 4;
        board.add(player1Undo, c);
        
        if (controllor.checkWinState() == 1) 
        {
            displayMessage .setText("Player 1: " + controllor.getMancalaA() + " v.s. "+ "Player 2: " + controllor.getMancalaB() + " --------------- Player 1 Wins!");
            JOptionPane.showMessageDialog(board, "Player 1: " + controllor.getMancalaA() + " v.s. "+ "Player 2: " + controllor.getMancalaB() + " --------------- Player 1 Wins!");
        } 
        else if (controllor.checkWinState() == 2) 
        {
            displayMessage .setText("Player 1: " + controllor.getMancalaA() +" v.s. "+ "Player 2: " + controllor.getMancalaB() + " --------------- Player 2 wins!");
            JOptionPane.showMessageDialog(board, "Player 1: " + controllor.getMancalaA() + " v.s. "+ "Player 2: " + controllor.getMancalaB() + " --------------- Player 2 wins!");
        } 
        else if (controllor.checkWinState() == 3)
        {
        	displayMessage .setText("Player 1: " + controllor.getMancalaA() + " v.s. "+ "Player 2: " + controllor.getMancalaB() + " Draw");
        	JOptionPane.showMessageDialog(board, "Player 1: " + controllor.getMancalaA() + " v.s. "+ "Player 2: " + controllor.getMancalaB() + " --------------- Draw");
        }
        else if (controllor.checkTurnPlayerA()) 
        {
            displayMessage.setText("Player 1");
        } else 
        {
            displayMessage.setText("Player 2");
        }
        
        board.repaint();
    }

    /**
     * take controller which means a change has been made, update data
     * and re paint the board
     * @param c - GameControllor
     */
    void setData(GameControllor c) {
        this.controllor = c;
        setupBoard();
    }
    
}
