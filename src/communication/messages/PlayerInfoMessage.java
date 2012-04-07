package communication.messages;

//import logic.host.Host;
import com.google.gson.annotations.SerializedName;

import carddeckplatform.game.TableView;

import communication.server.ServerMessageHandler;
import communication.server.ServerTask;

public class PlayerInfoMessage extends Message {
	
	
	
	@SerializedName("username")
	public String username;
	
	public PlayerInfoMessage(){
		messageType = "PlayerInfoMessage";
	}
	
	public PlayerInfoMessage(String username){
		messageType = "PlayerInfoMessage";
		this.username = username;
	}
	
	@Override
	public void serverAction() {
		// TODO Auto-generated method stub
		//host.addPlayer(username, serverTask.getId());
	}

	@Override
	public void clientAction() {
		// TODO Auto-generated method stub
		
	}

}
