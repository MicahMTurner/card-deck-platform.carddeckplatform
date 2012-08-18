package client.gui.entities;

import android.view.MotionEvent;

public interface TouchHandler {
	public boolean onDoubleTap(MotionEvent arg0);

	public boolean onDoubleTapEvent(MotionEvent arg0);

	public boolean onSingleTapConfirmed(MotionEvent arg0);

	public boolean onDown(MotionEvent arg0);

	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3);

	public void onLongPress(MotionEvent arg0);

	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3);

	public boolean onSingleTapUp(MotionEvent arg0);
	
	public boolean onRotate(MotionEvent arg0,float angleRadians);
	
	public boolean onPinch(MotionEvent arg0,float currentDistance,float previousDistance,float scale);
}
