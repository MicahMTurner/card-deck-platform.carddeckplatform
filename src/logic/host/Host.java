package logic.host;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Stack;

import carddeckplatform.game.gameEnvironment.GameEnvironment;

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
	//private ArrayList<PlayersInfo> playersInfo;
	private static Game game;
	private volatile boolean shutDown;
	//synchronized private boolean startGameFlag=false;
	int playersRdy=0;


	public void shutDown(){
		shutDown=true;
		game.getPlayers().clear();
		availablePositions.clear();
		ConnectionsManager.getConnectionsManager().shutDown();
//		ServerSocket ss=GameEnvironment.get().getTcpInfo().getServerSocket();
//		if (ss!=null){
//			try {
//				ss.close();
//			} catch (IOException e) {				
//				e.printStackTrace();
//			}
//		}
	}
	public Host(Game game) {	
		availablePositions=new Stack<Position.Player>();
		availablePositions.add(Position.Player.RIGHT);
		availablePositions.add(Position.Player.LEFT);
		availablePositions.add(Position.Player.TOP);
		availablePositions.add(Position.Player.BOTTOM);
		shutDown=false;
		Host.game=game;
		//this.playersInfo=new ArrayList<Host.PlayersInfo>();
		
	}
	public static void addPlayer(Player playerInfo){
		//playersInfo.add(playerInfo);
		game.addPlayer(playerInfo);
	}
	public static Position.Player nextInTurn(){
		return game.nextInTurn();
	}
	public static void playerLost(int id){
		game.playerLost(id);
	}
	
	public void waitForPlayers() throws Exception{
		ConnectionsManager.getConnectionsManager().connectHostingPlayer(availablePositions.pop(),game.toString(),game.getPlayers());
		while(ConnectionsManager.getConnectionsManager().getNumberOfConnections()<game.minPlayers()){
			ConnectionsManager.getConnectionsManager().connectPlayer(availablePositions.pop(),game.toString(),game.getPlayers());
			if (shutDown){
				throw new Exception("server shutting down");
			}
	    }
	}
	
	@Override
	public void run() {

		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	

	
}
