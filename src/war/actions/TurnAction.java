package war.actions;

import communication.link.ServerConnection;
import communication.messages.Message;

import client.controller.actions.ClientAction;
import logic.client.GameLogic;
import carddeckplatform.game.TableView;

public class TurnAction extends ClientAction{

	public TurnAction() {
		
		
	}

	@Override
	/**
	 * start turn
	 */
	public void incoming() {
		// TODO Auto-generated method stub
		// unlock gui.
		
	}

	@Override
	/**
	 * end turn
	 */
	public void outgoing() {
		// TODO Auto-generated method stub
		// lock gui.
		ServerConnection.getConnection().getMessageSender().send(new Message(this));
	}

}
