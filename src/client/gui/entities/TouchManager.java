package client.gui.entities;


import utils.Point;


import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TouchManager implements GestureDetector.OnGestureListener,
		GestureDetector.OnDoubleTapListener {
	private final TouchHandler handler;
	private final int maxNumberOfTouchPoints;
	private final Point[] points;
	private final Point[] previousPoints;
	private GestureDetector gestures;
	private float scale=1;
	private float angle=0;
	private boolean angleFlag = true;
	private boolean scaleFlag = true;
	private boolean multitouchFlag = true;
	private boolean ismultitouch = false;
	public TouchManager(Context context,final TouchHandler handler,int maxNumberOfTouchPoints) {
		this.maxNumberOfTouchPoints=maxNumberOfTouchPoints;
		this.points=new Point[maxNumberOfTouchPoints];
		this.previousPoints=new Point[maxNumberOfTouchPoints];
		this.handler=handler;
		gestures= new GestureDetector(context,this, null, false);
		
		
	}
	public boolean isPressed(int index) {
		return points[index] != null;
	}

	public int getPressCount() {
		int count = 0;
		for(Point point : points) {
			if (point != null)
				++count;
		}
		return count;
	}

	public Point moveDelta(int index) {

		if (isPressed(index)) {
			Point previous = previousPoints[index] != null ? previousPoints[index] : points[index];
			return Point.subtract(points[index], previous);
		}
		else {
			return new Point();
		}
	}

	private static Point getVector(Point a, Point b) {
		if (a == null || b == null)
			throw new RuntimeException("can't do this on nulls");

		return Point.subtract(b, a);
	}

	public Point getPoint(int index) {
		return points[index] != null ? points[index] : new Point();
	}

	public Point getPreviousPoint(int index) {
		return previousPoints[index] != null ? previousPoints[index] : new Point();
	}

	public Point getVector(int indexA, int indexB) {
		return getVector(points[indexA], points[indexB]);
	}

	public Point getPreviousVector(int indexA, int indexB) {
		if (previousPoints[indexA] == null || previousPoints[indexB] == null)
			return getVector(points[indexA], points[indexB]);
		else
			return getVector(previousPoints[indexA], previousPoints[indexB]);
	}

	
	
	public static float getDegreesFromRadians(float angle) {
		while(angle<0){
			angle+=Math.PI*2;
		}
		while(angle>Math.PI*2)
			angle-=Math.PI*2;
		
		return (float)(angle * 180.0 / Math.PI);
	}
	
	public void resetAngle(){
		this.angle=0;
	}
	public void resetScale(){
		this.scale=1;
	}
	
	
	
	
	
	
	
	/************************************************************************************************************************************
	 * 
	 * 		Touch methods  
	 * 
	 * 
	 ************************************************************************************************************************************/
	
	boolean isValidSingleTap(MotionEvent event){
		if(event.getPointerCount()>1)
			return false;
		if(event.getPointerId(0)!=0)
			return false;
		return true;
	}
	/**
	 * updating the pointers on the screen by their id
	 * 
	 * @param event is the touch event from the screen
	 */
	
	public void update(MotionEvent event) {
			
		   int actionCode = event.getAction() & MotionEvent.ACTION_MASK;

		   if (actionCode == MotionEvent.ACTION_POINTER_UP || actionCode == MotionEvent.ACTION_UP) {
			   int index = event.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT;
			   previousPoints[index] = points[index] = null;
		   }
		   else {
				for(int i = 0; i < maxNumberOfTouchPoints; ++i) {
					if (i < event.getPointerCount()) {
						int index = event.getPointerId(i);

						Point newPoint = new Point(event.getX(i), event.getY(i));
						try{
						if (points[index] == null)
							points[index] = newPoint;
						else {
							if (previousPoints[index] != null) {
								previousPoints[index]=(points[index]);
							}
							else {
								previousPoints[index] = new Point(newPoint);

							}

							if (Point.subtract(points[index], newPoint).getLength() < 64)
								points[index]=(newPoint);
						}
						}catch(ArrayIndexOutOfBoundsException e){
							e.printStackTrace();
							System.out.println(index);
							
						}
					}
					else {
					   previousPoints[i] = points[i] = null;
					}
				}
		   }
		}
	
	
	
	
	public boolean onTouchEvent(MotionEvent event){
		if(event.getPointerCount()>maxNumberOfTouchPoints)
			return true;
		update(event);
		boolean flag=gestures.onTouchEvent(event);
		if(event.getPointerCount()>1){
			this.multitouchFlag=onMultiTouch(event);
		}
		if(event.getPointerCount()==1 && event.getAction()==MotionEvent.ACTION_UP && multitouchFlag){
			handler.onFling(event, event, 0, 0);
			ismultitouch=false;
		
		}
		return multitouchFlag || flag;
	}
	
	/************************************************************************************************************************************
	 * 
	 * 		Touch handlers that activate the handler methods  
	 * 
	 * 
	 ************************************************************************************************************************************/
	/*************************************************************************************
	 * 
	 *	handlers for single touch only 
	 *************************************************************************************/
	@Override
	public boolean onDoubleTap(MotionEvent event) {
		System.out.println("TouchManager.onDoubleTap()");
		if(!isValidSingleTap(event))
			return true;
		System.out.println("TouchManager.onDoubleTap()");
		return handler.onDoubleTap(event);
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
		if(!isValidSingleTap(event))
			return true;
		
		return handler.onDoubleTapEvent(event);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
		if(!isValidSingleTap(event))
			return true;
		return handler.onSingleTapConfirmed(event);
	}

	@Override
	public void onLongPress(MotionEvent event) {
		if(!isValidSingleTap(event))
			return ;
		handler.onLongPress(event);
		return ;
	}
	
	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		if(!isValidSingleTap(event))
			return true;
		return handler.onSingleTapUp(event);
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		multitouchFlag=false;
		if(!isValidSingleTap(e1) || !isValidSingleTap(e2))
			return true;
		
		return handler.onFling(e1, e2, velocityX, velocityY);
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if(!isValidSingleTap(e1) || !isValidSingleTap(e2))
			return true;
		return handler.onScroll(e1, e2, distanceX, distanceY);
	}
	@Override
	public boolean onDown(MotionEvent event) {
		if(!isValidSingleTap(event))
			return true;
		multitouchFlag=true;
		return handler.onDown(event);
		
	}
	/*************************************************************************************
	 * 
	 *	handlers for multi touch and touch 
	 *************************************************************************************/
	
	private boolean onMultiTouch(MotionEvent event) {
		Point current =null,previous =null;
		synchronized (points) {
			synchronized (previousPoints) {
				for(int i=0;i<maxNumberOfTouchPoints;i++){
					if(points[i]==null||previousPoints[i]==null)
						return true;
				}
				current = getVector(0, 1);
				previous = getPreviousVector(0, 1);
			}
			
		}
		float currentDistance = current.getLength();
		float previousDistance = previous.getLength();
		synchronized (previous) {
			if (previousDistance != 0) {
				scale *= currentDistance / previousDistance;
			}
//			angle -= Point.getSignedAngleBetween(current, previous);
		}
		

		
		if(angleFlag)
			angleFlag=handler.onRotate(event,-Point.getSignedAngleBetween(current, previous));
		if(scaleFlag)
			scaleFlag=handler.onPinch(event,currentDistance, previousDistance, scale);
		
		return angleFlag ||scaleFlag;
	}
	
	/*************************************************************************************
	 * 
	 *	unhandled methods (cause its cannot be trust on multi touch) 
	 *************************************************************************************/

	
	

	@Override
	public void onShowPress(MotionEvent arg0) {
	}
	
	
}
