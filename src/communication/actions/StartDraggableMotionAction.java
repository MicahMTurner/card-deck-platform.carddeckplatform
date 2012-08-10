package communication.actions;

import client.controller.ClientController;

public class StartDraggableMotionAction implements Action  {
	int cardId;
	String sender;
	
	public StartDraggableMotionAction(int cardId, String sender){
		this.cardId = cardId;
		this.sender = sender;
	}
	
	
	@Override
	public void execute() {
		ClientController.get().getGui().startDraggableMotion(sender, cardId);
	}
}
