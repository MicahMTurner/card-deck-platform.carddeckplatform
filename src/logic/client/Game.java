package logic.client;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;



import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.gui.entities.Droppable;

import utils.DeckArea;
import utils.Deck;
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
	protected Deck deck;	
	
	 

	//protected abstract Player createPlayer(String userName, Position.Player position);
	public abstract Deck getDeck();

	//the order of the players turns 
	public abstract Queue<utils.Position.Player> setTurns();
	//the minimal players count
	public abstract int minPlayers();
	//how many cards to split
	public abstract int cardsForEachPlayer();
	
	//the game split cards on the begginng of the game
	public abstract void dealCards();	
	
	
	public abstract void getLayouts(ArrayList<Droppable>publics);
	/**
	 * game id
	 */
	@Override	
	public abstract String toString();
	//the game create player according to his hander
	public abstract PlayerEventsHandler getPlayerHandler();	

	public String getClassName(){
		return getClass().getName();
	}
	//return my player object
	public Player getMe() {
		return players.get(0);
	}
	//create the deck of the game
	public void initiate(){
		deck=getDeck();	
	}
	
	
	public Game() {
		turnsQueue=setTurns();
	}
	//return the next player
	public utils.Position.Player nextInTurn(){
		utils.Position.Player next=null;
		if (turnsQueue!=null){
			ArrayList<Position.Player> availablePos=new ArrayList<Position.Player>();
			for (Player player : players){
				availablePos.add(player.getGlobalPosition());
			}
			next=turnsQueue.poll();
			while (!availablePos.contains(next)){
				next=turnsQueue.poll();
			}
			turnsQueue.add(next);
		}
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
	//player lost so we remove him from the list
	public void playerLost(int id){		
		turnsQueue.remove(id);
	}
	// 
	public void addMe(PlayerInfo playerInfo, utils.Position.Player position,int uniqueId) {
		players.add(new Player(playerInfo,position,uniqueId,getPlayerHandler()));
		
	}

	public void positionUpdate(Player player, Position.Player newPosition) {
		Player swappedWith=(Player) ClientController.get().getZone(newPosition);
		if (swappedWith==null){
			player.setGlobalPosition(newPosition);
		}else{			
			swapGlobalPositions(player,swappedWith);
			
		}
		setRelativePositions(player,swappedWith);

	}
	private void setRelativePositions(Player player,Player swappedWith){
		//check if I moved
				if (player.equals(getMe()) || swappedWith.equals(getMe())){
					
					for (int i=1;i<players.size();i++){				
						players.get(i).setRelativePosition(player.getGlobalPosition());
					}
				}else{
					//other person moved
					player.setRelativePosition(getMe().getGlobalPosition());
					//check if swapped with existing player
					if (swappedWith!=null){
						//true, set existing player relative position
						swappedWith.setRelativePosition(getMe().getGlobalPosition());
					}
					
					
				}
	}

	private void swapGlobalPositions(Player player, Player swappedWith) {
		Position.Player temp=swappedWith.getGlobalPosition();
		swappedWith.setGlobalPosition(player.getGlobalPosition());
		player.setGlobalPosition(temp);
	}

}
