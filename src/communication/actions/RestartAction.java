package communication.actions;

import carddeckplatform.game.GameActivity;
import client.controller.ClientController;

public class RestartAction implements Action {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ClientController.get().restart();
		
		//GameActivity.restart();
	}

}
