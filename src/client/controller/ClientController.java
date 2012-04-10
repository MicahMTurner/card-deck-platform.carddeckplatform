package client.controller;

import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

import communication.messages.Message;
import controller.commands.Command;
import controller.commands.IncomingCommand;
import controller.commands.OutgoingCommand;

import android.database.Observable;
import logic.card.CardLogic;
import logic.client.GameLogic;
import logic.client.Player;
import client.controller.actions.ClientAction;
import client.controller.actions.DraggableMotionAction;
import client.controller.actions.EndDraggableMotionAction;
//import client.controller.actions.GiveCardAction;
//import client.controller.actions.HideCardAction;
//import client.controller.actions.PutInPublicAction;
//import client.controller.actions.RemoveFromPublicAction;
//import client.controller.actions.RevealCardAction;
//import client.controller.actions.TurnAction;

import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;

//maybe not creating new action and commands all the time?
public class ClientController implements Runnable, Observer {
	
	
	private TableView gui=null;
	private GameLogic logic;
	private IncomingAPI incomingAPI;
	private OutgoingAPI outgoingAPI;
	
	//holds commands in queue
	private  LinkedBlockingQueue<Command> commandsQueue;
	//tells the controller to stop
	private volatile boolean stopped;

	//-------Singleton implementation--------//
		private static class ControllerHolder
		{
			private final static ClientController controller=new ClientController();
		}
		
				
		/**
		 * get Controller instance
		 */
		public static ClientController getController(){
			return ControllerHolder.controller;
		}

	
	//---------STATIC GETters for incoming and outgoing APIs-------//
		/**
		 * get Incoming API
		 * @return IncomingAPI instance
		 */
		public static IncomingAPI incomingAPI(){
			return ControllerHolder.controller.incomingAPI;
		}
		/**
		 * get Outgoing API
		 * @return OutgoingAPI instance
		 */
		public static OutgoingAPI outgoingAPI(){
			return ControllerHolder.controller.outgoingAPI;
		}
	
	//constructor
	private ClientController(){
		
		commandsQueue=new LinkedBlockingQueue<Command>();
		this.outgoingAPI=new OutgoingAPI();
		this.incomingAPI=new IncomingAPI();		
		this.stopped=false;
		
		
		//--starting controller when first referred--//
		new Thread(this).start();
		//ServerConnection.getConnection().openConnection(this);
	}
	
	public void setLogic(GameLogic logic) {
		this.logic = logic;
	}
	
	public void setTv(TableView tv) {
		this.gui = tv;
	}
	
	
				
	
	
	
	//---------API for outgoing-----------//
	/**
	 * API used by the class that creating events (for now GUI)
	 * @author Yoav
	 *
	 */
	public class OutgoingAPI{
		
		private OutgoingAPI(){}
		
		public void outgoingCommand(ClientAction action){
			action.setGui(gui);
			action.setLogic(logic);
			//commandsQueue.add(new OutgoingCommand(action));
			new OutgoingCommand(action).execute();
		}
		
//		public void endTurn(){
//			commandsQueue.add(new OutgoingCommand(new TurnAction()));
//		}
//		
//		public void putInPublic(Player player, CardLogic card, boolean isRevealed) {		
//
//			commandsQueue.add(new OutgoingCommand(new PutInPublicAction(player,card,isRevealed)));			
//		}
//		public void removeFromPublic(Player player, CardLogic card) {
//			commandsQueue.add(new OutgoingCommand(new RemoveFromPublicAction(player,card)));			
//		}		
//
//		public void revealCard(Player player, CardLogic card){
//			commandsQueue.add(new OutgoingCommand(new RevealCardAction(player,card)));
//		}
//		
//		public void hideCard(Player player, CardLogic card) {
//			commandsQueue.add(new OutgoingCommand(new HideCardAction(player,card)));
//			
//		}
//
//		public void giveCard(Player from, Player to, CardLogic card) {
//			commandsQueue.add(new OutgoingCommand(new GiveCardAction(from,to,card)));			
//		}
//		
		public void cardMotion(String username, int cardId, int x, int y){
			//commandsQueue.add(new OutgoingCommand(new DraggableMotionAction(gui, logic, username, cardId, x, y)));
			new OutgoingCommand(new DraggableMotionAction(username, cardId, x, y)).execute();
		}
		
