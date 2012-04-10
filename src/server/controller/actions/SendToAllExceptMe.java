package server.controller.actions;

import communication.messages.Message;
import communication.server.ConnectionsManager;

public class SendToAllExceptMe extends ServerAction {
	private Message msg;
	
	public SendToAllExceptMe(Message msg){
		this.msg = msg;
	}
	
	@Override
	public void execute(String id) {
		// TODO Auto-generated method stub
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(msg, id);
	}

}
