package war.messages;

import server.controller.ServerController;
import war.actions.TurnAction;
import client.controller.ClientController;
import communication.messages.Message;

public class TurnMessage extends Message {

	@Override
	/**
	 * end turn
	 */
	public void serverAction(String connectionId) {
		ServerController.getServerController().endTurnCommand();
		
	}

	@Override
	/**
	 * start turn
	 */
	public void clientAction() {
		ClientController.incomingAPI().incomingCommand(new TurnAction());
		
	}



}
