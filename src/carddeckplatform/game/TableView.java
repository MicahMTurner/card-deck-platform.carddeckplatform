package carddeckplatform.game;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import war.War;

import logic.card.CardLogic;
import logic.client.Game;

import communication.entities.TcpClient;
import communication.link.ServerConnection;
import communication.link.TcpReceiver;
import communication.link.TcpSender;
import communication.messages.Message;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import carddeckplatform.game.GameStatus;
//import logic.client.Game;
import client.gui.entities.Card;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import client.gui.entities.Table;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;
import carddeckplatform.game.R;

public class TableView extends SurfaceView {
	private ServerConnection serverConnection;
	private Table table;
	private Context cont; 
	private Canvas canv;
	private Draggable draggableInHand=null;
	private int xDimention;
	private int yDimention;
	private AnimationTask animationTask;
	
	
	
	//private Game game;
//	private Logic logic;
	
//	public Logic getLogic(){
		
//	}
	
//	public Game getGame(){
//		return game;
//	}
	
	
	public void draggableMotion(String username, int id , int x , int y){
		Draggable draggable = table.getDraggableById(id, true);
		draggable.motionAnimation(username);
		draggable.setLocation(xDimention-x, yDimention-y);
		//invalidate(); 
		
		animationTask.redraw();
	}
	
	public void endDraggableMotion(int id){
		Draggable draggable = table.getDraggableById(id, true);
		draggable.clearAnimation();
		//invalidate();
		
		animationTask.redraw();
	}
	
//	private class CardAnnimation extends AsyncTask<Integer, Point, Long>{
//		private Draggable draggable;
//		private int newX;
//		private int newY;
//		
//		public CardAnnimation(final Draggable draggable, final int newX, final int newY){
//			this.draggable = draggable;
//			this.newX = newX;
//			this.newY = newY;
//			
//		}
//		
//		@Override
//		protected Long doInBackground(Integer... arg) {
//			// TODO Auto-generated method stub
//			final ArrayList<Point> vector = StaticFunctions.midLine(draggable.getX(), draggable.getY(), newX, newY);
//			for(int i=0; i<vector.size(); i++){
//				if(i%3==0)
//					onProgressUpdate(vector.get(i));
//			}
//			
//			
//			return null;
//		}
//		
//		protected void onProgressUpdate(Point... point) {
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			draggable.setLocation(point[0].x, point[0].y);
//			CarddeckplatformActivity.h.post(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					invalidate();
//				} 
//				
//			});
//			
//		}
//		
//	}
	
	public void moveDraggable(final Draggable draggable, final int newX, final int newY){
		
		//new CardAnnimation(draggable, newX, newY).execute(0);
		
		
//		final int x = draggable.getX();
//		final int y = draggable.getY();
//		TcpReceiver.h.post(new Runnable() { 
//            public void run() { 
//            	final ArrayList<Point> vector = StaticFunctions.midLine(x, y, newX, newY);
//		
//            	for(int i=0; i<vector.size(); i++){
//            		final int index = i;
//			
//					try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//			
//			
//                	draggable.setLocation(vector.get(index).x, vector.get(index).y);
//                	invalidate();
//            } }});
			
		
		
//		Animation mAnimationTranslate = new TranslateAnimation(x, newX, y, newY);
//	    mAnimationTranslate.setDuration(5000);
//	    mAnimationTranslate.setFillAfter(true);
//	    
//		draggable.startAnimation(mAnimationTranslate);
//		//this.startAnimation(mAnimationTranslate);
//		draggable.setLocation(newX, newY);
//		invalidate();
		
		
		
		//while
	}
	
	
	
	private class AnimationTask extends AsyncTask<Integer, Integer, Long>{
		private BlockingQueue<String> calls = new ArrayBlockingQueue<String>(1000);
		
		
		public void redraw(){
			calls.add("xyz");
		}
		
		public void stopDrawing(){
			calls.clear();
		}
		
		@Override
		protected Long doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			
			while(true){
				try {
					System.out.println("ANIMATION: before take.");
					calls.take();
					onProgressUpdate(0);
					System.out.println("ANIMATION: after take.");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		@Override
		protected void onProgressUpdate(Integer... Integ) {
			CarddeckplatformActivity.h.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("ANIMATION: before invalidate.");
					invalidate();
					System.out.println("ANIMATION: after invalidate.");
				}
			});
		}
		
	}
	
	
	
	public void moveDraggable(Draggable draggable, Droppable droppable){
		moveDraggable(draggable, droppable.getX(), droppable.getY());
	}
	
	public void addDroppable(Droppable droppable){
		table.addDroppable(droppable);
	}
	
	public void addDraggable(ArrayList<CardLogic> cardLogics, Droppable target){
		animationTask.stopDrawing();
		// get the first letter of the type and concatenates it with the value.
		
		for(CardLogic cardLogic : cardLogics){
			String key = cardLogic.getType().subSequence(0, 1) + String.valueOf(cardLogic.getValue());
			System.out.println("the key is: " + key);
			int resourceId = getResources().getIdentifier("drawable/" + key, "drawable", "carddeckplatform.game");		
			Card card = new Card(getContext(),resourceId,0,0); 
			card.setCardLogic(cardLogic);
			table.addDraggable(card);
			target.addDraggable(card);
		}
		//invalidate();
		
		animationTask.redraw();
	}
	
	public void moveFromTo(Droppable from, Droppable to){
		CardLogic cardLogic = from.getDraggable();
		int cardId = cardLogic.getId();
		Draggable draggable = table.getDraggableById(cardId, true);	// correct this. shouldn't be at top, should be at bottom.
		
		// TODO apply some animation on the draggable.
		
		to.addDraggable(draggable);
		from.removeDraggable(draggable);
	}
	
	public Droppable getDroppableById(int id){
		return table.getDroppableById(id);
	}
	
	public TableView(Context context,AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
		animationTask = new AnimationTask();
		animationTask.execute(0);
		//init();
		this.xDimention =  getMeasuredWidth();
		this.yDimention = getMeasuredHeight();
		// TODO Auto-generated constructor stub
		cont = context;
		
		
		table = new Table(context);
		table.setTableImage(R.drawable.boardtest);
		// connects with the server.
//		serverConnection = new ServerConnection(new TcpClient(GameStatus.localIp , "jojo"), new TcpSender(GameStatus.hostIp , GameStatus.hostPort), this);
//	    serverConnection.openConnection();
		char types[] = {'h', 's', 'd', 'c'};
		
//		for(int i=0; i<4; i++){
//			for(int j=2;j<=2; j++){
//				String key = String.valueOf(types[i]) + String.valueOf(j);
//				table.addDraggable(new Card(context,getResources().getIdentifier("drawable/" + key, "drawable", "carddeckplatform.game"),50,50));
//			}
//		}
		
		
//	    table.addDraggable(new Card(context,getResources().getIdentifier("drawable/c14", "drawable", "carddeckplatform.game"),50,50,serverConnection));
	    //table.addDraggable(new Card(context,getResources().getIdentifier("drawable/h14", "drawable", "carddeckplatform.game"),60,60,serverConnection));
	    setFocusable(true); //necessary for getting the touch events.
	}
	
