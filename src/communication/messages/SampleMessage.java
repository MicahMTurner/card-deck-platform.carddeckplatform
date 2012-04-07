package communication.messages;

import logic.host.Host;
import carddeckplatform.game.TableView;

import client.controller.ClientController;

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
	public void serverAction(ServerMessageHandler serverMessageHandler, Host host, ServerTask serverTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction(ClientController controller) {
		// TODO Auto-generated method stub
		
	}

	
}
