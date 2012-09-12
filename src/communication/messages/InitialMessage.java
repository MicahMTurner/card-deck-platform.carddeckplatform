package communication.messages;


import utils.Player;
import utils.Position;
import logic.host.Host;
import communication.actions.Action;
import communication.actions.AddPlayerAction;
import communication.server.ConnectionsManager;




public class InitialMessage extends Message {
	
	private Player newPlayer;
	public InitialMessage() {}
	
	public InitialMessage(Player newPlayer) {
		this.newPlayer=newPlayer;
	}
	
	public InitialMessage(Action action) {
		super.action=action;
	}

	@Override
	public void actionOnServer(int id){			
		//Host.addPlayer(newPlayer);
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(new Message(new AddPlayerAction(newPlayer)), id);
		
	}
	
	@Override
	public void actionOnClient(){
		action.execute();
	}
}
