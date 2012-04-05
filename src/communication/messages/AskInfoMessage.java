package communication.messages;

import logic.host.Host;

import client.controller.ClientController;

import com.google.gson.annotations.SerializedName;

import carddeckplatform.game.TableView;

import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public class AskInfoMessage extends Message  {
	
	
	
	public AskInfoMessage(){
		messageType = "PlayerInfoMessage";
	}
	
	@Override
	public void serverAction(ServerMessageHandler serverMessageHandler, Host host,  ServerTask serverTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction(ClientController controller) {
		// TODO Auto-generated method stub
		controller.sendInfo();
	}

}
