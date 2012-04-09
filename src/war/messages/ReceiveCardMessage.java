package war.messages;

import server.controller.ServerController;
import war.actions.RecieveCardAction;
import client.controller.ClientController;
import communication.messages.Message;
import communication.server.ConnectionsManager;

public class ReceiveCardMessage extends Message {

	@Override
	public void serverAction(String connectionId) {
		
		ServerController.getServerController().recieveCardCommand(connectionId);
		
	}

	@Override
	public void clientAction() {
		ClientController.incomingAPI().incomingCommand(new RecieveCardAction());
		
	}

}
