package communication.messages;

import logic.host.Host;

import communication.actions.Action;


public class EndRoundMessage extends Message{
	private Integer nextPlayerId;
	
	public EndRoundMessage(Integer nextPlayerId, Action action) {
		this.nextPlayerId=nextPlayerId;
		super.action=action;
	}	
	public EndRoundMessage(Integer nextPlayerId) {
		this.nextPlayerId=nextPlayerId;
	}
	
	@Override
	public void actionOnServer(int id){
		if (nextPlayerId!=null){
			Host.reArrangeQueue(nextPlayerId);
		}
		//ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);
	}
	
	@Override
	public void actionOnClient(){
		action.execute();
	}
	

}
