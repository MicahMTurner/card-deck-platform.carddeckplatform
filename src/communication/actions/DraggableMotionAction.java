package communication.actions;

import utils.Point;
import utils.Position;
import client.controller.ClientController;
import client.gui.entities.MetricsConvertion;

public class DraggableMotionAction implements Action {

	
	// TODO: Add indicator about the position of the player who moves the card.
	private String username;
	private int cardId;
	private float x; 
	private float y;
	private Position.Player position;
	
	public DraggableMotionAction( String username, int cardId, float x, float y) {
		this.username = username;
		this.cardId = cardId;
		this.x = x;
		this.y = y;	
		this.position = ClientController.get().getMe().getGlobalPosition();
	}


	@Override
	public void execute() {
		Point p = new Point(x,y);
		Position.Player playerRelPos = position.getRelativePosition(ClientController.get().getMe().getGlobalPosition());
		switch (playerRelPos) {
		case TOP:
			p = MetricsConvertion.pointRelativeToPx(MetricsConvertion.fromTop(p));
			break;
		case LEFT:
			p = MetricsConvertion.pointRelativeToPx(MetricsConvertion.fromLeft(p));
			break;
		case RIGHT:
			p = MetricsConvertion.pointRelativeToPx(MetricsConvertion.fromRight(p));
			break;
		default:
			break;
		}
		
		ClientController.get().getGui().draggableMotion(username, cardId, p.getX(), p.getY());
		
	}

}
