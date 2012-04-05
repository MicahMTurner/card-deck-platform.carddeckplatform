package client.controller;

import java.util.Observable;
import java.util.Observer;

import communication.entities.TcpClient;
import communication.link.ServerConnection;
import communication.link.TcpSender;
import communication.messages.CardMotionMessage;
import communication.messages.EndCardMotionMessage;
import communication.messages.Message;
import communication.messages.PlayerInfoMessage;

import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;

public class ClientController implements Observer {
	private TableView tv=null;
	
	public void setTv(TableView tv) {
		this.tv = tv;
	}

	//--------------------- Singletone--------------------------
	static private ClientController clientController=null;
	static public ClientController get(){
		if(clientController==null){
			clientController = new ClientController();
		}
		return clientController;
	}
	//----------------------------------------------------------
	
	//private Logic logic;
	
	private ClientController(){
		ServerConnection.getConnection().openConnection(this);
	}
	
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
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Message message = (Message) arg1;
		//message.clientAction(this);
	}

}
