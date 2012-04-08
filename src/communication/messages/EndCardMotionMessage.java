package communication.messages;

//import logic.host.Host;
import carddeckplatform.game.TableView;
import client.controller.Controller;

//import com.google.gson.annotations.SerializedName;
import communication.client.ClientMessageHandler;
import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public class EndCardMotionMessage extends Message {
	
	public EndCardMotionMessage(){
		messageType = "EndCardMotionMessage";
	}
	
	public EndCardMotionMessage(int cardId){
		messageType = "EndCardMotionMessage";
		
		
		this.cardId = cardId;
	
	}
	
	public int cardId;

	@Override
	public void serverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		//controller.endDraggableMotion(cardId);
		Controller.getController().incomingAPI().endCardMotion(cardId);
	}


}
