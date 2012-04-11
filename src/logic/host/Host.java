package logic.host;


import java.util.ArrayList;
import java.util.Stack;






import communication.server.ConnectionsManager;
import communication.server.Connection;

import logic.client.Game;
import logic.client.Player;
import logic.client.Player.Position;



public class Host implements Runnable{
	
	private final int maxPlayers=4;
	public static ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private static Stack<Position> availablePositions;
	private Game game;

	//synchronized private boolean startGameFlag=false;
	int playersRdy=0;
	
	public static Position getAvailablePosition() {
		return availablePositions.pop();
	}
			
	public Host(Game game) {	
		availablePositions=new Stack<Player.Position>();
		this.availablePositions.add(Player.Position.RIGHT);
		this.availablePositions.add(Player.Position.LEFT);
		this.availablePositions.add(Player.Position.TOP);
		this.availablePositions.add(Player.Position.BOTTOM);
		
		this.game=game;
		
	}

	
	public void addPlayer(String player, String id){
		players.add(new Player(player,id));
	}
	
	public void waitForPlayers(){
		//while(players.size()<game.getPrefs().getMinPlayers()){
		while(ConnectionsManager.getConnectionsManager().getNumberOfConnections()<game.getPrefs().getMinPlayers()){
			System.out.println("waiting for player " + ConnectionsManager.getConnectionsManager().getNumberOfConnections() + "/" + game.getPrefs().getMinPlayers() + " " + String.valueOf(players.size()<game.getPrefs().getMinPlayers()));
			ConnectionsManager.getConnectionsManager().connectPlayer(availablePositions);
	    }
		//TODO send to GUI enable start game button
		
		
	}
	
	@Override
	public void run() {
		//---------------------- must fix that ------------------
		players.add(new Player("",""));
		players.add(new Player("",""));
		
		players.get(0).setPosition(Position.BOTTOM);
		players.get(1).setPosition(Position.TOP);
		//-------------------------------------------------------
		waitForPlayers();
		System.out.println("got all players");
		game.initiate();
		System.out.println("game initiated");
		game.getLogic().dealCards(game.getCards(), players);
		System.out.println("cards dealt");
		
		
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
