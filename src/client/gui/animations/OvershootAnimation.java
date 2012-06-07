package client.gui.animations;

import communication.actions.DraggableMotionAction;
import communication.link.ServerConnection;

import utils.Card;
import utils.Point;
import android.os.AsyncTask;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import client.controller.ClientController;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;

public class OvershootAnimation extends Animation {

	Droppable source;
	Droppable destination;
	long duration;
	float totalAnimDx;
	float totalAnimDy;
	Card card;
	boolean sendToCommunication;
	float destX,destY;

	public OvershootAnimation(Droppable from, Droppable to,
			Card card, long duration, float totalAnimDx,
			float totalAnimDy,  boolean sendToCommunication) {
		super();
		this.source = from;
		this.destination = to;
		this.duration = duration;
		this.totalAnimDx = totalAnimDx;
		this.totalAnimDy = totalAnimDy;
		this.card = card;
		this.sendToCommunication = sendToCommunication;
		this.destX=to.getX();
		this.destY=to.getY();
	}
	public OvershootAnimation(float destX,float destY,
			Card card, long duration,  boolean sendToCommunication){
		this.sendToCommunication=sendToCommunication;
		this.card=card;
		this.duration=duration;
		this.destX=destX;
		this.destY=destY;
		this.totalAnimDx = destX- card.getX();
		this.totalAnimDy = destY-card.getY();
		
	}
	
	@Override
	protected void animate() {
		float minSize=MetricsConvertion.pointRelativeToPx(new Point(10,5)).getX();
		if(totalAnimDx<minSize && totalAnimDy<minSize ){
//			card.setLocation(destX, destY);
			this.duration=250;
//			return ;
		}
		System.out.println("OvershootAnimation.doInBackground()");
		Interpolator animateInterpolator = new OvershootInterpolator();
		long startTime = System.currentTimeMillis();
		long endTime = startTime + duration;
		long curTime = System.currentTimeMillis();
		float percentTime = (float) (curTime - startTime)
				/ (float) (endTime - startTime);
		float percentDistance = animateInterpolator
				.getInterpolation(percentTime);
		float curDx = percentDistance * totalAnimDx;
		float curDy = percentDistance * totalAnimDy;
		float x = card.getX();
		float y = card.getY();
		card.setLocation(x + curDx, y + curDy);
		
		if(sendToCommunication)
			card.onDrag();
		
		while (percentTime < 1.0) {
			curTime = System.currentTimeMillis();
			percentTime = (float) (curTime - startTime)
					/ (float) (endTime - startTime);
			percentDistance = animateInterpolator
					.getInterpolation(percentTime);
			curDx = percentDistance * totalAnimDx;
			curDy = percentDistance * totalAnimDy;
			card.setLocation(x + curDx, y + curDy);
			
			if(sendToCommunication)
				card.onDrag();
			
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		card.setLocation(destX, destY);
		System.out.println("END");
		
	}
	@Override
	protected void postAnimation() {
		if(sendToCommunication){
			
			ClientController.sendAPI().endDragMotion(card.getId());
			if (!destination.onDrop(ClientController.get().getMe(), source,
					((Card) card))){
				card.invalidMove();
			}
			
		}
		card.onRelease();
		
	}
	
//	@Override
//	protected void onPostExecute(Void result) {
//		// TODO Auto-generated method stub
//		super.onPostExecute(result);
//		
//		if(sendToCommunication){
//			
//			ClientController.sendAPI().endDragMotion(card.getId());
//			if (!destination.onDrop(ClientController.get().getMe(), source,
//					((Card) card))){
//				card.invalidMove();
//			}
//			
//		}
//		card.onRelease();
//		
//	}

}
