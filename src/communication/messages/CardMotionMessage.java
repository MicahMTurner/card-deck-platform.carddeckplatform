package communication.messages;

//import logic.host.Host;
import carddeckplatform.game.TableView;
import client.controller.Controller;

//import com.google.gson.annotations.SerializedName;
import communication.client.ClientMessageHandler;
import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public class CardMotionMessage extends Message {
	public CardMotionMessage(){
		messageType = "CardMotionMessage";
	}
	
	public CardMotionMessage(String username , int cardId , int x , int y){
		messageType = "CardMotionMessage";
		
		this.username = username;
		this.cardId = cardId;
		this.x = x;
		this.y = y;
		
	}
	

	public String username;
	
	public int cardId;
	
	public int x;
	
	public int y;

	@Override
	public void serverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		//controller.draggableMotion(username, cardId, X, Y);
		Controller.getController().incomingAPI().cardMotion(username, cardId, x, y);
	}
	
}
