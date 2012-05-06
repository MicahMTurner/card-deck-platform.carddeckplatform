package communication.messages;

import java.io.Serializable;

import utils.Position;

import communication.actions.Action;
import communication.server.ConnectionsManager;


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
