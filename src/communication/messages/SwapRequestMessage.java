package communication.messages;

import utils.Position;
import client.controller.LivePosition;

import communication.actions.Action;

public class SwapRequestMessage extends Message{
	
	private Position.Player myPosition;
	private Position.Player desiredPosition;	
	
	
	public SwapRequestMessage() {}
	public SwapRequestMessage(Action action){
		this.action = action;
		//this.withWhomId=withWhomId;		
	}
	public SwapRequestMessage(Position.Player myPosition,Position.Player desiredPosition){
		this.myPosition=myPosition;
		this.desiredPosition=desiredPosition;
	}
	@Override
	public void actionOnServer(int id){
		
		LivePosition.get().moveRequested(myPosition,desiredPosition);//ConnectionsManager.getConnectionsManager().sendTo(this, withWhomId);
	}
	@Override
	public void actionOnClient(){
		action.execute();		
	}

}
