package client.controller;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import logic.client.Game;
import utils.Button;
import utils.Card;
import utils.Pair;
import utils.Player;
import utils.Point;
import utils.Position;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import carddeckplatform.game.TableView;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import carddeckplatform.game.gameEnvironment.PlayerInfo;
import client.gui.entities.Droppable;

import communication.actions.CardAdded;
import communication.actions.DraggableMotionAction;
import communication.actions.EndDraggableMotionAction;
import communication.actions.EndRoundAction;
import communication.actions.EndTurnAction;
import communication.actions.SetRulerCardAction;
import communication.link.ServerConnection;
import communication.messages.EndTurnMessage;
import communication.messages.Message;
import communication.messages.RequestCardMessage;
import freeplay.customization.FreePlayProfile;

/**
 * this is a singleton class that provides an API for the card deck platform
 * 
 * @author Yoav
 *
 */
public class ClientController {//implements Observer {
	
	
	private TableView gui;	
	private Game game;	
	//send API class
	private Send send;
	

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
		 * get Send API instance
		 * @return SendAPI instance
		 */
		public static Send sendAPI(){
			return ControllerHolder.controller.send;
		}
		
	
	//constructor
	private ClientController(){				
		this.send=new Send();		
	}
	
	/**
	 * set the game instance
	 * @param game the game instance
	 */
	public void setGame(Game game) {
		//if (this.game==null){
			this.game = game;
		//}
	}
	/**
	 * set the gui instance
	 * @param gui the gui instance
	 */
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
		/**
		 * send End Turn message
		 * @param playerId player's turn that just ended
		 */
		public void endTurn(int playerId){
			gui.setUiEnabled(false);
			ServerConnection.getConnection().send(new EndTurnMessage(new EndTurnAction(playerId)));
		}
		
