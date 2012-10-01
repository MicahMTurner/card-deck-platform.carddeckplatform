package communication.actions;

import utils.Player;
import client.controller.ClientController;



public class AddPlayerAction implements Action{
	
	private Player newPlayerInfo;
	
	public AddPlayerAction(Player newPlayerInfo) {
		this.newPlayerInfo=newPlayerInfo;
	}
	@Override
	public void execute() {		
		ClientController.get().addPlayer(newPlayerInfo);
	}


}
