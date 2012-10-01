package logic.host;


import java.util.Stack;
import java.util.concurrent.CountDownLatch;

import logic.client.Game;
import utils.Player;
import utils.Position;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import carddeckplatform.game.GameActivity;
import carddeckplatform.game.R;
import carddeckplatform.game.gameEnvironment.GameEnvironment;

import communication.actions.Turn;
import communication.link.HostGameDetails;
import communication.link.TcpIdListener;
import communication.messages.Message;
import communication.server.ConnectionsManager;




public class Host implements Runnable{
	private TcpIdListener tcpIdListener;

	public static boolean hostStartedGame;
	private Stack<Position.Player> availablePositions;
	private static Game game;
	private Dialog minPlayerAchievedDialog;
	private volatile boolean shutDown;
	private CountDownLatch cdl;
	
	
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
		minPlayerAchievedDialog=null;
		cdl=new CountDownLatch(1);
		
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
					minPlayerAchievedDialog=dialog;
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
					
					dialog.setContentView(R.layout.startgamedialog);
					Button button = (Button)dialog.findViewById(R.id.startGameButton);
					//start game button click listener 
					button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							hostStartedGame=true;
							//GameActivity.enableStartButton=false;
							ConnectionsManager.getConnectionsManager().stopListening();						
							dialog.dismiss();						
							
						}
					});
					button =  (Button)dialog.findViewById(R.id.startGameHideButton);
					//minimize button click listener
					button.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {		
							dialog.dismiss();						
							
						}
					});
					dialog.show();
					cdl.countDown();
					GameActivity.enableStartButton=true;
					
				}
			});			
		}else{
			if (ConnectionsManager.getConnectionsManager().getNumberOfConnections()==game.maxPlayers()){
				cdl.countDown();
			}
		}
	}
	public void waitForPlayers() throws Exception{

		ConnectionsManager.getConnectionsManager().connectHostingPlayer(availablePositions.pop(),game.toString(),game.getPlayers(), game.getFreePlayProfile());
		popDialogIfMinPlayers();
		while(ConnectionsManager.getConnectionsManager().getNumberOfConnections()<game.getNumberOfParticipants() && !hostStartedGame){
			ConnectionsManager.getConnectionsManager().connectPlayer(availablePositions.pop(),game.toString(),game.getPlayers(), game.getFreePlayProfile());
			popDialogIfMinPlayers();
			if (shutDown){
				throw new Exception("server shutting down");
			}
	    }
		cdl.await();
		if (minPlayerAchievedDialog!=null && ConnectionsManager.getConnectionsManager().getNumberOfConnections()==game.getNumberOfParticipants()){
			if (minPlayerAchievedDialog.isShowing()){
				minPlayerAchievedDialog.dismiss();
			}
		}
		GameActivity.enableStartButton=false;
	}
	
	@Override
	public void run() {

		try {
			tcpIdListener.start();
			waitForPlayers();
			tcpIdListener.stop();
			startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public static void reArrangeQueue(int nextPlayerId) {
		game.reArrangeQueue(nextPlayerId);
		
	}
	
	public static void startGame(){
		
		System.out.println("got all players");
		game.initiate();		
		game.resetRoundNumber();
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
