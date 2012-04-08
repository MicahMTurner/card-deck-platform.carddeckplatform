package logic.client;

import java.util.ArrayList;
//do this as a class and then just use set and stuff or even just use the methods?!
public interface GamePrefs {
	
	public enum Player{
		HOST,LEFT,RIGHT,UP,DOWN
	}
	public int getMinPlayers();
	//add status for random or static cards
	/**
	 * 0 for dividing cards among all players
	 * @return
	 */
	public int cardsForEachPlayer();

	public Player startingPlayer();
	
	//array list specifying the order of the turns
	public ArrayList<Player> turnsMethod();
}
