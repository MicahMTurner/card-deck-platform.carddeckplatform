package communication.actions;


import client.controller.ClientController;
import utils.Player;
import utils.Position;



public class Turn implements Action{
	Position.Player player;
	public Turn(Position.Player player) {
		this.player=player;
		
	}
	@Override
	public void execute() {
		ClientController.get().playerTurn(player.getId());
		
	}

}
