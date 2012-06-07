package utils.droppableLayouts.line;

import java.util.Random;

import utils.Card;
import utils.Point;
import utils.droppableLayouts.DroppableLayout;
import utils.droppableLayouts.LineLayout;
import utils.droppableLayouts.DroppableLayout.LayoutType;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;

public class BottomLineLayout extends LineLayout {

	public BottomLineLayout(Droppable droppable) {
		super(droppable);
	}

	@Override
	public void rearrange(int index, float width, float height) {
		int numberOfCards = droppable.cardsHolding();

		if (numberOfCards == 0)
			return;
		Point newLocation = null;
		Point location = new Point(droppable.getX(), droppable.getY());
		float[][] animationArgs = new float[3][numberOfCards];

		// gets the step that each card would move.
//		float step = MetricsConvertion.pointRelativeToPx(new Point(3, 0))
//				.getX();

//		newLocation = new Point(0, 0);
//		float[] diff = new float[numberOfCards - 1];
//
//		for (int i = 0; i < index; i++) {
//			diff[i] = numberOfCards-Math.abs(index - i) + 1;
//		}
//		// second loop
//		for (int i = index + 1; i < numberOfCards; i++) {
//			diff[i - 1] = numberOfCards-Math.abs(index - i) + 1;
//		}
//		
//		//find min
//		float min=10000;
//		for (int i = 0; i < diff.length; i++)
//			if(diff[i]<min)
//				min=diff[i];
//		for (int i = 0; i < diff.length; i++)
//			diff[i]=diff[i]-min;
//		
//		for (int i = 0; i < diff.length; i++) {
//			System.err.println(diff[i]);
//		}
//		animationArgs[0][0] = 0;
//		for (int i = 1; i < numberOfCards; i++)
//			animationArgs[0][i] = animationArgs[0][i - 1] + diff[i - 1];
		
		for (int i = 0; i < numberOfCards; i++) {
			animationArgs[0][i] = (i+1);
			animationArgs[1][i] = 0;
			animationArgs[2][i] = 0;
		}

		
		
		animate(droppable.getCards(),
				shift(normalizePosition(animationArgs, width, height),
						location.getX()-width/2, location.getY()), 1000);
	}

	
}
