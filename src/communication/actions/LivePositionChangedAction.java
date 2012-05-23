package communication.actions;

import utils.Position;
import client.controller.ClientController;

public class LivePositionChangedAction implements Action {
	private int playerId;
	private Position.Player newPosition;
	
	public LivePositionChangedAction() {}
	
	public LivePositionChangedAction(int playerId,Position.Player newPosition) {
		this.playerId=playerId;
		this.newPosition=newPosition;
	}
	
	@Override
	public void execute() {
		ClientController.get().positionUpdate(playerId, newPosition);
	}

}
