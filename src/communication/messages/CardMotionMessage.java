package communication.messages;

import carddeckplatform.game.TableView;

import com.google.gson.annotations.SerializedName;
import communication.client.ClientMessageHandler;
import communication.server.ServerMessageHandler;

public class CardMotionMessage extends Message {
	public CardMotionMessage(){
		messageType = "CardMotionMessage";
	}
	
	public CardMotionMessage(String username , int cardId , int X , int Y){
		messageType = "CardMotionMessage";
		
		this.username = username;
		this.cardId = cardId;
		this.X = X;
		this.Y = Y;
		
	}
	
	@SerializedName("username")
	public String username;
	
	@SerializedName("cardId")
	public int cardId;
	
	@SerializedName("X")
	public int X;
	
	@SerializedName("Y")
	public int Y;

	@Override
	public void serverAction(ServerMessageHandler serverMessageHandler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction(TableView tableView) {
		// TODO Auto-generated method stub
		tableView.draggableMotion(username, cardId, X, Y);
	}
	
}
