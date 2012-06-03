package utils.droppableLayouts;

import java.util.Random;

import utils.Card;
import utils.Point;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;

public class BottomLineLayout extends DroppableLayout {

	public BottomLineLayout(Droppable droppable) {
		super(droppable);
		// TODO Auto-generated constructor stub
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
		float step = MetricsConvertion.pointRelativeToPx(new Point(3, 0))
				.getX();

		newLocation = new Point(0, 0);
		float[] diff = new float[numberOfCards - 1];

		for (int i = 0; i < index; i++) {
			diff[i] = numberOfCards-Math.abs(index - i) + 1;
		}
		// second loop
		for (int i = index + 1; i < numberOfCards; i++) {
			diff[i - 1] = numberOfCards-Math.abs(index - i) + 1;
		}
		
		//find min
		float min=10000;
		for (int i = 0; i < diff.length; i++)
			if(diff[i]<min)
				min=diff[i];
		for (int i = 0; i < diff.length; i++)
			diff[i]=diff[i]-min;
		
		for (int i = 0; i < diff.length; i++) {
			System.err.println(diff[i]);
		}
		animationArgs[0][0] = 0;
		for (int i = 1; i < numberOfCards; i++)
			animationArgs[0][i] = animationArgs[0][i - 1] + diff[i - 1];
		for (int i = index; i < numberOfCards; i++) {
			animationArgs[1][i] = newLocation.getY();
			animationArgs[2][i] = 0;
		}

		animate(droppable.getCards(),
				shift(normalizePosition(animationArgs, width, height),
						location.getX(), location.getY()), 1000);
	}
}
