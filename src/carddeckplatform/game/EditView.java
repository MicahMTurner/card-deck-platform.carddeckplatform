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
import utils.droppableLayouts.DroppableLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import client.gui.entities.Droppable;
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

	public enum Mode{ONE_BIG, MANY_SMALL}
	
	Mode mode = Mode.ONE_BIG;
	
	private carddeckplatform.game.EditView.DrawThread drawThread;
	Table table;
	private TouchManager touchmanager;
	
	private Context context;
	
	private CustomizationPlayer cp1 = new CustomizationPlayer(Position.Player.BOTTOM);
	private CustomizationPlayer cp2 = new CustomizationPlayer(Position.Player.TOP);
	private CustomizationPlayer cp3 = new CustomizationPlayer(Position.Player.LEFT);
	private CustomizationPlayer cp4 = new CustomizationPlayer(Position.Player.RIGHT);
	
	private CustomizationDeck cd = new CustomizationDeck(Position.Button.TOPRIGHT);
	
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
	}
	
	public void addOneBig(){
		table.addDroppable(new CustomizationPublic(null, Position.Public.MID,null , new Point(65,65)));
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
		return profile;
	}

	public void setProfile(FreePlayProfile profile){
		table.clearDroppables();
		for(Droppable d : profile.getDroppables()){
			table.addDroppable(d);
		}
		
		mode=profile.getMode();
	}
	
}
