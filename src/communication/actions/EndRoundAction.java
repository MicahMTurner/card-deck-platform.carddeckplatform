package communication.actions;

import client.controller.ClientController;

public class EndRoundAction implements Action{
	private int nextPlayerId;
	public EndRoundAction(int nextPlayerId) {
		this.nextPlayerId=nextPlayerId;
	}
	public EndRoundAction() {	
	}
	@Override
	public void execute() {
		ClientController.get().endRound();
		
	}

}
