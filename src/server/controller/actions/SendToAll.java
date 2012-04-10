package server.controller.actions;

import communication.messages.Message;
import communication.server.ConnectionsManager;

public class SendToAll extends ServerAction {
	private Message msg;
	
	public SendToAll(Message msg){
		this.msg = msg;
	}
	
	@Override
	public void execute(String id) {
		// TODO Auto-generated method stub
		ConnectionsManager.getConnectionsManager().sendToAll(msg);
	}

}
