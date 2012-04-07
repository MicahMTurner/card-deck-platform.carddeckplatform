package communication.messages;

//import logic.host.Host;
import client.controller.Controller;

import com.google.gson.annotations.SerializedName;

import carddeckplatform.game.TableView;

import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public class AskInfoMessage extends Message  {
	
	
	
	public AskInfoMessage(){
		messageType = "PlayerInfoMessage";
	}
	
	@Override
	public void serverAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		//Controller.getController().incomingAPI().sendInfo();
	}

}
