/**
 * Controller class, make changes and update to the model(data), and then display by view(MainView) 
 * Contains all the rules of the game
 * @author GuoHua Jiang
 */

import java.util.ArrayList;
import org.omg.CORBA.DynAnyPackage.InvalidValue;

public class GameControllor
{
	public int undoCounterA; 
	public int undoCounterB; 
	public Data data; 
	public Data prevoiusData;
	private MainView view;
	
	/**
	 * get counter of times of undo
	 * @return int - undo counter number of player1
	 */
	public int getUndoCounterA()
	{
		return undoCounterA;
	}
	
	/**
	 * get counter of times of undo
	 * @return int - undo counter number of player2
	 */
	public int getUndoCounterB()
	{
		return undoCounterB;
	}
	
	/**
	 * Using the user selected information to start a new game 
	 * @param numOfMarbles the number of marbles
	 * @param v the view that contains the user selected style
	 */
	public GameControllor(int numOfMarbles, MainView v)
	{
		undoCounterA = 0;
		undoCounterB = 0; 
		data = new Data(numOfMarbles);
		prevoiusData = data;
        view = v;
	}
	
	/**
	 * Start a new game
	 * @param marbles the number of the marbles
	 */
	public void newGame(int marbles)
	{
        data.numberOfMarbles = marbles;
		undoCounterA = 0;
		undoCounterB = 0;
		data = new Data(data.numberOfMarbles);
		prevoiusData = data;
		data.isPlayerA = true;
        view.setupBoard();
	}
	
	/**
	 *  Move the marble and return the current state of the game
	 * @param index the index the hole that is clicked by the player
	 * @return an integer representing the different invalid move. e.g. 0: user clicks the mancala. 1: user clicks the opponent's board. 
	 * 2: users clicks the empty hole
	 */
	public int makeMove(int index)
	{
		prevoiusData = data.Copy();
		if(index == data.MANCALA_A || 
				index == data.MANCALA_B) {
			return 0;
		}
		
		if(checkIfValidMove(index)) {
			return 1;
		}
		
		if(data.getStoneInHole(index) == 0)
		{
			return 2;
		}
		
		int endingIndex = data.move(index);
		
		System.out.println("endingIndex is " + endingIndex);
		
		checkIfHoleWasEmpty(endingIndex); 
		
		// give player A another turn
		if(data.isPlayerA && endingIndex == data.MANCALA_A)
		{
			data.isPlayerA = true;
		} else if(!data.isPlayerA && endingIndex == data.MANCALA_B)
		{
			data.isPlayerA = false;
		} else {
			data.isPlayerA = !data.isPlayerA;
		}
                view.setupBoard();
        return 3;

	}
	
	/**
	 * Will revert back the the previous known turn each player 
	 * is allows 3 undo clicks after that no matter how many 
	 * times the undo is pressed nothing will occur. 
	 */
	public void undo()
	{
		if(data.isPlayerA && undoCounterA < 3) {
			undoCounterA++;
			data = prevoiusData;
		}
		
		if(!data.isPlayerA && undoCounterB < 3) {
			undoCounterB++;
			data = prevoiusData;
		}
        view.setupBoard();
	}
	
	/**
	 * If player A won returns 1
	 * if player B won returns 2
	 * if draw return 3
	 * if game in progress returns 4 
	 * @return an integer representing different state of the game
	 */
	public int checkWinState()
	{
		if(data.checkWinState() != 0)
		{
			int mA = getMancalaA(); 
			int mB = getMancalaB(); 
			if(mA > mB)
				return 1;
			else if(mB > mA)
				return 2;
			else 
				return 3;
		}
		return 4;
	}
	
	/**
	 * returns an array list in the order of display 
	 * @return arrayList on integer representing pits of A 
	 */
	public ArrayList<Integer> getPlayerAMarble() 
	{
		return data.getMarblesA();
	}
	
	/**
	 * returns an array list in the order of display 
	 * @return arrayList on integer representing pits of B 
	 */
	public ArrayList<Integer> getPlayerBMarble() 
	{
		return data.getMarblesB();
	}
	
	/**
	 * @return number of marbles in player A mancala
	 */
	public int getMancalaA()
	{
		return data.getStoneInHole(data.MANCALA_A);
	}
	
	/**
	 * @return number of marbles in player B mancala
	 */
	public int getMancalaB()
	{
		return data.getStoneInHole(data.MANCALA_B);
	}
	
	/**
	 * Returns true if is player A turn
	 * @return boolean 
	 */
	public boolean checkTurnPlayerA() {
		return data.isPlayerA; 
	}
	
	/**
	 * Helper Function of make move 
	 * @param pitIndex
	 * @return boolean
	 */
	private boolean checkIfValidMove(int pitIndex)
	{
		if(data.isPlayerA && pitIndex >=0 && pitIndex <=6 )
			return true;
		if(!data.isPlayerA && pitIndex >=7 && pitIndex <=12 )
			return true;
		
		return false;
	}
	
	/**
	 * This function checks if a hole was empty and steels 
	 * @param endingIndex the ending index 
	 */
	private void checkIfHoleWasEmpty(int endingIndex) 
	{
		if(data.isPlayerA && endingIndex >=7 && endingIndex <=12 
				&& data.getStoneInHole(endingIndex) == 1) {
			// steal stones from pit B add to mancala A
			int adjIndex = getAdjacentIndex(endingIndex);
			int stoneForMancal = data.getStoneInHole(adjIndex);
			data.removeAllStonesFromHole(adjIndex);
			data.addMarblesToMancala(stoneForMancal);
		}
		
		if(!data.isPlayerA && endingIndex >=0 && endingIndex <=5
				&& data.getStoneInHole(endingIndex) == 1) {
			// steal stones from pit A add to mancala B
			int adjIndex = getAdjacentIndex(endingIndex);
			System.out.println("adj index " + adjIndex + " " + endingIndex);
			int stoneForMancal = data.getStoneInHole(adjIndex);
			data.removeAllStonesFromHole(adjIndex);
			data.addMarblesToMancala(stoneForMancal);
				}
	}
	
	/**
	 *  Find adjacent the index 
	 * @param endingIndex the ending index
	 * @return the adjacent index
	 */
	private int getAdjacentIndex(int endingIndex) 
	{
			return 12 - endingIndex ;
	}
}
