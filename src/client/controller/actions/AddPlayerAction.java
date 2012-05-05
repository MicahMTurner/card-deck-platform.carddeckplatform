package client.controller.actions;

import utils.Player;
import client.controller.ClientController;
import client.gui.entities.GuiPlayer;
import communication.actions.Action;



public class AddPlayerAction implements Action{
	
	private Player newPlayerInfo;
	
	public AddPlayerAction(Player newPlayerInfo) {
		this.newPlayerInfo=newPlayerInfo;
	}
	@Override
	public void execute() {		
		ClientController.getController().addPlayer(newPlayerInfo);
	}


}
