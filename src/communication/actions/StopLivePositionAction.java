package communication.actions;

import client.controller.LivePosition;

public class StopLivePositionAction implements Action{
	public StopLivePositionAction() {
	}
	
	@Override
	public void execute() {
		LivePosition.get().stop();
		
	}
	
}
