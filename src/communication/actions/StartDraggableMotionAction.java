package communication.actions;

import client.controller.ClientController;

public class StartDraggableMotionAction implements Action  {
	int cardId;
	String sender;
	int fromId;
	
	public StartDraggableMotionAction(int cardId,int fromId, String sender){
		this.cardId = cardId;
		this.sender = sender;
		this.fromId = fromId;
	}
	
	
	@Override
	public void execute() {
		ClientController.get().getGui().startDraggableMotion(sender, cardId,fromId);
	}
}
