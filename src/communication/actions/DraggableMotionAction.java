package communication.actions;

import client.controller.ClientController;

public class DraggableMotionAction implements Action {

	private String username;
	private int cardId;
	private int x; 
	private int y;
	
	public DraggableMotionAction( String username, int cardId, int x, int y) {
		this.username = username;
		this.cardId = cardId;
		this.x = x;
		this.y = y;		
	}


	@Override
	public void execute() {
		ClientController.get().getGui().draggableMotion(username, cardId, x, y);
		
	}

}
