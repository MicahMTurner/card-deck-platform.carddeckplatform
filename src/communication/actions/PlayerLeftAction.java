package communication.actions;

import client.controller.ClientController;

public class PlayerLeftAction implements Action{
	
	@Override
	public void execute() {
		ClientController.get().playerLeft();
		
	}

}
