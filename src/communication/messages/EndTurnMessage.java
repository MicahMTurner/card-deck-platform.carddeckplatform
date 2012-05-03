package communication.messages;


import utils.Player;
import utils.Position;
import communication.actions.Action;
import communication.actions.Turn;
import communication.server.ConnectionsManager;
import logic.host.Host;

public class EndTurnMessage extends Message{
	
	public EndTurnMessage(Action action) {
		super.action=action;
	}
	
	
	@Override
	public void actionOnServer(Position.Player id){		
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new Turn(Host.nextInTurn())));
	}
	
	@Override
	public void actionOnClient(){
		action.execute();
	}
}
