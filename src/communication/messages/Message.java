package communication.messages;

import java.io.Serializable;


import communication.actions.Action;
import communication.server.ConnectionsManager;
import server.controller.actions.SendToAllExceptMe;
import server.controller.actions.ServerAction;
import utils.Player;
import utils.Position;
import client.controller.ClientController;
import client.controller.actions.ClientAction;
import carddeckplatform.game.GameStatus;
import carddeckplatform.game.TableView;


public class Message implements Serializable {
	protected Action action;
	
	
	public Message() {}
	public Message(Action action){
		this.action = action;
		
	}
	
	public void actionOnServer(Position.Player id){
		
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);
	}
	
	public void actionOnClient(){
		action.execute();		
	}

}
