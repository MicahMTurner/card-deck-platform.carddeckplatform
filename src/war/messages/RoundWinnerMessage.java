package war.messages;

import server.controller.ServerController;
import war.actions.RoundWinnerAction;
import client.controller.ClientController;
import communication.messages.Message;

public class RoundWinnerMessage extends Message {

	String winner;
	
	public RoundWinnerMessage(String winner){
		this.winner = winner;
	}
	
	@Override
	public void serverAction(String connectionId) {
		ServerController.getServerController().RoundWinner(connectionId,winner);
		
	}

	@Override
	public void clientAction() {
		ClientController.incomingAPI().incomingCommand(new RoundWinnerAction(winner));		
	}



	

}