		public void endCardMotion(int cardId){
			//commandsQueue.add(new OutgoingCommand(new EndDraggableMotionAction(gui, logic, cardId)));
			new OutgoingCommand(new EndDraggableMotionAction( cardId)).execute();
		}
		
	}

	
	//---------API for incoming-----------//
	/**
	 * API used by the class that receive messages (for now TCP RECIEVER)
	 * @author Yoav
	 *
	 */
	public class IncomingAPI {	
		
		private IncomingAPI(){}
		
		public void incomingCommand(ClientAction action){
			action.setGui(gui);
			action.setLogic(logic);
			//commandsQueue.add(new OutgoingCommand(action));
			new IncomingCommand(action).execute();
		}
		
//		public void myTurn(){
//			commandsQueue.add(new IncomingCommand(new TurnAction()));
//		}
//		
//		public void putInPublic(Player player, CardLogic card, boolean isRevealed) {		
//
//			commandsQueue.add(new IncomingCommand(new PutInPublicAction(player,card,isRevealed)));			
//		}
//		public void removeFromPublic(Player player, CardLogic card) {
//			commandsQueue.add(new IncomingCommand(new RemoveFromPublicAction(player,card)));			
//		}		
//
//		public void revealCard(Player player, CardLogic card){
//			commandsQueue.add(new IncomingCommand(new RevealCardAction(player,card)));
//		}
//		
//		public void hideCard(Player player, CardLogic card) {
//			commandsQueue.add(new IncomingCommand(new HideCardAction(player,card)));
//			
//		}
//
//		public void giveCard(Player from, Player to, CardLogic card) {
//			commandsQueue.add(new IncomingCommand(new GiveCardAction(from,to,card)));			
//		}
		
		public void cardMotion(String username, int cardId, int x, int y){
			//commandsQueue.add(new IncomingCommand(new DraggableMotionAction(gui, logic, username, cardId, x, y)));
			new IncomingCommand(new DraggableMotionAction(username, cardId, x, y)).execute();
		}
		
		public void endCardMotion(int cardId){
			//commandsQueue.add(new IncomingCommand(new EndDraggableMotionAction(gui, logic, cardId)));
			new IncomingCommand(new EndDraggableMotionAction(cardId)).execute();
		}
		
	}
	//--communication with GUI--//
 
	
	//---Actions on Controller---//
	
	/**
	 * ends controller's thread
	 */
	public void shutDown(){
		this.stopped=true;
		//add a new blank command to wake controller and stop it
		commandsQueue.add(new Command(null) {
			@Override
			public void execute() {
			}
		});
	}
	
	

	@Override
	public void run() {
		while (!stopped){
			try {
				commandsQueue.take().execute();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		
	}
	
	
	  

	/*
	public void draggableMotion(String username, int id , int x , int y){
		gui.draggableMotion(username, id, x, y);
	}
	
	
	
	public void endDraggableMotion(int id){
		gui.endDraggableMotion(id);
	}
	
	public void sendInfo(){
		ServerConnection.getConnection().getMessageSender().sendMessage(new PlayerInfoMessage(GameStatus.username));
	}
	
	
	
	
	public void sendDraggableMotion(int cardId, int x, int y){
		ServerConnection.getConnection().getMessageSender().sendMessage(new CardMotionMessage(GameStatus.username,cardId, x, y));
	}
	
	public void sendEndDraggableMotion(int cardId){
		ServerConnection.getConnection().getMessageSender().sendMessage(new EndCardMotionMessage(cardId));
	}
	*/


	@Override
	public void update(java.util.Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Message message = (Message) arg1;
		message.actionOnClient();
	}
	
	
	
	
	//---------------------------------------------------------------------//
	
	

/*
	public void addAction(Command action) {
		commandsQueue.add(action);
		
	}
	*/
	

}
