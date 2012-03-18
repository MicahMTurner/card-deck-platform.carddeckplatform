package communication.messages;

import carddeckplatform.game.TableView;

import com.google.gson.annotations.SerializedName;
import communication.client.ClientMessageHandler;
import communication.server.ServerMessageHandler;

public class SampleMessage extends Message {
	
	public SampleMessage(){
		messageType = "SampleMessage";
	}
	
	@SerializedName("name")
	public String name;

	@Override
	public void serverAction(ServerMessageHandler serverMessageHandler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction(TableView tableView) {
		// TODO Auto-generated method stub
		
	}

	
}
