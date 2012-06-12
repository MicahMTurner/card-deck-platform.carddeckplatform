package client.gui.animations;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import client.gui.entities.Droppable;

public class GlowAnimation extends AsyncTask<Void, Void, Void> {
	Droppable droppable;
	long duration;
	boolean running=true;
	boolean pause=false;

	public GlowAnimation(Droppable droppable, long duration) {
		this.duration = duration;
		this.droppable = droppable;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		ColorInterpolator colorinterpolator = new ColorInterpolator(
				Color.TRANSPARENT, Color.argb(255, 255, 255, 255));
		Interpolator animateInterpolator = new LinearInterpolator();
		long startTime = System.currentTimeMillis();
		long endTime = startTime + duration;
		long curTime = System.currentTimeMillis();
		float percentTime = (float) (curTime - startTime)
				/ (float) (endTime - startTime);
		float percentDistance = animateInterpolator
				.getInterpolation(percentTime);
		
		
		float degree=40;//controlling with the degree on the cycle period
		percentDistance=(float) (Math.cos(percentDistance*degree)+1)/2;
		int glowColor = colorinterpolator.getInterpolatedColor(percentDistance);
		droppable.setGlowColor(glowColor);

		while (percentTime < 1.0) {
			curTime = System.currentTimeMillis();
			percentTime = (float) (curTime - startTime)
					/ (float) (endTime - startTime);
			percentDistance = animateInterpolator.getInterpolation(percentTime);
			percentDistance=(float) (Math.cos(percentDistance*degree)+1)/2;
			glowColor = colorinterpolator.getInterpolatedColor(percentDistance);
			droppable.setGlowColor(glowColor);
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}

		}
		droppable.setGlowColor(Color.TRANSPARENT);

		return null;
	}

}
