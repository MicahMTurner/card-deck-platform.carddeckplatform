package communication.messages;

import logic.host.Host;
import communication.actions.Action;
import communication.actions.Turn;
import communication.server.ConnectionsManager;


public class EndRoundMessage extends Message{
	int nextPlayerId;
	
	public EndRoundMessage(int nextPlayerId, Action action) {
		this.nextPlayerId=nextPlayerId;
		super.action=action;
	}	
	
	@Override
	public void actionOnServer(int id){		
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);
		Host.reArrangeQueue(nextPlayerId);
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(nextPlayerId)));
		
	}
	
	@Override
	public void actionOnClient(){
		action.execute();
	}
	

}
