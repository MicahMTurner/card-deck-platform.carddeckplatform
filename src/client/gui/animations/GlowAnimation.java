package client.gui.animations;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import client.gui.entities.Droppable;

public class GlowAnimation extends Animation {
	Droppable droppable;
	long duration;
	boolean running=true;
	boolean pause=false;
	int colorArbg;

	public GlowAnimation(Droppable droppable, long duration, int colorArbg) {
		this.duration = duration;
		this.droppable = droppable;
		this.colorArbg = colorArbg;
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

//	@Override
//	protected Void doInBackground(Void... arg0) {
//		
//		return null;
//	}

	@Override
	protected void animate() {
		if(droppable==null)
			return;
		//System.out.println("GLOW Animation Activated");
		ColorInterpolator colorinterpolator = new ColorInterpolator(
				Color.TRANSPARENT, colorArbg);
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
			//glowColor+=255;
			droppable.setGlowColor(glowColor);
			//System.out.println(curTime+","+glowColor);
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}

		}
		droppable.setGlowColor(droppable.originalDroppableColor);
		
	}

	@Override
	protected void postAnimation() {		
		
	}

}
