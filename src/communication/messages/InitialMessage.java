package communication.messages;

import logic.client.Player;
import logic.host.Host;
import communication.server.ConnectionsManager;

import client.controller.ClientController;
import client.controller.actions.ClientAction;

public class InitialMessage extends Message {

	private Player player;
	
	public InitialMessage(ClientAction clientAction, Player player) {
		super(clientAction);
		// TODO Auto-generated constructor stub
		this.player = player; 
	}

	@Override
	public void actionOnServer(String id){
		//serverAction.execute(id);
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);
		Host.players.add(player);
	}
	
	@Override
	public void actionOnClient(){
		ClientController.incomingAPI().incomingCommand(clientAction);
	}
}
