package communication.messages;

import war.actions.TurnAction;
import communication.server.ConnectionsManager;

import logic.client.Game;
import logic.client.Player;
import client.controller.ClientController;
import client.controller.actions.ClientAction;

public class EndTurnMessage extends Message{
	
	public EndTurnMessage(ClientAction clientAction) {
		super.clientAction=clientAction;
	}
	
	
	@Override
	public void actionOnServer(Player.Position id){		
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, id);
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new TurnAction(Game.nextInTurn())));
	}
	
	@Override
	public void actionOnClient(){
		ClientController.incomingAPI().incomingCommand(clientAction);
	}
}
