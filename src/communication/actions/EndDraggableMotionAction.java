package communication.actions;

import client.controller.ClientController;

public class EndDraggableMotionAction implements Action  {

	private int cardId;
	public EndDraggableMotionAction( int cardId) {
		this.cardId = cardId;
		
	}

	@Override
	public void execute() {
		ClientController.get().endDraggableMotion(cardId);
		
	}

}
