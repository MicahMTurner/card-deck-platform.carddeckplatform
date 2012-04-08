package communication.messages;

import carddeckplatform.game.TableView;
import com.google.gson.annotations.SerializedName;
import communication.client.ClientMessageHandler;
import communication.server.ServerMessageHandler;

public class EndCardMotionMessage extends Message {
	
	public EndCardMotionMessage(){
		messageType = "EndCardMotionMessage";
	}
	
	public EndCardMotionMessage(int cardId){
		messageType = "EndCardMotionMessage";
		
		
		this.cardId = cardId;
	
	}
	
	@SerializedName("cardId")
	public int cardId;

	@Override
	public void serverAction(ServerMessageHandler serverMessageHandler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction(TableView tableView) {
		// TODO Auto-generated method stub
		tableView.endDraggableMotion(cardId);
	}


}
