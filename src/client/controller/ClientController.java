package client.controller;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import communication.actions.CardAdded;
import communication.actions.CardRemoved;
import communication.actions.DraggableMotionAction;
import communication.actions.EndDraggableMotionAction;
import communication.actions.EndRoundAction;
import communication.actions.EndTurnAction;
import communication.link.ServerConnection;
import communication.messages.EndTurnMessage;
import communication.messages.Message;

import utils.Card;
import utils.Player;
import utils.Point;
import utils.Position;
import utils.Public;

import IDmaker.IDMaker;
import logic.client.Game;
import client.gui.entities.Droppable;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;


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
		
		public void endTurn(Position.Player position){
			gui.setUiEnabled(false);
			ServerConnection.getConnection().send(new EndTurnMessage(new EndTurnAction(position)));
		}
		public void cardAdded(Card card,int from,int to,Player byWhom){			
			ServerConnection.getConnection().send(new Message(new CardAdded(card,from,to,byWhom)));
		}
		public void cardRemoved(ArrayList<Card> cards,String from){
			ServerConnection.getConnection().send(new Message(new CardRemoved(cards, from)));
		}
		public void endRound(){
			ServerConnection.getConnection().send(new Message(new EndRoundAction()));
		}
		public void cardRevealed(Card card){
			//ServerConnection.getConnection().getMessageSender().send(new Message(new CardRevealAction()))
		}

		public void cardHidden(Card card) {
			
			
		}

		public void dragMotion(String username, int id, Point coord) {
			ServerConnection.getConnection().send(new Message(
					new DraggableMotionAction(GameStatus.username,id, coord.getX(), coord.getY())));
			
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
		return gui.getDroppableById(IDMaker.getMaker().getIdByPos(pos.getRelativePosition(getMe().getGlobalPosition())));
	}
	public void setLayouts(ArrayList<Droppable> publics) {
		game.getLayouts(publics);	
	}
	public void cardMoved(Card card,int from, int to, Player byWhom){
		gui.moveCard(card, from, to,byWhom);		
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
		}
		//glow player icon/name		
	}

	public void endTurn() {
		// disable glow player icon/name	
		
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
		gui.popToast("WINNER!");
		
	}
	
	public void declareLoser() {
		gui.popToast("LOSSER!");
		
	}

	public void popMessage(String displayMessage) {
		gui.popToast(displayMessage);
		
	}


	public void removeCards(ArrayList<Card> cards, String from) {
		gui.removeCards(cards,from);
		
	}

	public void endRound() {
		//game.getMe().roundEnded();		
	}
	public Player getMe(){
		return game.getMe();
	}

	public void dealCards(ArrayList<Card> cards, int from, int to,boolean revealWhileMoving,boolean revealAtEnd) {
		gui.dealCards(cards, to);
		//ClientController.guiAPI().moveCards(cards, to, revealWhileMoving, revealAtEnd);
		
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
				send.endTurn(getMe().getGlobalPosition());								
			}
		}
		//if(arg0 instanceof Player)
		
	}

	public void positionUpdate(int playerId, utils.Position.Player newPosition) {
		Player player=(Player) gui.getDroppableById(playerId);
		Player swappedWith=(Player) getZone(newPosition);
		game.positionUpdate(player,swappedWith);
		gui.swapPositions(player,swappedWith);
		
	}

	
	
	//---------------------------------------------------------------------//	

}
