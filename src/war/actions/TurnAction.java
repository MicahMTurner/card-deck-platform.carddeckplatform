package war.actions;

import war.messages.EndTurnMessage;
import communication.link.ServerConnection;

import client.controller.actions.Action;
import logic.client.GameLogic;
import carddeckplatform.game.TableView;

public class TurnAction extends Action{

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
		ServerConnection.getConnection().getMessageSender().sendMessage(new EndTurnMessage());
	}

}
