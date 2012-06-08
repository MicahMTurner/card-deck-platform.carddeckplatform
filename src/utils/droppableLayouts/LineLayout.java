package utils.droppableLayouts;

import utils.Point;
import utils.Position;
import client.gui.entities.Droppable;

public class LineLayout extends DroppableLayout {
	
	
	private Rearranger rearranger;
	
	
	public LineLayout(Droppable droppable) {
		super(droppable);
		// TODO Auto-generated constructor stub
		
		Position.Player playerPos = (Position.Player)(this.droppable).getPosition();
		
		switch (playerPos) {
		case BOTTOM:
			rearranger = new BottomRearranger();
			break;
		case TOP:
			rearranger = new TopRearranger();
			break;
		case LEFT:
			rearranger = new LeftRearranger();
			break;
		case RIGHT:
			rearranger = new RightRearranger();
			break;
		default:
			break;
		}
	}

	private class BottomRearranger implements Rearranger{
		@Override
		public void rearrange(int index, float width, float height) {
			// TODO Auto-generated method stub
			int numberOfCards = droppable.cardsHolding();

			if (numberOfCards == 0)
				return;
			Point newLocation = null;
			Point location = new Point(droppable.getX(), droppable.getY());
			float[][] animationArgs = new float[3][numberOfCards];
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
	
	private class TopRearranger implements Rearranger{
		@Override
		public void rearrange(int index, float width, float height) {
			// TODO Auto-generated method stub
			int numberOfCards = droppable.cardsHolding();

			if (numberOfCards == 0)
				return;
			Point newLocation = null;
			Point location = new Point(droppable.getX(), droppable.getY());
			float[][] animationArgs = new float[3][numberOfCards];
				for (int i = 0; i < numberOfCards; i++) {
				animationArgs[0][numberOfCards-i-1] = (i+1);
				animationArgs[1][i] = 0;
				animationArgs[2][i] = 0;
					
				
			}
			animate(droppable.getCards(),
					shift(normalizePosition(animationArgs, width, height),
							location.getX()-width/2, location.getY()), 1000);
		}
	}
	
	
	private class LeftRearranger implements Rearranger{
		@Override
		public void rearrange(int index, float width, float height) {
			// TODO Auto-generated method stub
			int numberOfCards = droppable.cardsHolding();

			if (numberOfCards == 0)
				return;
			Point newLocation = null;
			Point location = new Point(droppable.getX(), droppable.getY());
			float[][] animationArgs = new float[3][numberOfCards];
				for (int i = 0; i < numberOfCards; i++) {
				animationArgs[0][i] = 0;
				animationArgs[1][i] = (i+1);
				animationArgs[2][i] = 0;
			}
			animate(droppable.getCards(),
					shift(normalizePosition(animationArgs, width, height),
							location.getX(), location.getY() - height / 2), 1000);
		}
	}

	
	private class RightRearranger implements Rearranger{
		@Override
		public void rearrange(int index, float width, float height) {
			// TODO Auto-generated method stub
			int numberOfCards = droppable.cardsHolding();

			if (numberOfCards == 0)
				return;
			Point newLocation = null;
			Point location = new Point(droppable.getX(), droppable.getY());
			float[][] animationArgs = new float[3][numberOfCards];
				for (int i = 0; i < numberOfCards; i++) {
				animationArgs[0][i] = 0;
				animationArgs[1][numberOfCards-i-1] = (i+1);
				animationArgs[2][i] = 0;
			}
			animate(droppable.getCards(),
					shift(normalizePosition(animationArgs, width, height),
							location.getX(), location.getY() - height / 2), 1000);
		}
	}


	@Override
	public LayoutType getType() {
		return LayoutType.LINE;
	}
	
	@Override
	public void rearrange(int index, float width, float height) {
		// TODO Auto-generated method stub
		rearranger.rearrange(index, width, height);
	}


}

	
