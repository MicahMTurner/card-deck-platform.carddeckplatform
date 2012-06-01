package utils.droppableLayouts;

import utils.Card;
import utils.Point;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;

public class RightLineLayout extends DroppableLayout {

	public RightLineLayout(Droppable droppable) {
		super(droppable);
	}

	@Override
	public void rearrange() {
		Point newLocation=null;
		Point location = new Point(droppable.getX() , droppable.getY());
		int numberOfCards = droppable.getCards().size();
		
		// gets the step that each card would move.
		float step = MetricsConvertion.pointRelativeToPx(new Point(0 , 3)).getY();
		if(numberOfCards>0)
			newLocation = new Point(location.getX() , (int)(location.getY() - step * numberOfCards/2));
		
		
		for(Card c : droppable.getCards()){
			//c.setLocation(newLocation.getX(), newLocation.getY());
			
			animate(c , newLocation, -90,1000);
			newLocation.setY(newLocation.getY() + step);
		}
	}

}
