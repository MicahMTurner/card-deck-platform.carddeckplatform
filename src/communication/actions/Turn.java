package communication.actions;


import client.controller.ClientController;
import utils.Player;
import utils.Position;



public class Turn implements Action{
	Position.Player position;
	public Turn(Position.Player position) {
		this.position=position;
		
	}
	@Override
	public void execute() {
		ClientController.getController().playerTurn(position);
		
	}

}
