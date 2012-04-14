package client.gui.entities;

import java.util.Random;

import logic.card.CardLogic;

import client.controller.ClientController;
import client.controller.actions.DraggableMotionAction;
import client.controller.actions.EndDraggableMotionAction;

import communication.link.ServerConnection;

import carddeckplatform.game.GameStatus;
import carddeckplatform.game.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Card extends Draggable {
	private Bitmap img; // the image of the ball
	//private int id; // gives every ball his own id, for now not necessary
	//private static int count = 1;
	private boolean goRight = true;
	private boolean goDown = true;
	private boolean isCarried=false;
	private String carrier = "";
	private Context context;

	public Card(Context context, int drawable, int x, int y){
		super(context, x, y);
		this.context = context;
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        img = BitmapFactory.decodeResource(context.getResources(), drawable); 
        //id=count;
		//count++;
	}
	
	//public Card(Context context, int drawable, Droppable dropable){
	//	super(context);
	//}
	
	public int sensitivityRadius() {

		return 30;
	}


	public void onClick() {		
		// save the current location in order to be able to return to this position if needed.
		prevX = x;
		prevY = y;
	}


	public void onDrag() {
		//serverConnection.getMessageSender().sendMessage(new CardMotionMessage(GameStatus.username,getId(), coordX, coordY));
		ClientController.outgoingAPI().outgoingCommand(new DraggableMotionAction(GameStatus.username,cardLogic.getId(), x, y));
	}


	public void onRelease() {
		//ClientController.outgoingAPI().cardMotion(GameStatus.username,getId(), coordX, coordY);
		//ClientController.outgoingAPI().endCardMotion(getId());
		ClientController.outgoingAPI().outgoingCommand(new DraggableMotionAction(GameStatus.username,cardLogic.getId(), x, y));
		ClientController.outgoingAPI().outgoingCommand(new EndDraggableMotionAction(cardLogic.getId()));
		//randomizeAngle();
	}

	
	@Override
	public void undoMove() {
		x=prevX;
		y=prevY;
		ClientController.outgoingAPI().outgoingCommand(new DraggableMotionAction(GameStatus.username,cardLogic.getId(), x, y));
		ClientController.outgoingAPI().outgoingCommand(new EndDraggableMotionAction(cardLogic.getId()));
		angle=0;
		
	}
	


	@Override
    protected void onDraw(Canvas canvas) {
		Bitmap resizedBitmap=null;
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		if(cardLogic.isRevealed())
			resizedBitmap = Bitmap.createBitmap(img, 0, 0, img.getScaledWidth(canvas) , img.getScaledHeight(canvas), matrix, true);
		else
			resizedBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.back), 0, 0, img.getScaledWidth(canvas) , img.getScaledHeight(canvas), matrix, true);
		canvas.drawBitmap(resizedBitmap, getX()-25, getY()-20, new Paint());
        
        // if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
        if(isCarried){
        	Paint paint = new Paint(); 		   
        	// draws the name of the carrier.
            paint.setColor(Color.BLACK); 
            paint.setTextSize(20); 
            canvas.drawText(carrier,getX()-25, getY()-20, paint);
            // draws the hand.
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hand),getX()-30, getY()+20 , paint);
        }
        
        	
	}


	@Override
	public int getId() {

		return cardLogic.getId();
	}

	@Override
	public void motionAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void motionAnimation(String str) {

		isCarried = true;
		carrier = str;
	}

	@Override
	public void clearAnimation() {

		isCarried = false;
		carrier = "";
	}

	

}
