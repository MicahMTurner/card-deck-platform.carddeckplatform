package logic.host;


import java.util.ArrayList;



import communication.server.ConnectionsManager;
import communication.server.Connection;

import logic.client.Game;
import logic.client.Player;



public class Host implements Runnable{
	
	private final int maxPlayers=4;
	private ArrayList<Player> players;
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	private Game game;

	//synchronized private boolean startGameFlag=false;
	int playersRdy=0;
	
	public Host(Game game) {		
		players=new ArrayList<Player>();
		this.game=game;
	}
	
	public void addPlayer(String player, String id){
		players.add(new Player(player,id));
	}
	
	public void waitForPlayers(){
		while(players.size()<game.getPrefs().getMinPlayers()){
			ConnectionsManager.getConnectionsManager().connectPlayer();
	    }
		//TODO send to GUI enable start game button
		
		
	}
	
	@Override
	public void run() {
		waitForPlayers();
		game.initiate();
		
		
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
