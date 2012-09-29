package carddeckplatform.game;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import utils.Point;
import utils.Position;
import utils.Position.Player;
import utils.Public;
import utils.StandardSizes;
import utils.droppableLayouts.DroppableLayout;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;
import client.gui.entities.Table;
import client.gui.entities.TouchHandler;
import client.gui.entities.TouchManager;
import carddeckplatform.game.R;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import freeplay.customization.CustomizationDeck;
import freeplay.customization.CustomizationItem;
import freeplay.customization.CustomizationPlayer;
import freeplay.customization.CustomizationPublic;
import freeplay.customization.FreePlayProfile;
import freeplay.customization.ToggleModeButton;


public class EditView extends SurfaceView implements SurfaceHolder.Callback,
TouchHandler {
	private boolean showHelp=false;
	public enum Mode{ONE_BIG, MANY_SMALL}
	
	Mode mode = Mode.ONE_BIG;
	
	private DrawThread drawThread;
	Table table;
	private TouchManager touchmanager;
	
	private Context context;
	
	private CustomizationPlayer cp1 = new CustomizationPlayer(Position.Player.BOTTOM);
	private CustomizationPlayer cp2 = new CustomizationPlayer(Position.Player.TOP);
	private CustomizationPlayer cp3 = new CustomizationPlayer(Position.Player.LEFT);
	private CustomizationPlayer cp4 = new CustomizationPlayer(Position.Player.RIGHT);
	
	private CustomizationDeck cd = new CustomizationDeck(Position.Button.TOPRIGHT);
	
	private int cardsToDeal=0;
	
	public void setCardsToDeal(int cardsToDeal) {
		this.cardsToDeal = cardsToDeal;
	}
	
	public int getCardsToDeal() {
		return cardsToDeal;
	}
	
	public void toggleHelp(){
		showHelp = !showHelp;
	}
	
	public EditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//setBackgroundResource(R.drawable.boardtest);
		
		this.context = context;
				
		table = new Table(context);
		table.setTableImage(R.drawable.boardtest);
		
		touchmanager = new TouchManager(context, this, 2);	
		
		table.addButon(new ToggleModeButton(Position.Button.TOPLEFT, this));
		
		addOneBig();
		addPlayers();
		addDeck();
		setFocusable(true); // necessary for getting the touch events.
		// making the UI thread
		getHolder().addCallback(this);
		
		//loadProfile("profile1");
		
	}
	
	public void toggleMode(){
		table.clearDroppables();
		addPlayers();		
		addDeck();
		if(mode == Mode.ONE_BIG){
			mode = Mode.MANY_SMALL;
			addManySmall();
		}else{
			mode = Mode.ONE_BIG;
			addOneBig();
		}		
	}
	
	public void addDeck(){
		table.addDroppable(cd);
	}
	
	public void addPlayers(){
		table.addDroppable(cp1);
		table.addDroppable(cp2);
		table.addDroppable(cp3);
		table.addDroppable(cp4);
	}
	
	public void addManySmall(){
		table.addDroppable(new CustomizationPublic(null, Position.Public.BOT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMID,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMIDLEFT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.BOTMIDRIGHT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.LEFT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.MID,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.MIDLEFT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.MIDRIGHT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.RIGHT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.TOP,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMID,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMIDLEFT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
		table.addDroppable(new CustomizationPublic(null, Position.Public.TOPMIDRIGHT,DroppableLayout.LayoutType.HEAP , StandardSizes.HEAP_AREA));
	}
	
	public void addOneBig(){
		table.addDroppable(new CustomizationPublic(null, Position.Public.MID,DroppableLayout.LayoutType.NONE , new Point(65,65)));
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
		
		try {
			table.getNearestButton(x, y).onClick();
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
		drawThread = new EditDrawThread(holder, table);
		drawThread.setName("drawThread");
		drawThread.setRunning(true);
		drawThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	public void saveProfile(String profileName){
		FreePlayProfile.saveProfile(profileName, createProfile());
	}
	
	public void loadProfile(String profileName){
		table.clearDroppables();
		setProfile(FreePlayProfile.loadProfile(profileName));
		
	}
	
	private FreePlayProfile createProfile(){
		FreePlayProfile profile = new FreePlayProfile();
		
		profile.setDroppables(table.getDroppables());
		profile.setMode(mode);
		profile.setProfileName("profile1");
		profile.setCardsToDeal(cardsToDeal);
		return profile;
	}

	public void setProfile(FreePlayProfile profile){
		table.clearDroppables();
		for(Droppable d : profile.getDroppables()){
			table.addDroppable(d);
		}
		
		mode=profile.getMode();
		cardsToDeal = profile.getCardsToDeal();
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
		table.setDimentions(getMeasuredWidth(), getMeasuredHeight());
	}
	
	
	private class EditDrawThread extends DrawThread {
		public void setRunning(boolean value) {
			running = value;
		}

		public EditDrawThread(SurfaceHolder surfaceHolder, Table table) {
			super(surfaceHolder, table);
		}
		
		
		private void drawPlayerHelp(Canvas c){
			Bitmap playerHelp=BitmapHolder.get().getBitmap("playerhelp");
			Matrix matrix = new Matrix();
			
			
			Point p= new Point(70,30);
			Point scale = new Point(40,30);
			
			scale = MetricsConvertion.pointRelativeToPx(scale);
			p = MetricsConvertion.pointRelativeToPx(p);
			
			
			float x=MetricsConvertion.pointRelativeToPx(p).getX();
			float y=MetricsConvertion.pointRelativeToPx(p).getY();
			
			
			
			// transformations.		
			//matrix.postScale((float)p.getX()/(float)buttonBitmap.getWidth(), ((float)p.getX() * ratio)/(float)buttonBitmap.getHeight());
			float ratio = (float)playerHelp.getHeight() / (float)playerHelp.getWidth();
			matrix.postScale((float)scale.getX()/(float)playerHelp.getWidth(), ((float)scale.getX() * ratio)/(float)playerHelp.getHeight());
			matrix.postTranslate(p.getX()-scale.getX()/2, p.getY()-scale.getY()/2);
			
			
			
			c.drawBitmap(playerHelp, matrix, null);
		}
		
		
		
		private void drawPublicHelp(Canvas c){
			Bitmap publicHelp=BitmapHolder.get().getBitmap("publichelp");
			Matrix matrix = new Matrix();
			
			
			Point p= new Point(30,60);
			Point scale = new Point(40,30);
			
			scale = MetricsConvertion.pointRelativeToPx(scale);
			p = MetricsConvertion.pointRelativeToPx(p);
			
			
			float x=MetricsConvertion.pointRelativeToPx(p).getX();
			float y=MetricsConvertion.pointRelativeToPx(p).getY();
			
			
			
			// transformations.		
			//matrix.postScale((float)p.getX()/(float)buttonBitmap.getWidth(), ((float)p.getX() * ratio)/(float)buttonBitmap.getHeight());
			float ratio = (float)publicHelp.getHeight() / (float)publicHelp.getWidth();
			matrix.postScale((float)scale.getX()/(float)publicHelp.getWidth(), ((float)scale.getX() * ratio)/(float)publicHelp.getHeight());
			matrix.postTranslate(p.getX()-scale.getX()/2, p.getY()-scale.getY()/2);
			
			
			
			c.drawBitmap(publicHelp, matrix, null);
		}
		
		
		private void drawButtonHelp(Canvas c){
			Bitmap publicHelp=BitmapHolder.get().getBitmap("buttonhelp");
			Matrix matrix = new Matrix();
			
			
			Point p= new Point(30,10);
			Point scale = new Point(40,30);
			
			scale = MetricsConvertion.pointRelativeToPx(scale);
			p = MetricsConvertion.pointRelativeToPx(p);
			
			
			float x=MetricsConvertion.pointRelativeToPx(p).getX();
			float y=MetricsConvertion.pointRelativeToPx(p).getY();
			
			
			
			// transformations.		
			//matrix.postScale((float)p.getX()/(float)buttonBitmap.getWidth(), ((float)p.getX() * ratio)/(float)buttonBitmap.getHeight());
			float ratio = (float)publicHelp.getHeight() / (float)publicHelp.getWidth();
			matrix.postScale((float)scale.getX()/(float)publicHelp.getWidth(), ((float)scale.getX() * ratio)/(float)publicHelp.getHeight());
			matrix.postTranslate(p.getX()-scale.getX()/2, p.getY()-scale.getY()/2);
			
			
			
			c.drawBitmap(publicHelp, matrix, null);
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
							
							if(showHelp){
								drawPlayerHelp(c);
								drawPublicHelp(c);
								drawButtonHelp(c);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
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
