package president;

import client.controller.ClientController;
import handlers.ButtonEventsHandler;

public class ButtonHandler implements ButtonEventsHandler{

	@Override
	public void onClick() {
		ClientController.get().getMe().endTurn();
		President.passed=true;
		
	}

}
