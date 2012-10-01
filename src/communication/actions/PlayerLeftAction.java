package communication.actions;

import client.controller.ClientController;

import communication.link.ServerConnection;

public class PlayerLeftAction implements Action{
	
	@Override
	public void execute() {
		ClientController.get().playerLeft();
		ServerConnection.getConnection().closeConnection();	
	}

}
