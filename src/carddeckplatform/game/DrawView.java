package carddeckplatform.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.google.gson.Gson;

import communication.client.ClientMessageSender;
import communication.entities.Client;
import communication.entities.TcpClient;
import communication.link.Receiver;
import communication.link.Sender;
import communication.link.TcpReceiver;
import communication.link.TcpSender;
import communication.messages.CardMotionMessage;
import communication.messages.Message;
import communication.messages.MessageContainer;
import communication.messages.MessageDictionary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View implements  Observer {
   private ArrayList<ColorBall> colorballs = new ArrayList<ColorBall>(); // array that holds the balls
   private int balID = 0; // variable to know what ball is being dragged
   private Context cont; 
   private Handler h = new Handler();
   
   private ClientMessageSender clientMessageSender;
   
   
   public void moveCard(int card , int x , int y){
	   System.out.println("moving card " + card + " to (" + x + "," + y + ")");
		colorballs.get(card).setX(x);
		colorballs.get(card).setY(y);
		
		invalidate(); 
   }
   
   
   private class MoveCardTask extends AsyncTask<Integer, Message, Long> {
	   
	   public BufferedReader in;
	
	   public Message unParseMessage(String str){
			Gson gson = new Gson();
			MessageContainer mc = gson.fromJson(str , MessageContainer.class);
			return MessageDictionary.getMessage(mc.className, mc.classJson);
	   }
	   
		@Override
		protected Long doInBackground(Integer... arg0) {
			while(true){
				try {		
					String stringMessage;
					System.out.println("Message Accepted");
					stringMessage = in.readLine();
					if(stringMessage==null)
						continue;
					System.out.println("the message is: " + stringMessage);
					Message message = unParseMessage(stringMessage);
					// adds the ip address of the sender to the message.
					if(message!=null)
						System.out.println("Message unparsed");
					onProgressUpdate(message);
					System.out.println("Message delivared");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				} 
			}
		}
	
	protected void onProgressUpdate(Message... progress) {
		System.out.println("Entered update");
		Message message = progress[0];
		System.out.println("Got message");
		if(message.messageType.equals("CardMotionMessage")){
			System.out.println("CardMotionMessage");
			final CardMotionMessage cmm = (CardMotionMessage)message;
			System.out.println("Casting completed");
			
			
			h.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					moveCard(cmm.cardId , cmm.X , cmm.Y);
				}
				
			});
			
			System.out.println("finnished");
		}
//		else if(message.messageType.equals("Something else...")){
//			// do some other thing...
//		}
	}
	   
	protected void onPostExecute(Long result) {
		
    }

   }
   
   public DrawView(Context context) {
	    super(context);        
	    
	    
	    
	    
	    
	    Client c = new TcpClient(GameStatus.localIp , "jojo");
	    TcpSender s = new TcpSender(GameStatus.hostIp , 9998);
	    s.openConnection();
	    clientMessageSender = new ClientMessageSender();
	    clientMessageSender.setSender(s, c);
	    
	    MoveCardTask moveCardTask = new MoveCardTask();
	    moveCardTask.in = s.getIn();
	    // gets messages from the host.
//	    Receiver rc = new TcpReceiver(9999);
//	    rc.reg(moveCardTask);
	    moveCardTask.execute(0);
	    
//	    clientMessageSender.clientRegistration();
	    
	    cont = context;
	    setFocusable(true); //necessary for getting the touch events
	    
	    // setting the start point for the balls
	    Point point1 = new Point();
	    point1.x = 50;
	    point1.y = 20;
	    Point point2 = new Point();
	    point2.x = 100;
	    point2.y = 20;
	    Point point3 = new Point();
	    point3.x = 150;
	    point3.y = 20;
	    
	    Point point4 = new Point();
	    point4.x = 200;
	    point4.y = 350;
	    
	                   
	    // declare each ball with the ColorBall class
	    colorballs.add(new ColorBall(context,R.drawable.c8, point1));
	    colorballs.add(new ColorBall(context,R.drawable.d2, point2));
	    colorballs.add(new ColorBall(context,R.drawable.s10, point3));
	    colorballs.add(new ColorBall(context,R.drawable.hj, point4));
	
	    
	}

    // the method that draws the balls
    @Override protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFCCCCCC);     //if you want another background color       
        
    	//draw the balls on the canvas
    	for (ColorBall ball : colorballs) {
            canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), null);
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
        	balID = 0;
        	for (ColorBall ball : colorballs) {
        		// check if inside the bounds of the ball (circle)
        		// get the center for the ball
        		int centerX = ball.getX() + 25;
        		int centerY = ball.getY() + 25;
        		
        		// calculate the radius from the touch to the center of the ball
        		double radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));
        		
        		// if the radius is smaller then 23 (radius of a ball is 22), then it must be on the ball
        		if (radCircle < 23){
        			balID = ball.getID();
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
            	colorballs.get(balID-1).setX(X-25);
            	colorballs.get(balID-1).setY(Y-25);
            	
            	clientMessageSender.cardMotion(balID-1, X-25, Y-25);
            }
        	
            break; 

        case MotionEvent.ACTION_UP: 
       		// touch drop - just do things here after dropping
//        	colorballs.get(balID-1).setX(tmpX);
//        	colorballs.get(balID-1).setY(tmpY);
        	System.out.println("UP!!!");
        	System.out.println(balID-1);
        	if(balID>0)
        		clientMessageSender.cardMotion(balID-1, X-25, Y-25);
        	
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
			moveCard(cmm.cardId , cmm.X , cmm.Y);
		}
		else if(message.messageType.equals("Something else...")){
			// do some other thing...
		}
	}
}
