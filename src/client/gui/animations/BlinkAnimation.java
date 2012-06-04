package client.gui.animations;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import client.gui.entities.Droppable;

public class BlinkAnimation extends AsyncTask<Void, Void, Void> {
	Droppable droppable;
	long duration;
	
	
	
	public BlinkAnimation(Droppable droppable,long duration) {
		this.duration=duration;
		this.droppable=droppable;
	}
	@Override
	protected Void doInBackground(Void... arg0) {
		ColorInterpolator colorinterpolator= new ColorInterpolator(Color.TRANSPARENT, Color.argb(255, 255, 255, 255));
		Interpolator animateInterpolator = new AccelerateDecelerateInterpolator();
		long startTime = System.currentTimeMillis();
		long endTime = startTime + duration;
		long curTime = System.currentTimeMillis();
		float percentTime = (float) (curTime - startTime)
				/ (float) (endTime - startTime);
		float percentDistance = animateInterpolator
				.getInterpolation(percentTime);
		int curDAlpha = (int) (percentDistance * 255);
		droppable.setAlpha(curDAlpha);

		while (percentTime < 1.0) {
			curTime = System.currentTimeMillis();
			percentTime = (float) (curTime - startTime)
					/ (float) (endTime - startTime);
			percentDistance = animateInterpolator
					.getInterpolation(percentTime);
			curDAlpha = (int) (percentDistance * 255*5)%250+50;
			droppable.setAlpha(curDAlpha);
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		droppable.setAlpha(255);

		
		
		
		
		
		return null;
	}

}
