package client.gui.animations;

import utils.Card;
import android.os.AsyncTask;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import client.controller.ClientController;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;

public class OvershootAnimation extends AsyncTask<Void, Void, Void> {

	Droppable source;
	Droppable destination;
	//Draggable draggable;
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
	protected Void doInBackground(Void... arg0) {
		
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
			card.setLocation(card.getX() + curDx, card.getY() + y);

			while (percentTime < 1.0) {
				System.out.println(card.getX() + "::" + card.getY());
				curTime = System.currentTimeMillis();
				percentTime = (float) (curTime - startTime)
						/ (float) (endTime - startTime);
				percentDistance = animateInterpolator
						.getInterpolation(percentTime);
				curDx = percentDistance * totalAnimDx;
				curDy = percentDistance * totalAnimDy;
				card.setLocation(x + curDx, y + curDy);
				try {
					Thread.sleep(4);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			card.setLocation(destX, destY);
//			source.removeCard(null, card);
//			destination.addCard(null, card);
			System.out.println("END");
		
		
		return null;
	}
	
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if(sendToCommunication){
			if (!destination.onDrop(ClientController.get().getMe(), source,
					((Card) card))){
				card.invalidMove();
			}
		}else{
//			source.removeCard(null, card);
//			destination.addCard(null, card);
		}
		
		
		
		
	}

}
