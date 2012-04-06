package client.controller;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

import client.controller.commands.Command;

import communication.entities.TcpClient;
import communication.link.ServerConnection;
import communication.link.TcpSender;
import communication.messages.CardMotionMessage;
import communication.messages.EndCardMotionMessage;
import communication.messages.Message;
import communication.messages.PlayerInfoMessage;

import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;

public class Controller implements Observer, Runnable {
	
	private TableView tv=null;
	
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
		public static Controller getCommands(){
			return ControllerHolder.controller;
		}
	}
	//----------------------------------------------------------
	
	
	//constructor
	private Controller(){
		commandsQueue=new LinkedBlockingQueue<Command>();
		this.stopped=false;

		//--starting controller when first reffered--//
		new Thread(this).start();
		//ServerConnection.getConnection().openConnection(this);
	}
	
	public void setTv(TableView tv) {
		this.tv = tv;
	}
	
	//---------add here all PUBLIC actions that controller should do-----------//
	public void draggableMotion(String username, int id , int x , int y){
		tv.draggableMotion(username, id, x, y);
	}
	
	
	public void endDraggableMotion(int id){
		tv.endDraggableMotion(id);
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
	
	/**
	 * ends controller's thread
	 */
	public void shutDown(){
		this.stopped=true;
		//add a new blank command to wake controller and stop it
		commandsQueue.add(new Command() {
			@Override
			public void execute() {
			}
		});
	}
	
	//---------------------------------------------------------------------//
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Message message = (Message) arg1;
		//message.clientAction(this);
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
	
	

}
