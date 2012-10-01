package communication.messages;


import logic.host.Host;

import communication.actions.Action;
import communication.actions.Turn;
import communication.server.ConnectionsManager;

public class EndTurnMessage extends Message{
	
	public EndTurnMessage(Action action) {
		super.action=action;
	}
	public EndTurnMessage() {	
	}
	
	@Override
	public void actionOnServer(int id){			
		Integer nextId=Host.nextInTurn();
		if (nextId!=null){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(nextId)));
		}
	}
	
	@Override
	public void actionOnClient(){
		action.execute();
	}
}
