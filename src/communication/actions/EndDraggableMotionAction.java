package communication.actions;

import client.controller.ClientController;
import client.controller.actions.ClientAction;
import communication.link.ServerConnection;
import communication.messages.Message;

public class EndDraggableMotionAction implements Action  {

	private int cardId;
	public EndDraggableMotionAction( int cardId) {
		this.cardId = cardId;
		
	}

	@Override
	public void execute() {
		ClientController.getController().getGui().endDraggableMotion(cardId);
		
	}

}
