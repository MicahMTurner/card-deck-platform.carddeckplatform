package communication.actions;


import client.controller.ClientController;
import client.gui.entities.GuiPlayer;
import utils.Player;
import utils.Position;



public class Turn implements Action{
	Position.Player player;
	public Turn(Position.Player player) {
		this.player=player;
		
	}
	@Override
	public void execute() {
		ClientController.getController().playerTurn(player.getId());
		
	}

}
