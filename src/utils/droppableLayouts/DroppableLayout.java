package utils.droppableLayouts;

import java.io.Serializable;

import logic.client.Game;

import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.gui.entities.Droppable;
import utils.Card;
import utils.Point;

public abstract class DroppableLayout implements Serializable {
	public enum Orientation{HORIZONTAL, VERTICAL}
	
	protected Orientation orientation;
	protected Droppable droppable;
	
	
	public DroppableLayout(Droppable droppable){
		this.orientation = orientation;
		this.droppable = droppable;
	}
	
	public abstract void rearrange();
	public void animate(final Card card, final Point dest, final float finalAngle,final long duration) {
		
		GameEnvironment.get().getHandler().post(new Runnable() {
			
			@Override
			public void run() {
				Interpolator animateInterpolator = new AccelerateDecelerateInterpolator();
				float totalAnimDx=dest.getX()-card.getX();
				float totalAnimDy=dest.getY()-card.getY();
				float totalAngle=(float) (finalAngle-card.getAngle());
				long startTime = System.currentTimeMillis();
				long endTime = startTime + duration;
				long curTime = System.currentTimeMillis();
				
				float percentTime = (float) (curTime - startTime)
						/ (float) (endTime - startTime);
				float percentDistance = animateInterpolator
						.getInterpolation(percentTime);
				float curDx = percentDistance * totalAnimDx;
				float curDy = percentDistance * totalAnimDy;
				float curDAngle=percentDistance *totalAngle;
				
				//save the initial value
				float initialAngle=card.getAngle();
				float x = card.getX();
				float y = card.getY();
				card.setLocation(card.getX() + curDx, card.getY() + y);
				card.setAngle(initialAngle+curDAngle);
				while (percentTime < 1.0) {
					System.out.println(card.getX() + "::" + card.getY());
					curTime = System.currentTimeMillis();
					percentTime = (float) (curTime - startTime)
							/ (float) (endTime - startTime);
					percentDistance = animateInterpolator
							.getInterpolation(percentTime);
					curDx = percentDistance * totalAnimDx;
					curDy = percentDistance * totalAnimDy;
					curDAngle=percentDistance *totalAngle;
					card.setLocation(x + curDx, y + curDy);
					curDAngle=percentDistance *totalAngle;
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				card.setLocation(dest.getX(), dest.getY());
				card.setAngle(finalAngle);
				System.out.println("END");
				
				
			}
		});
		
	}
}
