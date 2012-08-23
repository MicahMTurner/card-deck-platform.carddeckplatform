package client.controller;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;


import communication.actions.CardAdded;
import communication.actions.CardRemoved;
import communication.actions.DraggableMotionAction;
import communication.actions.EndDraggableMotionAction;
import communication.actions.EndRoundAction;
import communication.actions.EndTurnAction;
import communication.actions.Turn;
import communication.link.ServerConnection;
import communication.messages.EndRoundMessage;
import communication.messages.EndTurnMessage;
import communication.messages.Message;
import communication.messages.RequestCardMessage;

import utils.Button;
import utils.Card;
import utils.Pair;
import utils.Player;
import utils.Point;
import utils.Position;
import utils.Public;

import IDmaker.IDMaker;
import logic.client.Game;
import client.gui.entities.Droppable;
import carddeckplatform.game.GameActivity;
import carddeckplatform.game.TableView;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import durak.Durak;


public class ClientController implements Observer {
	
	
	private TableView gui;	
	private Game game;	
	
	private Send send;
	private GuiAPI guiAPI;
	
	
	

	public Game getLogic() {
		return game;
	}
	
	public TableView getGui() {
		return gui;
	}

	//-------Singleton implementation--------//
		private static class ControllerHolder
		{
			private final static ClientController controller=new ClientController();
		}
		
				
		/**
		 * get Controller instance
		 */
		public static ClientController get(){
			return ControllerHolder.controller;
		}

	
	//---------STATIC GETters for GUI and outgoing APIs-------//
		
		/**
		 * get Send API
		 * @return SendAPI instance
		 */
		public static Send sendAPI(){
			return ControllerHolder.controller.send;
		}
		/**
		 * get Send API
		 * @return SendAPI instance
		 */
		public static GuiAPI guiAPI(){
			return ControllerHolder.controller.guiAPI;
		}
	
