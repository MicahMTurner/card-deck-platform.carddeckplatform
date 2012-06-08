package communication.actions;

import client.controller.ClientController;

public class InvalidMoveAction implements Action {

	int cardId;
	int fromId;
	public InvalidMoveAction(int cardId,int fromId){
		this.cardId = cardId;
		this.fromId = fromId;
	}
	
	@Override
	public void execute() {
		ClientController.get().invalidMove(cardId,fromId);
	}

}