		/**
		 * send message: card added to droppable zone 
		 * @param card the card that was added
		 * @param from card's originaly place
		 * @param to car'ds destination
		 * @param byWhomId the player that made the action
		 */
		public void cardAdded(Card card,int from,int to,int byWhomId){			
			ServerConnection.getConnection().send(new Message(new CardAdded(card,from,to,byWhomId)));
		}
//		/**
//		 * send message: card got removed from a droppable zone
//		 * @param cards all cards that got removed
//		 * @param from where the cards got removed from
//		 */
//		public void cardRemoved(ArrayList<Card> cards,String from){
//			ServerConnection.getConnection().send(new Message(new CardRemoved(cards, from)));
//		}
		/**
		 * send message: round ended
		 */
		public void endRound(){
			//Integer nextPlayerId=game.endRound();
			ServerConnection.getConnection().send(new Message(new EndRoundAction()));
			//ServerConnection.getConnection().send(new Message(new EndRoundAction()));
		}
		public void setRulerCard(Card card , Integer targetID){
			ServerConnection.getConnection().send(new Message(new SetRulerCardAction(card, targetID)));
		}
		/**
		 * send message: card got revealed
		 * @param card the card that got revealed
		 */
		public void cardRevealed(Card card){
			//ServerConnection.getConnection().getMessageSender().send(new Message(new CardRevealAction()))
		}
		/**
		 * send message: card got hidden
		 * @param card the card that got hidden
		 */
		public void cardHidden(Card card) {
			
			
		}
		/**
		 * send message: drag motion - someone is dragging a card
		 * @param username who's dragging the card
		 * @param id the id of the card that is being dragged
		 * @param coord current coordinations of the card that is being dragged
		 */
		public void dragMotion(String username, int id, Point coord) {
			ServerConnection.getConnection().send(new Message(
					new DraggableMotionAction(GameEnvironment.get().getPlayerInfo().getUsername(),id, coord.getX(), coord.getY())));
			
		}
		/**
		 * send message: drag motion ended - card is no longer being dragged
		 * @param id the id of the card that is no longer being dragged
		 */
		public void endDragMotion(int id){
			ServerConnection.getConnection().send(new Message(new EndDraggableMotionAction(id)));
			
		}
		
		
		
	}
	
	
	//---------Controller functionality-----------//

	/**
	 * get a Droppable zone instance according to its position
	 * @param pos position of the droppable zone
	 * @return instance of the droppable zone in the given position, or NULL if no such droppable in given position
	 */
	public Droppable getZone(Position pos){
		//return gui.getDroppableById(IDMaker.getMaker().getIdByPos(pos.getRelativePosition(getMe().getGlobalPosition())));
		
		Droppable answer=gui.getDroppableByPosition(pos);//(pos.getRelativePosition(getMe().getGlobalPosition())));
		if (answer==null && LivePosition.get().isRunning()){
			//try to see if moved cause of live position
			answer=gui.getDroppableByPosition((pos.getRelativePosition(getMe().getGlobalPosition())));
		}
		return answer;
	}
	/**
	 * get a droppable zone instance according to its id
	 * @param zoneID the zone's id
	 * @return droppable zone instace with the given id, NULL if droppable with given id doesn't exist
	 */
	public Droppable getZone(int zoneID){	
		Droppable answer=gui.getDroppableById(zoneID);
		return answer;
	}
	
	/**
	 * get card instance according to given id (the card's id)
	 * @param cardId the card's id 
	 * @return card instance with given id, NULL if no such card
	 */
	public Card getCard(int cardId){
		return (Card)gui.getDraggableById(cardId);
	}
	/**
	 * get game layouts that were set in Game class or any class that extends it
	 * @return pair of arrays, the first one contains all droppables sets and the secons one
	 * 			contains all buttons set
	 */
	public Pair<ArrayList<Droppable>,ArrayList<Button>>getLayouts() {
		return game.getLayouts();	
	}
	public void setLayouts(FreePlayProfile profile){
		if (profile!=null){
			game.setFreePlayProfile(profile);
		}else{
			game.setLayouts();
		}
	}
	/**
	 * notify to gui that card was moved from droppable A to droppable B(usually called when incoming card move message)
	 * @param card the card that was moved
	 * @param from droppable's id of card's original place
	 * @param to droppable's id of card's new place
	 * @param byWhomId the user-name that did the action
	 */
	public void cardMoved(Card card,int from, int to, int byWhomId){
		gui.moveCard(card, from, to,byWhomId);		
	}
	/**
	 * notify gui that a player started dragging a card (called at incoming start-drag-card message)
	 * @param cardId dragged card'd id
	 * @param fromId id of the droppable the card is dragged from
	 * @param username who made that action
	 */
	public void startDraggableMotion(int cardId,int fromId, String username){
		gui.startDraggableMotion(username, cardId,fromId);
	}
	/**
	 * add player to the game (called at incoming add-player message)
	 * @param newPlayer the new player that joined the game
	 */
	public void addPlayer(Player newPlayer) {
		//set new player's relative position according to my global position at the table
		newPlayer.setRelativePosition(game.getMe().getGlobalPosition());
		game.addPlayer(newPlayer);
		gui.addPlayer(newPlayer);
		
	}
	
	/**
	 * disable UI
	 */
	public void disableUi(){
		gui.setUiEnabled(false);
	}
	/**
	 * enable UI
	 */
	public void enableUi(){
		gui.setUiEnabled(true);
		gui.popToast("Your Move");
	}
	/**
	 * notify GUI who's turn it is now
	 * @param playerId the player's id that starts the turn
	 */
	public void playerTurn(int playerId) {
		Player me=game.getMe();
		if (playerId==me.getId()){
			//my turn
			me.startTurn();
		}else{
			//find who's turn it is
			for (Player player : game.getPlayers()){
				if (player.getId()==playerId){
					player.setMyTurn(true);
					break;
				}
			}
		}
		//glow player icon/name
		gui.setPlayerTurn(gui.getDroppableById(playerId));
	}
	/**
	 * the player that on his device this method is called decalred to be the winner 
	 */
	public void declareWinner() {
		disableUi();
		gui.popToast("WINNER!");
		
	}
	/**
	 * the player that on his device this method is called decalred to be the loser 
	 */
	public void declareLoser() {
		disableUi();
		gui.popToast("LOSSER!");
		
	}
	/**
	 * pop up a message
	 * @param displayMessage the message user want's to pop
	 */
	public void popMessage(String displayMessage) {
		gui.popToast(displayMessage);
		
	}

	/**
	 * remove cards from droppalbe zone (called at incoming message: card-removed)
	 * @param cards all cards that got removed from a droppable zone
	 * @param from 
	 */
