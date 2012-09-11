package communication.actions;


import client.controller.ClientController;
import utils.Player;
import utils.Position;



public class Turn implements Action{
	int playerId;
	public Turn(int playerId) {
		this.playerId=playerId;
		
	}
	@Override
	public void execute() {
		ClientController.get().playerTurn(playerId);
		
	}

}
