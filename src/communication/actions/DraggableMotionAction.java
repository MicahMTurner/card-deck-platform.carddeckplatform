package communication.actions;

import client.controller.ClientController;
import client.controller.actions.ClientAction;
import server.controller.actions.SendToAllExceptMe;
import communication.link.ServerConnection;
import communication.messages.Message;
import carddeckplatform.game.TableView;

public class DraggableMotionAction implements Action {

	private String username;
	private int cardId;
	private int x; 
	private int y;
	
	public DraggableMotionAction( String username, int cardId, int x, int y) {
		this.username = username;
		this.cardId = cardId;
		this.x = x;
		this.y = y;		
	}


	@Override
	public void execute() {
		ClientController.getController().getGui().draggableMotion(username, cardId, x, y);
		
	}

}