//	public void removeCards(ArrayList<Card> cards, String from) {
//		gui.removeCards(cards,from);
//		
//	}
	
	
	
	/**
	 * end current round
	 */
	public void endRound() {		
		disableUi();
		//get the player that starts next round
		Integer nextPlayerId=game.endRound();
		getMe().setMyTurn(false);
		if (nextPlayerId!=null){			
			game.reArrangeQueue(nextPlayerId);
			playerTurn(nextPlayerId);
		}
	}
	/**
	 * request a card from deck, call this method when logic wants to take card from deck/player 
	 * and not action that is done by the user. important for sync the deck's/player's cards by the server
	 * @param fromId id of the droppable you want to take/request a card from
	 * @param amount number of cards you want to take/request from given droppable zone's id
	 */
	public void requestCard(Integer fromId, int amount){
		ServerConnection.getConnection().send(new RequestCardMessage(ClientController.get().getMe(), fromId, amount));
	}
	/**
	 * get my player instance
	 * @return instance of the player that holds the device, NULL if no game(rare)
	 */
	public Player getMe(){
		if (game!=null){
			return game.getMe();
		}
		return null;
	}
	/**
	 * deal given card to given droppable zone id
	 * @param cards the cards to deal 
	 * @param to droppable zone id , where that cards should be delt to
	 */
	public void dealCards(ArrayList<Card> cards, int to) {
		gui.dealCards(cards, to);
	}
	 
//	@Override
//	public void update(Observable arg0, Object arg1) {
//		if (arg1!=null){
//			boolean myTurn=(Boolean)arg1;
//			if (myTurn){
//				gui.setUiEnabled(true);
//				gui.popToast("Your Move");
//			}
//			else{
//				gui.setUiEnabled(false);
//				send.endTurn(getMe().getId());								
//			}
//		}
//		//if(arg0 instanceof Player)
//		
//	}

	

	/**
	 * called when live position is on, swapping given players position
	 * @param movingList array list of pairs that contains the old position and the new position of a player
	 * 		  needed to swap
	 */
	public void positionUpdate2(
			ArrayList<Pair<utils.Position.Player, utils.Position.Player>> movingList) {
		//contains player to move
		Player player;
		for (Pair<Position.Player, Position.Player> pair : movingList){
			//get player instance by his position (the one that we want to move)
			player=(Player) getZone(pair.getFirst().getRelativePosition(getMe().getGlobalPosition()));	
			if (player!=null){
				game.positionUpdate(player,pair.getSecond());
			}
		}
	}
	/**
	 * called when player has left the game,
	 * notify that player has left the game and end it.
	 */
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
	/**
	 * update gui that invalid move happened (called at incoming message: invalid move)
	 * @param cardId
	 * @param fromId
	 */
	public void invalidMove(int cardId,int fromId){
		gui.invalidMove(cardId,fromId);
	}
	/**
	 * restarts the game
	 */
	public void restart() {
		gui.clearCards();

	}
	/**
	 * get player instance zone we want to swap with
	 * @param relativePosition position of player we want to swap with
	 * @return player instance in place we want to swap, NULL if no player in that position
	 */
	public Object getSwappingZone(utils.Position.Player relativePosition) {
		return gui.getDroppableByPosition(relativePosition);
	}

	/**
	 * notify gui about card that is being dragged (called at incoming message: draggable-motion)
	 * @param username player that is dragging the card
	 * @param cardId id of the card that is being dragged
	 * @param x the x-position of the card
	 * @param y the y-position of the card
	 */
	public void draggableMotion(String username, int cardId, float x, float y) {
		gui.draggableMotion(username, cardId, x, y);
		
	}

	/**
	 * notify GUI that card is no longer being dragged (called at incoming message: end-draggable-motion)
	 * @param cardId id of the card that is no longer being dragged
	 */
	public void endDraggableMotion(int cardId) {
		gui.endDraggableMotion(cardId);
		
	}

	/**
	 * add instance of player that holds the device (used when game starts)
	 * @param playerInfo player's info
	 * @param position player's position
	 * @param id player's unique id
	 */
	public void addMe(PlayerInfo playerInfo,
			utils.Position.Player position, int id) {
		game.addMe(playerInfo, position, id);
		gui.addPlayer(game.getMe());
		
	}
	
	//---------------------------------------------------------------------//	

}
