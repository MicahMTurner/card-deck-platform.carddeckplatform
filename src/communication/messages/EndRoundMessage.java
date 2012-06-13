package communication.messages;

import logic.host.Host;
import communication.actions.Action;
import communication.actions.Turn;
import communication.server.ConnectionsManager;


public class EndRoundMessage extends Message{
	private Integer nextPlayerId;
	
	public EndRoundMessage(Integer nextPlayerId, Action action) {
		this.nextPlayerId=nextPlayerId;
		super.action=action;
	}	
	
	@Override
	public void actionOnServer(int id){		
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);
		if (nextPlayerId!=null){
			Host.reArrangeQueue(nextPlayerId);
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(nextPlayerId)));
		}
		
	}
	
	@Override
	public void actionOnClient(){
		action.execute();
	}
	

}
