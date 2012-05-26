package logic.host;


import java.util.ArrayList;
import java.util.Stack;

import utils.Player;
import utils.Position;
import communication.actions.Turn;
import communication.link.Streams;
import communication.messages.Message;
import communication.server.ConnectionsManager;
import logic.client.Game;




public class Host implements Runnable{
	
	private final int maxPlayers=4;
	//public static ArrayList<Player> playersInfo = new ArrayList<Player>();	
	
	private Stack<Position.Player> availablePositions;
	private ArrayList<PlayersInfo> playersInfo;
	private static Game game;

	//synchronized private boolean startGameFlag=false;
	int playersRdy=0;
	private class PlayersInfo implements Comparable<String>{
		private String name;
		private float azimute;
		private Position.Player position;
		
		
		@Override
		public int compareTo(String another) {
			if (name.equals(another)){
				return 0;
			}
			return 1;
		}
		
		
	}

			
	public Host(Game game) {	
		availablePositions=new Stack<Position.Player>();
		availablePositions.add(Position.Player.RIGHT);
		availablePositions.add(Position.Player.LEFT);
		availablePositions.add(Position.Player.TOP);
		availablePositions.add(Position.Player.BOTTOM);
		
		Host.game=game;
		this.playersInfo=new ArrayList<Host.PlayersInfo>();
		
	}
	public static void addPlayer(Player playerInfo){
		//playersInfo.add(playerInfo);
		game.addPlayer(playerInfo);
	}
	public static Position.Player nextInTurn(){
		return game.nextInTurn();
	}
	public static void playerLost(Position.Player position){
		game.playerLost(position);
	}
	
	public void waitForPlayers(){
		//ConnectionsManager.getConnectionsManager().connectHostingPlayer(availablePositions.pop(),game.toString(),game.getPlayers());
		while(ConnectionsManager.getConnectionsManager().getNumberOfConnections()<game.minPlayers()){
			ConnectionsManager.getConnectionsManager().connectPlayer(availablePositions.pop(),game.toString(),game.getPlayers());
	    }
		//TODO send to GUI enable start game button
		
		
	}
	
	@Override
	public void run() {

		waitForPlayers();
		System.out.println("got all players");
		game.initiate();		
		
		System.out.println("game initiated");
		game.dealCards();		
		
		System.out.println("cards dealt");
		// send the turn action if the game is turned base card game.
		Position.Player next=game.nextInTurn();
		if (next!=null){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(next)));
		}else{
			for (Player player: game.getPlayers()){
				ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(player.getGlobalPosition())));
			}
		}
		
		
	}
	

	
}
