package client.controller;

import java.util.concurrent.LinkedBlockingQueue;
import logic.card.Card;
import logic.client.GameLogic;
import logic.client.Player;
import client.controller.actions.GiveCardAction;
import client.controller.actions.HideCardAction;
import client.controller.actions.PutInPublicAction;
import client.controller.actions.RemoveFromPublicAction;
import client.controller.actions.RevealCardAction;
import client.controller.actions.TurnAction;
import client.controller.commands.Command;
import client.controller.commands.IncGiveCardCommand;
import client.controller.commands.IncHideCardCommand;
import client.controller.commands.IncPutInPublicCommand;
import client.controller.commands.IncRemovePublicCommand;
import client.controller.commands.IncRevealCardCommand;
import client.controller.commands.IncTurnCommand;
import client.controller.commands.IncomingCommand;
import client.controller.commands.OutGiveCardCommand;
import client.controller.commands.OutHideCardCommand;
import client.controller.commands.OutPutInPublicCommand;
import client.controller.commands.OutRemovePublicCommand;
import client.controller.commands.OutRevealCardCommand;
import client.controller.commands.OutTurnCommand;
import client.controller.commands.OutgoingCommand;
import carddeckplatform.game.TableView;

//maybe not creating new action and commands all the time?
public class Controller implements Runnable {
	
	
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
			private final static Controller controller=new Controller();
		}
		
				
		/**
		 * get Controller instance
		 */
		public static Controller getController(){
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
	private Controller(){
		
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
		
		public void endTurn(){
			commandsQueue.add(new OutgoingCommand(new TurnAction(gui,logic)));
		}
		
		public void putInPublic(Player player, Card card, boolean isRevealed) {		

			commandsQueue.add(new OutgoingCommand(new PutInPublicAction(gui, logic,player,card,isRevealed)));			
		}
		public void removeFromPublic(Player player, Card card) {
			commandsQueue.add(new OutgoingCommand(new RemoveFromPublicAction(gui, logic,player,card)));			
		}		

		public void revealCard(Player player, Card card){
			commandsQueue.add(new OutgoingCommand(new RevealCardAction(gui, logic,player,card)));
		}
		
		public void hideCard(Player player, Card card) {
			commandsQueue.add(new OutgoingCommand(new HideCardAction(gui, logic,player,card)));
			
		}

		public void giveCard(Player from, Player to, Card card) {
			commandsQueue.add(new OutgoingCommand(new GiveCardAction(gui, logic,from,to,card)));			
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
		
		public void myTurn(){
			commandsQueue.add(new IncomingCommand(new TurnAction(gui,logic)));
		}
		
		public void putInPublic(Player player, Card card, boolean isRevealed) {		

			commandsQueue.add(new IncomingCommand(new PutInPublicAction(gui, logic,player,card,isRevealed)));			
		}
		public void removeFromPublic(Player player, Card card) {
			commandsQueue.add(new IncomingCommand(new RemoveFromPublicAction(gui, logic,player,card)));			
		}		

		public void revealCard(Player player, Card card){
			commandsQueue.add(new IncomingCommand(new RevealCardAction(gui, logic,player,card)));
		}
		
		public void hideCard(Player player, Card card) {
			commandsQueue.add(new IncomingCommand(new HideCardAction(gui, logic,player,card)));
			
		}

		public void giveCard(Player from, Player to, Card card) {
			commandsQueue.add(new IncomingCommand(new GiveCardAction(gui, logic,from,to,card)));			
		}
		
	}
	
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
	  
	 @Override
	public void update(Observable arg0, Object arg1) {
		Message message = (Message) arg1;
		//message.clientAction(this);
	}
	
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
	
	
	
	
	//---------------------------------------------------------------------//
	
	

/*
	public void addAction(Command action) {
		commandsQueue.add(action);
		
	}
	*/
	

}
