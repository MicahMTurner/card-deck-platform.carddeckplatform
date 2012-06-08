package president;

import utils.Button;
import utils.Card;
import client.controller.ClientController;
import handlers.ButtonEventsHandler;

public class ButtonHandler implements ButtonEventsHandler{

	@Override
	public void onClick() {
		ClientController.get().getMe().endTurn();
		President.passed=true;
		
	}

	@Override
	public boolean onFlipCard(Card card) {
		// TODO Auto-generated method stub
		return false;
	}

}
