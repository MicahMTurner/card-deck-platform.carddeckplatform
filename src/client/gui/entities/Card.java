package client.gui.entities;

import java.util.Random;

import logic.card.CardLogic;

import client.controller.ClientController;

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
	private int coordX = 0; // the x coordinate at the canvas
	private int coordY = 0; // the y coordinate at the canvas
	private int id; // gives every ball his own id, for now not necessary
	private static int count = 1;
	private boolean goRight = true;
	private boolean goDown = true;
	private float angle = 0;
	private boolean isCarried=false;
	private String carrier = "";
	private Context context;

	public Card(Context context, int drawable, int x, int y){
		super(context);
		this.context = context;
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        img = BitmapFactory.decodeResource(context.getResources(), drawable); 
        id=count;
		count++;
		coordX= x;
		coordY = y;
	}
	
	public Card(Context context, int drawable, Droppable dropable){
		super(context);
	}
	
	public void randomizeAngle(){
		Random generator = new Random();
		float randomIndex = generator.nextInt(20);
		randomIndex -= 10;
		angle = randomIndex;
	}
	
	public void setAngle(float angle){
		this.angle = angle;
	}
	
	public int sensitivityRadius() {
		// TODO Auto-generated method stub
		return 30;
	}


	public void onClick() {
		// TODO Auto-generated method stub
	}


	public void onDrag() {
		// TODO Auto-generated method stub
		//serverConnection.getMessageSender().sendMessage(new CardMotionMessage(GameStatus.username,getId(), coordX, coordY));
		ClientController.outgoingAPI().cardMotion(GameStatus.username,getId(), coordX, coordY);
	}


	public void onRelease() {
		// TODO Auto-generated method stub
		ClientController.outgoingAPI().cardMotion(GameStatus.username,getId(), coordX, coordY);
		ClientController.outgoingAPI().endCardMotion(getId());
		randomizeAngle();
	}


	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return coordX;
	}


	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return coordY;
	}

	@Override
    protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		Bitmap resizedBitmap = Bitmap.createBitmap(img, 0, 0, img.getScaledWidth(canvas) , img.getScaledHeight(canvas), matrix, true);
        canvas.drawBitmap(resizedBitmap, getX()-25, getY()-20, new Paint());
        
        // if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
        if(isCarried){
        	Paint paint = new Paint(); 		   
        	// draws the name of the carrier.
            paint.setColor(Color.BLACK); 
            paint.setTextSize(20); 
            canvas.drawText(carrier,getX(), getY(), paint);
            // draws the hand.
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hand),getX()-25, getY()+20 , paint);
        }
        
        	
	}


	@Override
	public void setLocation(int x, int y) {
		// TODO Auto-generated method stub
		coordX = x;
		coordY = y;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void motionAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void motionAnimation(String str) {
		// TODO Auto-generated method stub
		isCarried = true;
		carrier = str;
	}

	@Override
	public void clearAnimation() {
		// TODO Auto-generated method stub
		isCarried = false;
		carrier = "";
	}

}
