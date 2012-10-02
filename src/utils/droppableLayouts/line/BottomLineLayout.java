package utils.droppableLayouts.line;

import utils.Point;
import utils.droppableLayouts.LineLayout;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
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
