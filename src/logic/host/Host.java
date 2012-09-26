package logic.host;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Stack;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

import carddeckplatform.game.CarddeckplatformActivity;
import carddeckplatform.game.GameActivity;
import carddeckplatform.game.R;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

import utils.Player;
import utils.Position;
import communication.actions.Turn;
import communication.link.HostGameDetails;
import communication.link.Streams;
import communication.link.TcpIdListener;
import communication.messages.Message;
import communication.server.ConnectionsManager;
import logic.client.Game;




public class Host implements Runnable{
	private TcpIdListener tcpIdListener;

	//public static ArrayList<Player> playersInfo = new ArrayList<Player>();	
	public static boolean hostStartedGame;
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
		tcpIdListener.stop();
	}
	
	public Host(Game game) {	

		shutDown=false;
		Host.hostStartedGame=false;
		Host.game=game;
		setPositions();
		tcpIdListener=new TcpIdListener(getHostGameDetails());
		//this.playersInfo=new ArrayList<Host.PlayersInfo>();
		
	}
	
	private void setPositions(){
		availablePositions=game.getPositions();
	}
	
	public static void addPlayer(Player playerInfo){
		//playersInfo.add(playerInfo);
		game.addPlayer(playerInfo);
	}
	public static Integer nextInTurn(){
		return game.nextInTurn();
	}
	public static void playerLost(int id){
		game.playerLost(id);
	}
	private void popDialogIfMinPlayers(){
		if (ConnectionsManager.getConnectionsManager().getNumberOfConnections()==game.minPlayers()
				&& game.minPlayers()<game.maxPlayers() && !hostStartedGame){
			//pop start game dialog
			GameEnvironment.get().getHandler().post(new Runnable() {				
				@Override
				public void run() {
					final Dialog dialog=  new Dialog((Context)GameActivity.getContext(),R.style.startGameDialogTheme);
					//dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					
					//dialog.getWindow().setBackgroundDrawableResource(Color.TRANSPARENT);
					
					dialog.setContentView(R.layout.startgamedialog);
					Button button = (Button)dialog.findViewById(R.id.startGameButton);
					button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							hostStartedGame=true;
							GameActivity.enableStartButton=false;
							ConnectionsManager.getConnectionsManager().stopListening();						
							dialog.dismiss();						
							
						}
					});
					button =  (Button)dialog.findViewById(R.id.startGameHideButton);
					button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {		
							dialog.dismiss();						
							
						}
					});
					dialog.show();
					GameActivity.enableStartButton=true;
					
				}
			});			
		}		
	}
	public void waitForPlayers() throws Exception{
		//boolean hostStartedGame=false;
		ConnectionsManager.getConnectionsManager().connectHostingPlayer(availablePositions.pop(),game.toString(),game.getPlayers(), game.getFreePlayProfile());
		popDialogIfMinPlayers();
		while(ConnectionsManager.getConnectionsManager().getNumberOfConnections()<game.getNumberOfParticipants() && !hostStartedGame){
			ConnectionsManager.getConnectionsManager().connectPlayer(availablePositions.pop(),game.toString(),game.getPlayers(), game.getFreePlayProfile());
			popDialogIfMinPlayers();
			if (shutDown){
				throw new Exception("server shutting down");
			}
	    }
	}
	
	@Override
	public void run() {

		try {
			tcpIdListener.start();
			waitForPlayers();
			tcpIdListener.stop();
			startGame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void reArrangeQueue(int nextPlayerId) {
		game.reArrangeQueue(nextPlayerId);
		
	}
	
	public static void startGame(){
		
		System.out.println("got all players");
		game.initiate();		
	
		System.out.println("game initiated");
		game.dealCards();		
		game.setupTurns();
		
		System.out.println("cards dealt");
		// send the turn action if the game is turned base card game.
		Integer next=game.nextInTurn();
		if (next!=null){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(next)));
		}else{
			for (Player player: game.getPlayers()){
				ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(player.getId())));
			}
		}
	}
	private HostGameDetails getHostGameDetails() {
		return new HostGameDetails(GameEnvironment.get().getPlayerInfo().getUsername()
		 				 , game.toString(), game.minPlayers(), game.maxPlayers(), game.instructions());
		
	}

	
}