	//constructor
	private ClientController(){				
		this.send=new Send();
		this.guiAPI=new GuiAPI();
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void setGui(TableView gui) {
		this.gui = gui;
	}
	
	
				
	
	
	
	//---------API for outgoing-----------//
	/**
	 * API for sending messages
	 * @author Yoav
	 *
	 */
	public class Send{
		
		private Send(){}
		
		public void endTurn(int playerId){
			gui.setUiEnabled(false);
			ServerConnection.getConnection().send(new EndTurnMessage(new EndTurnAction(playerId)));
		}
		public void cardAdded(Card card,int from,int to,int byWhomId){			
			ServerConnection.getConnection().send(new Message(new CardAdded(card,from,to,byWhomId)));
		}
		public void cardRemoved(ArrayList<Card> cards,String from){
			ServerConnection.getConnection().send(new Message(new CardRemoved(cards, from)));
		}
		public void endRound(){
			//Integer nextPlayerId=game.endRound();
			ServerConnection.getConnection().send(new Message(new EndRoundAction()));
			//ServerConnection.getConnection().send(new Message(new EndRoundAction()));
		}
		public void cardRevealed(Card card){
			//ServerConnection.getConnection().getMessageSender().send(new Message(new CardRevealAction()))
		}

		public void cardHidden(Card card) {
			
			
		}

		public void dragMotion(String username, int id, Point coord) {
			ServerConnection.getConnection().send(new Message(
					new DraggableMotionAction(GameEnvironment.get().getPlayerInfo().getUsername(),id, coord.getX(), coord.getY())));
			
		}
		public void endDragMotion(int id){
			ServerConnection.getConnection().send(new Message(new EndDraggableMotionAction(id)));
			
		}
		
		
		
	}
	
	//---------API for GUI options-----------//
	/**
	 * API for GUI options	
	 * @author Yoav
	 *
	 */
	public class GuiAPI{
		private GuiAPI() {}
//		public void moveCards(ArrayList<Card> cards,int toId,boolean revealWhileMoving,boolean revealAtEnd){
//			//gui.drawMovement(cards, toId,1000,10,revealWhileMoving,revealAtEnd);	
//		}
	}
	
	//---------Controller functionality-----------//

	
	public Droppable getZone(Position pos){
		//return gui.getDroppableById(IDMaker.getMaker().getIdByPos(pos.getRelativePosition(getMe().getGlobalPosition())));
		
		Droppable answer=gui.getDroppableByPosition(pos);//(pos.getRelativePosition(getMe().getGlobalPosition())));
		if (answer==null && LivePosition.get().isRunning()){
			//try to see if moved cause of live position
			answer=gui.getDroppableByPosition((pos.getRelativePosition(getMe().getGlobalPosition())));
		}
		return answer;
	}
	
	public Droppable getZone(int zoneID){	
		Droppable answer=gui.getDroppableById(zoneID);
		return answer;
	}
	
	
	public Card getCard(int cardId){
		return (Card)gui.getDraggableById(cardId);
	}
	
	public Pair<ArrayList<Droppable>,ArrayList<Button>>getLayouts() {
		return game.getLayouts();	
	}
	public void cardMoved(Card card,int from, int to, int byWhomId){
		gui.moveCard(card, from, to,byWhomId);		
	}
	public void startDraggableMotion(int cardId,int fromId, String username){
		gui.startDraggableMotion(username, cardId,fromId);
	}
	public void addPlayer(Player newPlayer) {
		newPlayer.setRelativePosition(game.getMe().getGlobalPosition());
		game.addPlayer(newPlayer);
		gui.addPlayer(newPlayer);
		
	}
	
	//public void setPosition(Position position) {
	//	game.getMe().setPosition(position);
	//	
	//}
	
	//public Position getPosition() {
	//	return game.getMe().getPosition();
	//}

	
		
	public void disableUi(){
		gui.setUiEnabled(false);
	}
	public void enableUi(){
		gui.setUiEnabled(true);
		gui.popToast("Your Move");
	}

	public void playerTurn(int playerId) {
		Player me=game.getMe();
		if (playerId==me.getId()){
			me.startTurn();
		}else{
			for (Player player : game.getPlayers()){
				if (player.getId()==playerId){
					player.setMyTurn(true);
					break;
				}
			}
		}
		//glow player icon/name
		gui.setPlayerTurn(gui.getDroppableById(playerId));//getZone(playerPosition.getRelativePosition(me.getGlobalPosition())));
	}

	//private void moveCard(Card card,Droppable from,Droppable to){		
	//	to.addCard(card);
	//	from.removeCard(card);		
	//	gui.moveCard(card,from,to);
	//}
	//public void runCardAnimation(CardLogic cardLogic, LogicDroppable logicDroppable, final long initialDelay, final long delay, final boolean revealedWhileMoving, final boolean revealedAtEnd, Table.GetMethod g){
	//	gui.moveDraggable(gui.getDraggableById(cardLogic.getId(), g), gui.getDroppableById(logicDroppable.getId()), initialDelay, delay, revealedWhileMoving, revealedAtEnd);
	//}

	//public boolean isMyTurn() {
	//	return gui.isUiEnabled();
	//}

	public void declareWinner() {
		ClientController.get().disableUi();
		gui.popToast("WINNER!");
		
	}
	
	public void declareLoser() {
		ClientController.get().disableUi();
		gui.popToast("LOSSER!");
		
	}

	public void popMessage(String displayMessage) {
		gui.popToast(displayMessage);
		
	}


	public void removeCards(ArrayList<Card> cards, String from) {
		gui.removeCards(cards,from);
		
	}
	
	
	
	public void clearCards(){
		gui.clearCards();
	}
	

	public void endRound() {		
		disableUi();
		Integer nextPlayerId=game.endRound();
		getMe().setMyTurn(false);
		if (nextPlayerId!=null){
			//if (GameEnvironment.get().getPlayerInfo().isServer()){
				game.reArrangeQueue(nextPlayerId);
			//}
			playerTurn(nextPlayerId);
		}
//		if(getMe().isMyTurn()){
//			//sendAPI().endRound();
//			
//			
//			if (nextPlayerId!=null){
//				getMe().setMyTurn(false);
//				sendAPI().endRound(nextPlayerId);
//				//ServerConnection.getConnection().send(new EndRoundMessage(nextPlayerId,new Turn(nextPlayerId)));
//				//playerTurn(nextPlayerId);
//			}
//			
//			
//			
//		}else{
//			game.endRound();
//		}
//		
		
	}
	
	public void requestCard(Integer fromId, int amount){
		ServerConnection.getConnection().send(new RequestCardMessage(ClientController.get().getMe(), fromId, amount));
	}
	
	public Player getMe(){
		if (game!=null){
			return game.getMe();
		}
		return null;
	}

	public void dealCards(ArrayList<Card> cards, int to) {
		gui.dealCards(cards, to);
	}
	 
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1!=null){
			boolean myTurn=(Boolean)arg1;
			if (myTurn){
				gui.setUiEnabled(true);
				gui.popToast("Your Move");
			}
			else{
				gui.setUiEnabled(false);
				send.endTurn(getMe().getId());								
			}
		}
		//if(arg0 instanceof Player)
		
	}

	public void positionUpdate(int playerId, utils.Position.Player newPosition) {
		Player player=(Player) gui.getDroppableById(playerId);
		
		game.positionUpdate(player,newPosition);
		gui.swapPositions(player,newPosition);
		
	}

	public void positionUpdate2(
			ArrayList<Pair<utils.Position.Player, utils.Position.Player>> movingList) {
		//contains player to move
		Player player;
		for (Pair<Position.Player, Position.Player> pair : movingList){
			player=(Player) getZone(pair.getFirst().getRelativePosition(getMe().getGlobalPosition()));	
			if (player!=null){
				game.positionUpdate(player,pair.getSecond());
			}
		}
	}

	public void playerLeft() {
		gui.post(
				new Runnable() {					
					@Override
					public void run() {
						//create an alert dialog
						AlertDialog.Builder builder = new AlertDialog.Builder(gui.getContext());			
						builder.setMessage("Player has left the game").setCancelable(false).setTitle("Game Over");
						builder.setNeutralButton("Ok", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								((Activity)(gui.getContext())).finish();
								
							}
						});
						
						builder.create().show();
						
					}
				}
			);
		
	}

	public void invalidMove(int cardId,int fromId){
		gui.invalidMove(cardId,fromId);
	}

	public void restart() {
		// TODO Auto-generated method stub
		gui.clearCards();
//		try {
//			game = game.getClass().newInstance();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public Object getSwappingZone(utils.Position.Player relativePosition) {
		return gui.getDroppableByPosition(relativePosition);
	}
	
	//---------------------------------------------------------------------//	

}
