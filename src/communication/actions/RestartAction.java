package communication.actions;

import client.controller.ClientController;

public class RestartAction implements Action {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ClientController.get().restart();
		
		//GameActivity.restart();
	}

}
