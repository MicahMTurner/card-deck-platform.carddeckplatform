package carddeckplatform.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import com.google.gson.Gson;

import communication.client.ClientMessageSender;
import communication.entities.Client;
import communication.entities.TcpClient;
import communication.link.Receiver;
import communication.link.Sender;
import communication.link.ServerConnection;
import communication.link.TcpReceiver;
import communication.link.TcpSender;
import communication.messages.CardMotionMessage;
import communication.messages.EndCardMotionMessage;
import communication.messages.Message;
import communication.messages.MessageContainer;
import communication.messages.MessageDictionary;

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
import android.view.View;

public class DrawView extends View implements  Observer {
	
   private Stack<ColorBall> colorballs = new Stack<ColorBall>(); // array that holds the balls
   private int balID = 0; // variable to know what ball is being dragged
   private Context cont; 
   private Handler h = new Handler();
   private ServerConnection serverConnection;
   private Canvas canv;
   private ColorBall ballInHand;
   
   private float scaleFactor=1;
   private float lastX=Integer.MAX_VALUE;
   private float lastY=Integer.MAX_VALUE;
//   private ClientMessageSender clientMessageSender;
   
   
   public void endMotion(int card){
	   try{
		   for(ColorBall cb : colorballs){
			   if(cb.getID()==card){
				   cb.setUncarry();
				   cb.randomizeAngle();
			   }
		   }
	   }
	   catch(Exception e){
		   
	   }
	   invalidate(); 
   }
   
   public void moveCard(String username, int card , int x , int y){
	   System.out.println("moving card " + card + " to (" + x + "," + y + ")");
	   
	   
		   try{
			   for(ColorBall cb : colorballs){
				   if(cb.getID()==card){
					   cb.setX(x);
					   cb.setY(y);
					   
					   cb.setCarry(username);
					   //System.out.println(i);
					   ColorBall tmp = cb;   
					   colorballs.remove(cb);
					   colorballs.push(tmp);
				   }
			   }
		   }
		   catch(Exception e){
			   
		   }
		   
	   
	   
	   
		
		
		
		invalidate(); 
   }
   
  
   
   public DrawView(Context context) {
	    super(context);        
	    
	    
	    
	    serverConnection = new ServerConnection(new TcpClient(GameStatus.localIp , "jojo"), new TcpSender(GameStatus.hostIp , GameStatus.hostPort), this);
	    serverConnection.openConnection();

	    cont = context;
	    setFocusable(true); //necessary for getting the touch events
	    
	    // setting the start point for the balls
	    Point point1 = new Point();
	    point1.x = 300;
	    point1.y = 328;
	    Point point2 = new Point();
	    point2.x = 350;
	    point2.y = 328;
	    Point point3 = new Point();
	    point3.x = 400;
	    point3.y = 328;
	    
	    Point point4 = new Point();
	    point4.x = 450;
	    point4.y = 328;
	    
	                   
	    // declare each ball with the ColorBall class
	    colorballs.add(new ColorBall(context,R.drawable.c8, point1));
	    colorballs.add(new ColorBall(context,R.drawable.d2, point2));
	    colorballs.add(new ColorBall(context,R.drawable.s10, point3));
	    colorballs.add(new ColorBall(context,R.drawable.hj, point4));
	
	    
	}

    // the method that draws the balls
    @Override protected void onDraw(Canvas canvas) {
    	canv = canvas;
    	canv.drawColor(0xFFCCCCCC);     //if you want another background color  
        canv.scale(1, 1);
        
    	//draw the balls on the canvas
    	for (ColorBall ball : colorballs) {
    		ball.draw(canvas);
            
            
            
        }

    }
    
