package communication.actions;

import client.controller.ClientController;

public class EndRoundAction implements Action{

	@Override
	public void execute() {
		ClientController.get().endRound();
		
	}

}
