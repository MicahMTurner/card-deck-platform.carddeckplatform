package communication.actions;

import client.controller.ClientController;
import utils.Position;


public class EndTurnAction implements Action{
	
	int playerId;
	
	public EndTurnAction(int playerId) {
		this.playerId=playerId;
	}
	
	@Override
	public void execute() {
		//ClientController.get().endTurn();
		
	}

}
