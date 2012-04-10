package client.controller.actions;

import communication.link.ServerConnection;
import communication.messages.Message;

import logic.client.GameLogic;
import carddeckplatform.game.TableView;

public class EndDraggableMotionAction extends ClientAction  {

	private int cardId;
	public EndDraggableMotionAction( int cardId) {
		this.cardId = cardId;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void incoming() {
		// TODO Auto-generated method stub
		gui.endDraggableMotion(cardId);
	}

	@Override
	public void outgoing() {
		// TODO Auto-generated method stub
		ServerConnection.getConnection().getMessageSender().sendMessage(new Message(this));
	}

}