    // events when touching the screen
    public boolean onTouchEvent(MotionEvent event) {
    	try{
    		
    	
    	
    	
    	int tmpX=0;
    	int tmpY=0;
    	
        int eventaction = event.getAction(); 
//        colorballs.add(new ColorBall(cont,R.drawable.bol_geel, p));
        int X = (int)event.getX(); 
        int Y = (int)event.getY(); 
        
        boolean inHand = false;

        switch (eventaction ) { 

        case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
        	lastX = X;
        	lastY = Y;
        	balID = 0;
        	for (int i=colorballs.size()-1 ; i>=0 ; i--) {
        		
        		ColorBall ball = colorballs.get(i);
        		// check if inside the bounds of the ball (circle)
        		// get the center for the ball
        		int centerX = ball.getX() + 25;
        		int centerY = ball.getY() + 25;
        		
        		// calculate the radius from the touch to the center of the ball
        		double radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));
        		
        		// if the radius is smaller then 23 (radius of a ball is 22), then it must be on the ball
        		if (radCircle < 23){
        			balID = ball.getID();
        			ColorBall tmp = ball;
        			colorballs.remove(ball);
        			colorballs.push(tmp);
        			ballInHand = tmp;
        			if(!inHand){
	        			tmpX = colorballs.get(balID-1).getX();
	        			tmpY = colorballs.get(balID-1).getY();
	        			inHand = true;
        			}
        			System.out.println("card poition: " + tmpX + "," + tmpY);
                    break;
        		}

        		// check all the bounds of the ball (square)
        		//if (X > ball.getX() && X < ball.getX()+50 && Y > ball.getY() && Y < ball.getY()+50){
                //	balID = ball.getID();
                //	break;
                //}
        		
        		
              }
             
             break; 


        case MotionEvent.ACTION_MOVE:   // touch drag with the ball
        	// move the balls the same as the finger
        	System.out.println(X + " " + Y);
        	inHand = true;
            if (balID > 0 && balID <= colorballs.size()) {
            	ballInHand.setX(X-25);
            	ballInHand.setY(Y-25);
            	
            	//serverConnection.getMessageSender().cardMotion(ballInHand.getID(), X-25, Y-25);
            	serverConnection.getMessageSender().sendMessage(new CardMotionMessage(GameStatus.username,ballInHand.getID(), X-25, Y-25));
            }
            
            if(balID==0){
            	System.out.println("Canvas sizes: h=" +canv.getHeight() + " w=" + canv.getWidth());
            	if(lastY<=Y && scaleFactor<2.6){
            		float tmp = (float)11/(float)10;
            		scaleFactor*=tmp;
            		System.out.println("UP " + scaleFactor);
            		canv.scale(tmp, tmp , 374, 460);
            		
            	}
            	if(lastY>Y && scaleFactor>1.001){
            		float tmp = (float)10/(float)11;
            		scaleFactor= scaleFactor * tmp;
            		System.out.println("DOWN " + scaleFactor);
            		canv.scale(tmp, tmp , 374, 460);
            	}
            	;
    		}
            lastY=Y;
            break; 

        case MotionEvent.ACTION_UP: 
       		// touch drop - just do things here after dropping
//        	colorballs.get(balID-1).setX(tmpX);
//        	colorballs.get(balID-1).setY(tmpY);
        	lastY=Integer.MAX_VALUE;
        	lastX=Integer.MAX_VALUE;
        	System.out.println("UP!!!");
        	System.out.println(balID-1);
        	if(balID>0){
        		//serverConnection.getMessageSender().cardMotion(ballInHand.getID(), X-25, Y-25);
        		serverConnection.getMessageSender().sendMessage(new CardMotionMessage(GameStatus.username,ballInHand.getID(), X-25, Y-25));
        		serverConnection.getMessageSender().sendMessage(new EndCardMotionMessage(ballInHand.getID()));
        		ballInHand.randomizeAngle();
        	}
//        	if(balID > 0 && balID <= colorballs.size()){
//	        	if(Math.sqrt( (double) (((163-X)*(163-X)) + (228-Y)*(228-Y)))<100){
//	        		System.out.println(Math.sqrt( (double) (((587-X)*(587-X)) + (259-Y)*(259-Y))));
//	            	colorballs.get(balID-1).setX(tmpX);
//	        		colorballs.get(balID-1).setY(tmpY);
//	        	}
//	        	
//	        	
//	        	if(Math.sqrt( (double) (((587-X)*(587-X)) + (259-Y)*(259-Y)))<100){
//	        		System.out.println(Math.sqrt( (double) (((587-X)*(587-X)) + (259-Y)*(259-Y))));
//	            	colorballs.get(balID-1).setX(587);
//	            	colorballs.get(balID-1).setY(259);
//	        	}
//        	}
        	
        	inHand = false;
            break; 
        } 
        // redraw the canvas
        invalidate(); 
        
}catch(Exception ex){
    		System.out.println(ex.getMessage());
    	}
    	return true; 
    }

    // the receiver notifies by activating this method. 
	@Override
	public void update(Observable observabe, Object data) {
		// TODO Auto-generated method stub
		Message message = (Message) data;
		if(message.messageType.equals("CardMotionMessage")){
			CardMotionMessage cmm = (CardMotionMessage)message;
			moveCard(cmm.username , cmm.cardId , cmm.X , cmm.Y);
		}
		if(message.messageType.equals("EndCardMotionMessage")){
			EndCardMotionMessage ecmm = (EndCardMotionMessage)message;
			endMotion(ecmm.cardId);
		}
		else if(message.messageType.equals("Something else...")){
			// do some other thing...
		}
	}
}
