package logic.client;

import handlers.PlayerEventsHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import communication.actions.EndRoundAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;



import carddeckplatform.game.gameEnvironment.GameEnvironment;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.controller.ClientController;
import client.gui.entities.Droppable;

import utils.Button;
import utils.DeckArea;
import utils.Deck;
import utils.GamePrefs;
import utils.Pair;
import utils.Player;
import utils.Position;
import utils.Public;





public abstract class Game {	
	//i'm first in the list
	
//	public static GamePrefs receivedGamePrefs=null;
	
	protected ArrayList<Player> players = new ArrayList<Player>();
	protected Queue<utils.Position.Player> turnsQueue=new LinkedList<utils.Position.Player>();
	protected ArrayList<Droppable> droppables=new ArrayList<Droppable>();
	//The number of players the host would accept before starting the game.  
	protected int numberOfParticipants=0; 
	protected ArrayList<Button> buttons=new ArrayList<Button>();
	//private ToolsFactory tools=new DefaultTools();
	//private Player.Position currentTurn;
	protected Deck deck;
	private boolean firstRound;
	private Position.Player first;
	
	  
	

	//protected abstract Player createPlayer(String userName, Position.Player position);
	public abstract Deck getDeck();
	//what to do when round has ended
	public abstract Integer onRoundEnd();
	//the order of the players turns 
	protected abstract Queue<utils.Position.Player> setTurns();
	//the minimal players count
	public abstract int minPlayers();
	
	//how many cards to split
	public abstract int cardsForEachPlayer();
	
	//the game split cards on the begginng of the game
	public abstract void dealCards();	
	
	
	public abstract void setLayouts();
	/**
	 * game id
	 */
	@Override	
	public abstract String toString();
	//the game create player according to his hander
	public abstract Player getPlayerInstance(PlayerInfo playerInfo, utils.Position.Player position,int uniqueId);	
	public Pair<ArrayList<Droppable>,ArrayList<Button>> getLayouts(){
		return new Pair<ArrayList<Droppable>,ArrayList<Button>>(this.droppables,this.buttons);
	}
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
	
	public int getNumberOfParticipants() {
		if(numberOfParticipants==0)
			return minPlayers();
		return numberOfParticipants;
	}
	
	public void setupTurns(){
		turnsQueue=setTurns();
		if (turnsQueue!=null){
			first=turnsQueue.peek();
		}
	}
	
	public Game() {
		first=null;
		firstRound=true;		
		turnsQueue=setTurns();
		if (turnsQueue!=null){
			first=turnsQueue.peek();
		}
		//if(GameEnvironment.get().getPlayerInfo().isServer())
		loadPrefs();
		//clearEmptyPositions();	
		
//		if(receivedGamePrefs!=null)
//			applyReceivedPrefs(receivedGamePrefs);
		
//		try {
//			setLayouts();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		
		
		
		
	}
	
	private void clearEmptyPositions() {
		if (turnsQueue!=null){
			ArrayList<Position.Player> availablePos=new ArrayList<Position.Player>();
			for (Player player : players){
				availablePos.add(player.getGlobalPosition());
			}
			for (Position.Player position : turnsQueue){
				if (!availablePos.contains(position)){
					turnsQueue.remove(position);
				}
			}
		}		
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
			
//			if (next.equals(first) && !firstRound){
//				ConnectionsManager.getConnectionsManager().sendToAllExcptMe((new Message(new EndRoundAction())),players.get(0).getId());
//				Integer nextPlayerId=onRoundEnd();
//				if (nextPlayerId==null){
//					next=null;
//				}else{
//					while (next.getId()!=nextPlayerId){
//						turnsQueue.add(next);
//						next=turnsQueue.poll();
//					
//					}
//				}				
//			}
			if (next!=null){
				turnsQueue.add(next);
			}
			
		}
		
		firstRound=false;
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
		players.add(getPlayerInstance(playerInfo,position,uniqueId));
		
	}

	public void positionUpdate(Player player, Position.Player newPosition) {
		Position.Player oldPosition=getMe().getGlobalPosition();
		Player swappedWith=(Player) ClientController.get().getZone(newPosition.getRelativePosition(oldPosition));
		
		if (swappedWith==null){
			player.setGlobalPosition(newPosition);
		}else{			
			swapGlobalPositions(player,swappedWith);
			
		}
		setRelativePositions(player,swappedWith,oldPosition);

	}
	private void setRelativePositions(Player player,Player swappedWith, Position.Player oldPosition){
		//check if I moved
				if (player.equals(getMe()) || swappedWith.equals(getMe())){
					
					for (int i=1;i<players.size();i++){				
						players.get(i).setRelativePosition(player.getGlobalPosition());
					}
					//re arrange droppables
					for (Droppable droppalbe : droppables){
			    		//set public zone according to my position
						droppalbe.setPosition(droppalbe.getPosition().reArrangeRelativePosition(oldPosition, getMe().getGlobalPosition()));
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
	public void reArrangeQueue(int nextPlayerId) {
		Position.Player startingPlayer=null;
		for (Position.Player pos : Position.Player.values()){
			if (pos.getId()==nextPlayerId){
				startingPlayer=pos;
				break;
			}
		}
		Position.Player next=turnsQueue.poll();
		while (!next.equals(startingPlayer)){
			next=turnsQueue.poll();
		}		
		
	}
	public String getPrefsName(){
		return "";
	}
	
	public void loadPrefs(){
		
	}
	
	public void applyReceivedPrefs(GamePrefs gamePrefs){
		
	}
	
	public GamePrefs getPrefs() {
		// TODO Auto-generated method stub
		return null;
	}

}
