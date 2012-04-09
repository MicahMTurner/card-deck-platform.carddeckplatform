package war.messages;

import war.actions.GameWinnerAction;
import client.controller.ClientController;
import communication.messages.Message;
import communication.server.ConnectionsManager;

public class GameWinnerMessage extends Message {


	@Override
	public void serverAction(String connectionId) {
		ConnectionsManager.getConnectionsManager().sendToAllExcptMe(this, connectionId);
		
	}

	@Override
	public void clientAction() {
		ClientController.incomingAPI().incomingCommand(new GameWinnerAction());
		
	}


}