//	public TableView(Context context, int xDimention, int yDimention) {
//		super(context);
//		this.xDimention = getMeasuredWidth();
//		this.yDimention = getMeasuredHeight();
//		// TODO Auto-generated constructor stub
//		cont = context;
//		table = new Table(context, xDimention, yDimention);
//		table.setTableImage(R.drawable.table);
//		// connects with the server.
//		serverConnection = new ServerConnection(new TcpClient(GameStatus.localIp , "jojo"), new TcpSender(GameStatus.hostIp , GameStatus.hostPort), this);
//	    serverConnection.openConnection();
//		
//	    table.addDraggable(new Card(context,R.drawable.ca,50,50,serverConnection));
//	    setFocusable(true); //necessary for getting the touch events.
//	    
//	    
//	}
	@Override
	protected void onMeasure(int widthMeasureSpec,
	     int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
	    table.setxDimention(getMeasuredWidth());
	    table.setyDimention(getMeasuredHeight());
	}
	// the method that draws the balls
    @Override
    protected void onDraw(Canvas canvas) {
    	canv = canvas;
    	canv.drawColor(Color.TRANSPARENT);
    	super.onDraw(canvas);//if you want another background color  
        canv.scale(1, 1);
        // draws the table.
        table.draw(canvas);
    }

 // events when touching the screen
    public boolean onTouchEvent(MotionEvent event) {
    	try {
    		int X = (int)event.getX(); 
            int Y = (int)event.getY(); 
    		int eventaction = event.getAction();
    		switch (eventaction ) { 
	    		case MotionEvent.ACTION_DOWN:
	    			draggableInHand = table.getNearestDraggable(X, Y, true);
//	    			if(draggableInHand==null)
//	    				table.getNearestDroppable(X, Y).onClick();
//	    			else
//	    				draggableInHand.onClick();
	    			if(draggableInHand!=null)
	    				draggableInHand.onClick();
	    			
	    			//moveDraggable(table.getDraggableById(1, true),X, Y);

	    			
	    			break;
	    		case MotionEvent.ACTION_MOVE:
	    			if(draggableInHand!=null){
	    				draggableInHand.setLocation(X, Y);
	    				draggableInHand.onDrag();
	    				//table.getNearestDroppable(X, Y).onHover();
	    			}
	    			break;
	    		case MotionEvent.ACTION_UP:
	    			if(draggableInHand!=null){
	    				draggableInHand.onRelease();
	    				try {
							table.getNearestDroppable(X, Y).onDrop(draggableInHand);
						} catch (Exception e) {
							// TODO: handle exception
						}
	    				
	    				draggableInHand = null;
	    			}
	    			break;
    		}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
    	//invalidate();
    	animationTask.redraw();
		return true;
    }

	public int getxDimention() {
		return xDimention;
	}

	public void setxDimention(int xDimention) {
		this.xDimention = xDimention;
	}

	public int getyDimention() {
		return yDimention;
	}

	public void setyDimention(int yDimention) {
		this.yDimention = yDimention;
	}

	
	
	
	
	
	
	
//	private SurfaceHolder holder;
//    private SurfaceViewThread surfaceViewThread;
//    private boolean hasSurface;
//	
//	
//	
//	@Override
//	public void surfaceChanged(SurfaceHolder arg0, int arg1, int w, int h) {
//		// TODO Auto-generated method stub
////		if (surfaceViewThread != null)
////            surfaceViewThread.onWindowResize(w, h);
//	}
//	
//	private void init() {
//        // Create a new SurfaceHolder and assign this class as its callback
//        holder = getHolder();
//        holder.addCallback(this);
//        hasSurface = false;
//    }
//		
//	@Override
//	public void surfaceCreated(SurfaceHolder arg0) {
//		// TODO Auto-generated method stub
//		hasSurface = true;
//
//        if (surfaceViewThread != null)
//            surfaceViewThread.start();
//	}
//
//	@Override
//	public void surfaceDestroyed(SurfaceHolder arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//	private final class SurfaceViewThread extends Thread {
//		@Override
//        public void run() {
//			SurfaceHolder surfaceHolder = holder;
//			while (true) {
//				Canvas c = surfaceHolder.lockCanvas();
//				invalidate();
//				surfaceHolder.unlockCanvasAndPost(c);
//			}
//		}
//	}
	
}
