package logic.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import utils.Player;
import utils.Position;
import utils.Public;





public abstract class Game {	
	//i'm first in the list
	protected ArrayList<Player> players = new ArrayList<Player>();
	protected Queue<utils.Position.Player> turnsQueue=new LinkedList<utils.Position.Player>();
	protected ArrayList<Public> publics=new ArrayList<Public>();
	//private ToolsFactory tools=new DefaultTools();
	//private Player.Position currentTurn;
	protected AbstractDeck deck;	
	
	 

	//protected abstract Player createPlayer(String userName, Position.Player position);
	public abstract AbstractDeck getDeck();
	public abstract Queue<utils.Position.Player> setTurns();
	public abstract int minPlayers();
	public abstract int cardsForEachPlayer();
	
	
	public abstract void dealCards();	
	
	
	public abstract void getLayouts(ArrayList<Public>publics);
	/**
	 * game id
	 */
	@Override	
	public abstract String toString();
	public abstract Player createPlayer(String userName,utils.Position.Player position);	

	
	public Player getMe() {
		return players.get(0);
	}
	
	public void initiate(){
		deck=getDeck();	
	}
	
	
	public Game() {
		turnsQueue=setTurns();
	}
	
	public utils.Position.Player nextInTurn(){
		utils.Position.Player next=turnsQueue.poll();
		turnsQueue.add(next);
		return next;		
	}
	
	public void addPlayer(Player newPlayer) {
		if (!players.contains(newPlayer)){
			players.add(newPlayer);
		}
		
	}
	
	public ArrayList<Player> getPlayers(){
		return players;
	}	
	public void playerLost(Position.Player position){		
		turnsQueue.remove(position);
	}
	public void addMe(String userName, utils.Position.Player position) {
		players.add(createPlayer(userName, position));
		
	}
		
		
	

}
