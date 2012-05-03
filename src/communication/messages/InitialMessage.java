package communication.messages;


import utils.Player;
import utils.Position;
import logic.host.Host;
import communication.actions.Action;
import communication.server.ConnectionsManager;
import client.controller.actions.AddPlayerAction;


public class InitialMessage extends Message {
	
	private Player newPlayer;
	
	public InitialMessage(Player newPlayer) {
		this.newPlayer=newPlayer;
	}
	
	public InitialMessage(Action action) {
		super.action=action;
	}

	@Override
	public void actionOnServer(Position.Player id){		
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(new Message(new AddPlayerAction(newPlayer)), id);
		Host.players.add(newPlayer);
	}
	
	@Override
	public void actionOnClient(){
		action.execute();
	}
}
