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
		Position.Player next=Host.nextInTurn();
		if (next!=null){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(next)));
		}
	}
	
	@Override
	public void actionOnClient(){
		action.execute();
	}
}
