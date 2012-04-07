package communication.messages;

//import logic.host.Host;
import carddeckplatform.game.TableView;

import com.google.gson.annotations.SerializedName;
import communication.client.ClientMessageHandler;
import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public class SampleMessage extends Message {
	
	public SampleMessage(){
		messageType = "SampleMessage";
	}
	
	@SerializedName("name")
	public String name;

	@Override
	public void serverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		
	}

	
}
