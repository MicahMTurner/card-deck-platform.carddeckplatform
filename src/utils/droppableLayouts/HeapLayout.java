package utils.droppableLayouts;

import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;
import utils.Point;

public class HeapLayout extends DroppableLayout {
	
	
	public HeapLayout(Droppable droppable) {
		super(droppable);
	}
	@Override
	public void rearrange(int index, float width, float height) {
		int numberOfCards = droppable.cardsHolding();
		//Point step = new MetricsConvertion().pointRelativeToPx(new Point(1,1));
		if (numberOfCards == 0)
			return;
		Point location = new Point(droppable.getX(), droppable.getY());
		float[][] animationArgs = new float[3][numberOfCards];
		
		for (int i = 0; i < numberOfCards; i++) {
			animationArgs[0][i] = i+1;
			animationArgs[1][i] = i+1;
			animationArgs[2][i] = 0;	
		}
		
		animate(droppable.getCards(),
				shift(normalizePosition(animationArgs, width/4, height/4),
						location.getX(), location.getY()), 1000);
	}

	@Override
	public LayoutType getType() {
		// TODO Auto-generated method stub
		return LayoutType.HEAP;
	}

}
