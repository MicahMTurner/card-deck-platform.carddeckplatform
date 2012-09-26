package communication.actions;

import communication.link.ServerConnection;

import client.controller.ClientController;

public class PlayerLeftAction implements Action{
	
	@Override
	public void execute() {
		ClientController.get().playerLeft();
		ServerConnection.getConnection().closeConnection();	
	}

}
