package server.controller.actions;

import logic.client.Player;
import communication.messages.Message;
import communication.server.ConnectionsManager;

public class SendToAll extends ServerAction {
	private Message msg;
	
	public SendToAll(Message msg){
		this.msg = msg;
	}
	
	@Override
	public void execute(Player.Position id) {
		// TODO Auto-generated method stub
		ConnectionsManager.getConnectionsManager().sendToAll(msg);
	}

}
