package carddeckplatform.game;

import java.util.Observable;
import java.util.Observer;

import communication.entities.TcpClient;
import communication.link.ServerConnection;
import communication.link.TcpSender;
import communication.messages.Message;
import communication.messages.PlayerInfoMessage;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
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

public class TableView extends View {
	private ServerConnection serverConnection;
	private Table table;
	private Context cont; 
	private Canvas canv;
	private Draggable draggableInHand=null;
	private int xDimention;
	private int yDimention;
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
		draggable.setLocation(x, y);
		invalidate(); 
	}
	
	public void endDraggableMotion(int id){
		Draggable draggable = table.getDraggableById(id, true);
		draggable.clearAnimation();
		invalidate(); 
	}
	
	public void moveDraggable(int id, int newX, int newY){
		Draggable draggable = table.getDraggableById(id, true);
		int x = draggable.getX();
		int y = draggable.getY();
		
		Animation mAnimationTranslate = new TranslateAnimation(x, newX, y, newY);
	    mAnimationTranslate.setDuration(5000);
	    mAnimationTranslate.setFillAfter(true);
	    
		draggable.startAnimation(mAnimationTranslate);
		draggable.setLocation(newX, newY);
		invalidate(); 
		//while
	}
	
	public void addDroppable(Droppable droppable){
		table.addDroppable(droppable);
	}
	
	
	public void sendInfo(){
		serverConnection.getMessageSender().sendMessage(new PlayerInfoMessage(GameStatus.username));
	}
	
	public TableView(Context context,AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
		
		this.xDimention =  getMeasuredWidth();
		this.yDimention = getMeasuredHeight();
		// TODO Auto-generated constructor stub
		cont = context;
		table = new Table(context);
		table.setTableImage(R.drawable.boardtest);
		// connects with the server.
//		serverConnection = new ServerConnection(new TcpClient(GameStatus.localIp , "jojo"), new TcpSender(GameStatus.hostIp , GameStatus.hostPort), this);
//	    serverConnection.openConnection();
		
	    table.addDraggable(new Card(context,R.drawable.ca,50,50,serverConnection));
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
    	canv.drawColor(0xFFCCCCCC);     //if you want another background color  
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
    	invalidate();
    	
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
	
}
