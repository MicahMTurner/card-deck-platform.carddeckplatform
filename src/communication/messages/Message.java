package communication.messages;

import java.io.Serializable;

import communication.server.ConnectionsManager;

import server.controller.actions.SendToAllExceptMe;
import server.controller.actions.ServerAction;

import client.controller.ClientController;
import client.controller.actions.ClientAction;

//import logic.host.Host;
import carddeckplatform.game.GameStatus;

import carddeckplatform.game.TableView;


public class Message implements Serializable {
	protected ClientAction clientAction;
	//protected ServerAction serverAction;
	
	public Message(ClientAction clientAction){
		this.clientAction = clientAction;
		//this.serverAction = serverAction;
	}
	
	public void actionOnServer(String id){
		//serverAction.execute(id);
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);
	}
	
	public void actionOnClient(){
		//ClientController.incomingAPI().incomingCommand(clientAction);
		clientAction.incoming();
	}

}
