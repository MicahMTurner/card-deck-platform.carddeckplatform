package carddeckplatform.game;

import tutorial.CardHandler;
import tutorial.GuideCard;
import tutorial.PublicHandler;
import utils.Point;
import utils.Position;
import utils.Public;
import utils.droppableLayouts.DroppableLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import client.gui.entities.Table;
import client.gui.entities.TouchHandler;
import client.gui.entities.TouchManager;
import freeplay.customization.CustomizationPublic;

public class TutorialView extends TableView  {

	private DrawThread drawThread;
	Table table;
	private TouchManager touchmanager;
	
	private Context context;
	
	
	
	public void addManySmall(){
//		table.addDroppable(new CustomizationPublic(null, Position.Public.BOT,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMID,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMIDLEFT,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMIDRIGHT,null , new Point(10,11)));
		
		
		Public p = new Public(new PublicHandler(), Position.Public.LEFT,DroppableLayout.LayoutType.HEAP , new Point(10,11));
		
		table.addDroppable(p);
		p.addCard(null, new GuideCard(new CardHandler()));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.MID,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.MIDLEFT,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.MIDRIGHT,null , new Point(10,11)));
		//table.addDroppable(new CustomizationPublic(null, Position.Public.RIGHT,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.TOP,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMID,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMIDLEFT,null , new Point(10,11)));
//		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMIDRIGHT,null , new Point(10,11)));
	}
	
	public void addOneBig(){
		table.addDroppable(new CustomizationPublic(null, Position.Public.MID,null , new Point(65,65)));
	}
	
	
	
	public TutorialView(Context context) {
		super(context, null);
		
		this.context = context;
		
		
		table = new Table(context);
		table.setTableImage(R.drawable.boardtest);
		
		touchmanager = new TouchManager(context, this, 2);	
		setFocusable(true); // necessary for getting the touch events.
		// making the UI thread
		getHolder().addCallback(this);
		addManySmall();
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
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
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {	
	}
	
	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		touchmanager.onTouchEvent(event);
		return true;
	}
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		drawThread = new DrawThread(holder, table);
		drawThread.setName("drawThread");
		drawThread.setRunning(true);
		drawThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.setRunning(false);
		// SurfaceViewThread.running=false;
		try {
			drawThread.join();
		} catch (InterruptedException e) {
		}
	}

	public void setxDimention(int screenWidth) {
		// TODO Auto-generated method stub
		
	}

	public void setyDimention(int screenHeight) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
		table.setDimentions(getMeasuredWidth(), getMeasuredHeight());
	}
	

}
