package communication.messages;

import logic.client.Player;
import logic.host.Host;
import communication.server.ConnectionsManager;

import client.controller.ClientController;
import client.controller.actions.AddPlayerAction;
import client.controller.actions.ClientAction;
import client.controller.actions.InitialConnectionAction;

public class InitialMessage extends Message {
	
	private Player newPlayer;
	
	public InitialMessage(Player newPlayer) {
		this.newPlayer=newPlayer;
	}
	
	public InitialMessage(ClientAction clientAction) {
		super.clientAction=clientAction;
	}

	@Override
	public void actionOnServer(Player.Position id){		
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(new Message(new AddPlayerAction(newPlayer)), id);
		Host.players.add(newPlayer);
	}
	
	@Override
	public void actionOnClient(){
		ClientController.incomingAPI().incomingCommand(clientAction);
	}
}
