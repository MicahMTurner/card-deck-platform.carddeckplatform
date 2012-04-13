package client.controller.actions;

import logic.client.Player;
import communication.link.ServerConnection;
import communication.messages.EndTurnMessage;
import communication.messages.Message;

import client.controller.ClientController;

public class EndTurnAction extends ClientAction{
	Player.Position position;
	public EndTurnAction(Player.Position position) {
		this.position=position;
	}

	@Override
	public void incoming() {
		ClientController.getController().endTurn();
		
	}

	@Override
	public void outgoing() {
		ClientController.getController().disableUi();
		ServerConnection.getConnection().getMessageSender().send(new EndTurnMessage(new EndTurnAction(position)));
		
	}

}
