package communication.messages;


import utils.Position;
import communication.actions.Action;
import communication.actions.Turn;
import communication.server.ConnectionsManager;
import logic.host.Host;

public class EndTurnMessage extends Message{
	
	public EndTurnMessage(Action action) {
		super.action=action;
	}
	public EndTurnMessage() {	
	}
	
	@Override
	public void actionOnServer(int id){		
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);		
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
