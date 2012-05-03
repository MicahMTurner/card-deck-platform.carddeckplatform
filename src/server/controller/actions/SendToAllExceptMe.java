package server.controller.actions;



import utils.Position;
import communication.messages.Message;
import communication.server.ConnectionsManager;

public class SendToAllExceptMe extends ServerAction {
	private Message msg;
	
	public SendToAllExceptMe(Message msg){
		this.msg = msg;
	}
	
	@Override
	public void execute(Position.Player id) {
		// TODO Auto-generated method stub
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(msg, id);
	}

}
