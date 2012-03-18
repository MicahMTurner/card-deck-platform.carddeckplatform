package carddeckplatform.game;

import java.util.Observable;
import java.util.Observer;

import communication.entities.TcpClient;
import communication.link.ServerConnection;
import communication.link.TcpSender;
import communication.messages.Message;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import carddeckplatform.game.GameStatus;
import client.gui.entities.Card;
import client.gui.entities.Draggable;
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
import carddeckplatform.game.R;

public class TableView extends View  implements Observer {
	private ServerConnection serverConnection;
	private Table table;
	private Context cont; 
	private Canvas canv;
	private Draggable draggableInHand=null;
	private int xDimention;
	private int yDimention;
	
	
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
	
	public TableView(Context context, int xDimention, int yDimention) {
		super(context);
		this.xDimention = xDimention;
		this.yDimention = yDimention;
		// TODO Auto-generated constructor stub
		cont = context;
		table = new Table(context, xDimention, yDimention);
		table.setTableImage(R.drawable.table);
		// connects with the server.
		serverConnection = new ServerConnection(new TcpClient(GameStatus.localIp , "jojo"), new TcpSender(GameStatus.hostIp , GameStatus.hostPort), this);
	    serverConnection.openConnection();
		
	    table.addDraggable(new Card(context,R.drawable.ca,50,50,serverConnection));
	    setFocusable(true); //necessary for getting the touch events.
	    
	    
	}
	
	// the method that draws the balls
    @Override protected void onDraw(Canvas canvas) {
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
	    				//table.getNearestDroppable(X, Y).onDrop(draggableInHand);
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
	
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Message message = (Message) arg1;
		message.clientAction(this);
	}

}
