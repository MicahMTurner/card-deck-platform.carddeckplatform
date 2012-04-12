package logic.host;


import java.util.ArrayList;
import java.util.Stack;

import client.controller.actions.DealCardAction;

import war.actions.RecieveCardAction;
import war.actions.TurnAction;






import communication.messages.Message;
import communication.server.ConnectionsManager;
import communication.server.Connection;

import logic.client.Game;
import logic.client.Player;
import logic.client.Player.Position;



public class Host implements Runnable{
	
	private final int maxPlayers=4;
	public static ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private Stack<Position> availablePositions;
	private Game game;

	//synchronized private boolean startGameFlag=false;
	int playersRdy=0;
	

			
	public Host(Game game) {	
		availablePositions=new Stack<Player.Position>();
		availablePositions.add(Player.Position.RIGHT);
		availablePositions.add(Player.Position.LEFT);
		availablePositions.add(Player.Position.TOP);
		availablePositions.add(Player.Position.BOTTOM);
		
		this.game=game;
		
	}

	
	public void addPlayer(String player, String id){
		players.add(new Player(player,id));
	}
	
	public void waitForPlayers(){
		//while(players.size()<game.getPrefs().getMinPlayers()){
		while(ConnectionsManager.getConnectionsManager().getNumberOfConnections()<game.getPrefs().getMinPlayers()){
			System.out.println("waiting for player " + ConnectionsManager.getConnectionsManager().getNumberOfConnections() + "/" + game.getPrefs().getMinPlayers() + " " + String.valueOf(players.size()<game.getPrefs().getMinPlayers()));
			ConnectionsManager.getConnectionsManager().connectPlayer(availablePositions.pop(),game.toString(),players);
	    }
		//TODO send to GUI enable start game button
		
		
	}
	
	@Override
	public void run() {

		waitForPlayers();
		System.out.println("got all players");
		try {
			Thread.sleep(2000);
		
			game.initiate();
			
			Thread.sleep(2000);
			
			
			System.out.println("game initiated");
			game.getLogic().dealCards(game.getCards(), players);
			
			Thread.sleep(2000);
			//ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(0).getHand(),4)));
			//ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(1).getHand(),3)));
			System.out.println("cards dealt");
			
			ConnectionsManager.getConnectionsManager().sendTo(new Message(new TurnAction()), game.nextInTurn());
			
			Thread.sleep(2000);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//public void playerLeft(Player player){
		//TODO add compare method in player
	//	players.remove(player);
	//}
	
	//public void playerRdy(Player player){
		
	//	playersRdy++;
	//	int totalPlayers=players.size();		
	//	if (playersRdy==totalPlayers){
	//		if (totalPlayers>=game.getMinPlayers()){
				//start the game
	//			game.start();
	//		}
	//	}
	//}	
	
	//public void kickPlayer(Player player){
		//TODO add - disconnect player
	//	playerLeft(player);
	//}
	

	//public void waitForRdy(){
		//while all players not rdy, wait for them to be rdy
		//if someone is rdy , ping the other ones to notify them
		
	//}	
	
}
