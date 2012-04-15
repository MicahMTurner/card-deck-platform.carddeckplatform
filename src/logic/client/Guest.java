package logic.client;

import java.util.ArrayList;

public class Guest {
	private Player me;
	private Game game;
	protected ArrayList<Player> players;
	
	
	public Guest(Game game) {		
		players=new ArrayList<Player>();
		this.game=game;
	}
	
	public void playerConnected(Player player){
			players.add(player);
	}
	
	public void playerLeft(Player player){
		//TODO add compare method in player
		players.remove(player);
	}
	
	public void readyUp(){		
		me.setReady(true);			
	}	
	
	
	
}
