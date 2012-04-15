package client.controller;


import android.content.Context;
import logic.card.CardLogic;
import logic.client.Game;
import logic.client.LogicDroppable;
import logic.client.Player;
import logic.client.Player.Position;
import client.controller.actions.ClientAction;
import client.gui.entities.Table;
import carddeckplatform.game.TableView;

//maybe not creating new action and commands all the time?
public class ClientController {
	
	
	private TableView gui=null;
	private Game logic;
	private IncomingAPI incomingAPI;
	private OutgoingAPI outgoingAPI;
	
	private LogicDroppable myArea;
	

	public Game getLogic() {
		return logic;
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
		
		this.outgoingAPI=new OutgoingAPI();
		this.incomingAPI=new IncomingAPI();		

	}
	
	public void setLogic(Game game) {
		this.logic = game;
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
			action.outgoing();
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
			action.incoming();
		}

		
	}

	public void buildGameLayout(Context applicationContext, TableView tableview, Position pos) {
		logic.buildLayout(applicationContext, tableview, pos);	
	}
	
	public void addPlayer(Player newPlayer) {
		logic.addPlayer(newPlayer);
		
	}
	
	public void setPosition(Position position) {
		logic.getMe().setPosition(position);
		
	}
	
	public Position getPosition() {
		return logic.getMe().getPosition();
	}
	
	public void addCard(CardLogic cardLogic) {
		logic.getMe().addCard(cardLogic);
	}
	
		
	public void disableUi(){
		gui.setUiEnabled(false);
	}

	public void playerTurn(Player.Position position) {
		if (position.equals(logic.getMe().getPosition())){
			gui.setUiEnabled(true);
		}
		//glow player icon/name		
	}

	public void endTurn() {
		// disable glow player icon/name	
		
	}
	
	
	public void setMyArea(LogicDroppable myArea) {
		this.myArea = myArea;
	}
	
	public LogicDroppable getMyArea() {
		return myArea;
	}
	
	public void runCardAnimation(CardLogic cardLogic, LogicDroppable logicDroppable, final long initialDelay, final long delay, final boolean revealedWhileMoving, final boolean revealedAtEnd, Table.GetMethod g){
		gui.moveDraggable(gui.getDraggableById(cardLogic.getId(), g), gui.getDroppableById(logicDroppable.getId()), initialDelay, delay, revealedWhileMoving, revealedAtEnd);
	}

	public boolean isMyTurn() {
		return gui.isUiEnabled();
	}

	public void declareWinner() {
		gui.popToast("WINNER!");
		
	}
	
	public void declareLoser() {
		gui.popToast("LOSSER!");
		
	}

	public void popMessage(String displayMessage) {
		gui.popToast(displayMessage);
		
	}
	public void runCardAnimation(CardLogic cardLogic, int x,int y, final long initialDelay, final long delay, final boolean revealedWhileMoving, final boolean revealedAtEnd, Table.GetMethod g){
		gui.moveDraggable(gui.getDraggableById(cardLogic.getId(), g), x,y, initialDelay, delay, revealedWhileMoving, revealedAtEnd);
	}
	public void removeCard(CardLogic card) {
		// TODO Auto-generated method stub
		
	}
	
	
	//---------------------------------------------------------------------//	

}
