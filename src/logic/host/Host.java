package logic.host;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import carddeckplatform.game.GameStatus;

import communication.server.ConnObj;
import communication.server.Server;
import communication.server.ServerConnections;
import communication.server.ServerTask;

import logic.client.Game;
import logic.client.Player;

import war.War;
import war.WarPrefs;

public class Host implements Runnable{
	private final int maxPlayers=4;
	private ArrayList<Player> players;
	private Game game=new War();
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
			Server.connectAPlayer();
	    }
		//TODO send to GUI enable start game button
		//while (!startGameFlag || players.size()<maxPlayers){
		//	Server.connectAPlayer();
		//}
		
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
