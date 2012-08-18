package communication.actions;

import client.controller.ClientController;

public class InvalidMoveAction implements Action {

	int cardId;
	
	public InvalidMoveAction(int cardId){
		this.cardId = cardId;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ClientController.get().invalidMove(cardId);
	}

}
