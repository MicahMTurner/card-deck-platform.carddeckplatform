package communication.actions;

import client.controller.ClientController;
import utils.Position;


public class EndTurnAction implements Action{
	
	Position.Player position;
	
	public EndTurnAction(Position.Player position) {
		this.position=position;
	}
	
	@Override
	public void execute() {
		//ClientController.get().endTurn();
		
	}

}
