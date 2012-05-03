package client.controller.actions;

import utils.Player;
import client.controller.ClientController;
import communication.actions.Action;



public class AddPlayerAction implements Action{
	
	private Player newPlayer;
	
	public AddPlayerAction(Player newPlayer) {
		this.newPlayer=newPlayer;
	}
	@Override
	public void execute() {		
		ClientController.getController().addPlayer(newPlayer);
	}


}
