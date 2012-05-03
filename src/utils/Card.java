package utils;


import handlers.CardEventsHandler;
import IDmaker.IDMaker;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import carddeckplatform.game.R;
import client.gui.entities.Draggable;

public class Card extends Draggable{
	public enum CardColor{
		HEART,DIAMOND,SPADE,CLUB
	}
	private CardEventsHandler eventsHandler;
	private Bitmap frontImg;
	private boolean revealed;	
	private String owner;
	private int value;
	private CardColor color;

	private boolean isCarried=false;
	private String carrier = "";
	
	public Card(int value,CardColor color,CardEventsHandler handler) {		
		super(null,IDMaker.getMaker().getId());
		this.eventsHandler=handler;		
		this.value=value;
		this.revealed=false;		
		this.color=color;
		
	}
	public CardColor getColor() {
		return color;
	}
	@Override
	public int sensitivityRadius() {
		
		return 30;
	}
	public void reveal(){
		this.revealed = true;
		eventsHandler.onReveal(this);
	}
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}
	public void hide(){
		this.revealed = false;
		eventsHandler.OnHide(this);
	}
	public boolean isRevealed() {
		return revealed;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public int getValue() {
		return value;
	}
	public void setCardImage(Bitmap img) {
		this.frontImg = img;
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
		Bitmap resizedBitmap=null;
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		if(revealed)
			resizedBitmap = Bitmap.createBitmap(frontImg, 0, 0, frontImg.getScaledWidth(canvas) , frontImg.getScaledHeight(canvas), matrix, true);
		else
			resizedBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(super.getResources(), R.drawable.back), 0, 0, frontImg.getScaledWidth(canvas) , frontImg.getScaledHeight(canvas), matrix, true);
		canvas.drawBitmap(resizedBitmap, getX()-25, getY()-20, new Paint());
		
		        
		
		
		// if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
        if(isCarried){
        	Paint paint = new Paint(); 		   
        	// draws the name of the carrier.
            paint.setColor(Color.BLACK); 
            paint.setTextSize(20); 
            canvas.drawText(carrier,getX()-25, getY()-20, paint);
            // draws the hand.
            canvas.drawBitmap(BitmapFactory.decodeResource(super.getResources(), R.drawable.hand),getX()-30, getY()+20 , paint);
        }        
       
	}
	

}
