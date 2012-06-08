package utils.droppableLayouts;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import utils.Point;
import utils.Position;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;

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
			int numberOfCards = droppable.cardsHolding();

			if (numberOfCards == 0)
				return;
			Point newLocation = null;
			Point location = new Point(droppable.getX(), droppable.getY());
			float[][] animationArgs = new float[3][numberOfCards];
			Point point=MetricsConvertion.pointRelativeToPx(droppable.getCards().get(0).getScale());
			
			if(point.getX()*numberOfCards<=width*1.8){//in case there is a lot of place 
				for (int i = 0; i < numberOfCards; i++) {
					animationArgs[0][i] = (i+1);
					animationArgs[1][i] = 0;
					animationArgs[2][i] = 0;
				}
				
				animationArgs=normalizePosition(animationArgs, width, height);
				
			}else{
				float position=findIndexPlace(point.getX(), width, index, numberOfCards);
				float  leftSize=position-point.getX();
				float  rightSize=width-(position+point.getX());
				
				//initialize the middle
				animationArgs[0][index]=position;
				animationArgs[1][index] = 0;
				animationArgs[2][index] = 0;
				
				//rearrange left size
				Interpolator interpolator=new AccelerateInterpolator();
				animationArgs[0][0]=0;
				animationArgs[1][0] = 0;
				animationArgs[2][0] = 0;
				for(int i=1;i<index;i++){
					System.out.println(i+"::"+(index-1)+"::"+((float)i/(index-1)));
					animationArgs[0][i]=leftSize*interpolator.getInterpolation((float)i/(index-1));
					animationArgs[1][i] = 0;
					animationArgs[2][i] = 0;
				}
				//rearange right size
				interpolator=new DecelerateInterpolator();
				animationArgs[0][numberOfCards-1]=width;
				animationArgs[1][numberOfCards-1] = 0;
				animationArgs[2][numberOfCards-1] = 0;
				for(int i=index+1;i<numberOfCards-1;i++){
					animationArgs[0][i]=point.getX()+position+rightSize*interpolator.getInterpolation((float)(i-(index+1))/((numberOfCards-1)-(index+1)));
					animationArgs[1][i] = 0;
					animationArgs[2][i] = 0;
					
				}
				
				
				
			}
			
			animate(droppable.getCards(),
					shift(animationArgs,
							location.getX()-width/2, location.getY()), 1000);
		}
		
		
		
		private float findIndexPlace(float cardSize,float length,int cardsIndex,int numberOfCards){
			float middle=length/2;
			float factor=1.8f;
			if(cardsIndex*cardSize<middle*factor)
				return (float) (cardSize*cardsIndex/factor);
			else if((numberOfCards-cardsIndex)*cardSize<middle*factor)
				return (float) (length-(numberOfCards-cardsIndex)*cardSize/factor);
			else
				return middle;
			
			
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

	
