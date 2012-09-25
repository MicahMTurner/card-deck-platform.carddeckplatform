package communication.actions;

import client.controller.ClientController;

public class FlipCardAction implements Action {

	private int cardId;
	
	public FlipCardAction(int cardId){
		this.cardId = cardId;
	}
	
	@Override
	public void execute() {
		ClientController.get().flipCard(cardId);
		
	}

}
