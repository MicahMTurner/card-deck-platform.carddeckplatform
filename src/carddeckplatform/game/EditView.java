package carddeckplatform.game;

import utils.Point;
import utils.Position;
import utils.Position.Player;
import utils.Public;
import utils.droppableLayouts.DroppableLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import client.gui.entities.Table;
import client.gui.entities.TouchHandler;
import client.gui.entities.TouchManager;
import carddeckplatform.game.R;
import freeplay.customization.CustomizationItem;
import freeplay.customization.CustomizationPlayer;
import freeplay.customization.CustomizationPublic;


public class EditView extends SurfaceView implements SurfaceHolder.Callback,
TouchHandler {

	Table table;
	private carddeckplatform.game.EditView.DrawThread drawThread;
	private TouchManager touchmanager;
	
	public EditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//setBackgroundResource(R.drawable.boardtest);
		
		
				
		table = new Table(context);
		table.setTableImage(R.drawable.boardtest);
		
		touchmanager = new TouchManager(context, this, 2);
		
		table.addDroppable(new CustomizationPublic(null, Position.Public.BOT,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMID,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMIDLEFT,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMIDRIGHT,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.LEFT,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.MID,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.MIDLEFT,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.MIDRIGHT,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.RIGHT,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.TOP,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMID,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMIDLEFT,null , new Point(10,11)));
		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMIDRIGHT,null , new Point(10,11)));
		
		
//		table.addDroppable(new CustomizationPlayer(Player.BOTTOM));
//		table.addDroppable(new CustomizationPlayer(Player.TOP));
//		table.addDroppable(new CustomizationPlayer(Player.LEFT));
		table.addDroppable(new CustomizationPlayer(Player.RIGHT));
		
		setFocusable(true); // necessary for getting the touch events.
		// making the UI thread
		getHolder().addCallback(this);
		
	}
	
	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		touchmanager.onTouchEvent(event);
		return true;
	}
	

	@Override
	public boolean onDoubleTap(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
				return false;
		
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		float x = arg0.getX();
		float y = arg0.getY();
		try {
			((CustomizationItem)table.getNearestDroppable(x, y)).onClick();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return true;
	}

	@Override
	public boolean onRotate(MotionEvent arg0, float angleRadians) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onPinch(MotionEvent arg0, float currentDistance,
			float previousDistance, float scale) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		drawThread = new DrawThread(holder);
		drawThread.setName("drawThread");
		drawThread.setRunning(true);
		drawThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class DrawThread extends Thread {
		private SurfaceHolder surfaceHolder;

		private boolean running = false;

		public void setRunning(boolean value) {
			running = value;
		}

		public DrawThread(SurfaceHolder surfaceHolder) {
			this.surfaceHolder = surfaceHolder;
		}

		@Override
		public void run() {
			Canvas c;
			while (running) {
				try {
					// Don't hog the entire CPU
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				c = null;
				try {

					c = surfaceHolder.lockCanvas(null);
					synchronized (surfaceHolder) {
						// System.out.println(c.getDensity());
						try {
							table.draw(c);// draw it
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					}
				} finally {
					if (c != null) {
						surfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

}
