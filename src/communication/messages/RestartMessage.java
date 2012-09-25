package communication.messages;

import logic.host.Host;
import communication.actions.RestartAction;
import communication.server.ConnectionsManager;

public class RestartMessage extends Message {
	
	@Override
	public void actionOnServer(int id){
		ConnectionsManager.getConnectionsManager().sendToAll(this);
		Host.startGame();
		
	}
	
	@Override
	public void actionOnClient(){
		new RestartAction().execute();		
	}
}
