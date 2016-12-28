import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is a representation of the board  
 *
 */
public class Data 
{
	/* This board is on 1D representation of the board with 
	player B pits ranging from 0-5 and 6th position for 
	mancala Player A from 7-12, 13th for mancala A 
	*/
	public ArrayList<Integer> board; 
	// size of board 
	public int SIZE = 14; 
	public int MANCALA_A = 13; 
	public int MANCALA_B = 6;
    public int numberOfMarbles;
	public boolean isPlayerA; 

	/**
	 * Constructor:
	 * The number of marbles used to build the initial 
	 * board, the enforcement of the rules is done by 
	 * GameControllor which only allows 3 or 4 stones
	 * set Player A always goes first. 
	 * @param num the number of marbles to be added
	 */
	public Data(int num)
	{
		board = new ArrayList<Integer>(SIZE);
		setStones(num);
		isPlayerA = true;
		numberOfMarbles = num;
	}
	
	/**
	 * empty constructor 
	 */
	public Data() {
		board = new ArrayList<Integer>(14);
	}

	/**
	 * returns a  copy of the state of the 
	 * board. 
	 * @return copy of the current data
	 */
	public Data Copy() 
	{
		Data tmp = new Data(numberOfMarbles); 
		tmp.isPlayerA = this.isPlayerA;
		Collections.copy(tmp.board, board);
		return tmp;
	}
	
	/**
	 * Distributes the marbles to all of the pits 
	 * and initialize the 0 to two sides of the macalas
	 * 0 stones  
	 * @param numOfMarble the number of marbles 
	 */
	public void setStones(int numOfMarble) 
	{
		for(int i = 0; i < SIZE; i++) {
			if(i == MANCALA_A || i == MANCALA_B) {
				board.add(0);
			} else {
				board.add(numOfMarble);
			}
		}
	}
	
	/**
	 * The move function is called by the GameControllor class
	 * The GameControllor checks if all the pits are empty before
	 * and after the move function to see if game is won.
	 * GameControllor also checks if the player will steal the
	 * mancalas or if player will get extra turn. 
	 * @param index the index of the hole
	 * @return the last index the player left
	 */
	
	public int move(int index) 
	{
		int stonesToDistribute = board.get(index);
		if(stonesToDistribute > 0) {
		board.set(index,0);
		index++;
		while(stonesToDistribute != 0){
			if(isPlayerA && index == MANCALA_A){
				System.out.println("set mancala A to " + (board.get(index)+1));
				board.set(index,board.get(index)+1);
				stonesToDistribute--;
			}else if(!isPlayerA && index == MANCALA_B){
				board.set(index,board.get(index)+1);
				stonesToDistribute--;
			} 
			else {
				board.set(index,board.get(index)+1);
				stonesToDistribute--;
			}	
			
			if(index == 13) 
			{
				if(stonesToDistribute == 0 && isPlayerA) 
				{
					return 13;
				}
				index = -1;
			}
			index++;
		}
		
		return index-1;
		} else
			return -1;
	}
	
  /**
   * get number of stones in a pit
   * @param index the index of the current hole 
   * @return the number of marble on that hole 
   */
  public int getStoneInHole(int index) 
  {
	  return board.get(index);
  }
  
  /**
   * remove all marbles from a particular hole
   * @param index the index of a particular hole 
   */
  public void removeAllStonesFromHole(int index) 
  {
	  board.set(index, 0);
  }
  
  /**
   * 
   * @return ArrayList of integers of pits of playerA
   */
  public ArrayList<Integer> getMarblesB()
  {
	  ArrayList<Integer> b = new ArrayList<Integer>(); 
	  for(int i = 5 ; i >= 0; i--) {
	    	b.add(board.get(i));    	    	
  	}
	  return b;
  }
  
  /**
   * @return ArrayList of integers of pits of playerB
   */
  public ArrayList<Integer> getMarblesA() 
  {
	  ArrayList<Integer> a = new ArrayList<Integer>(); 
	  for(int i = 7 ; i < 13; i++) {
	    	a.add(board.get(i));    	    	
  	}
	  return a;
  }
  
  /**
   * If player A won returns 1
   * if player B won returns -1
   * if tie return 0
   * if in progress break 
   * @return int representing state of game 
   */
  public int checkWinState() 
  {
	  boolean isEmpty = true;
	  for(int i = 0 ; i < 6; i++){
	    if(board.get(i) == 0) {
	    	isEmpty = true;
	    }
	    else {
	    	isEmpty = false;
	    	break;
	    }
  	  }
	  // player B won
	  if(isEmpty) {
		  return -1;
	  }
	  
	  isEmpty = true;
	  for(int i = 7 ; i < 13; i++) {
	    if(board.get(i) == 0) {
	    	isEmpty = true;
	    }
	    else {
	    	isEmpty = false;
	    	break;
	    }
  	  }
	  // player A just won
	  if(isEmpty) {
		  return 1;
	  }
	  //draw
	  return 0;
  }
  
  /**
   * Adds stones to mancala, set stones to the board 
   * @param marble the number of marble that added to the mancala
   */
  public void addMarblesToMancala(int marble)
  {
	  if(isPlayerA)
		  board.set(MANCALA_A, board.get(MANCALA_A)+ marble);
	  else
		  board.set(MANCALA_B, board.get(MANCALA_B)+ marble);
  }
}
