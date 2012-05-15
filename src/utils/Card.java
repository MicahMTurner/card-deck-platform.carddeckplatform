package utils;


import java.io.Serializable;

import handlers.CardEventsHandler;
import IDmaker.IDMaker;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import carddeckplatform.game.R;
import client.gui.entities.Draggable;

public abstract class Card implements Serializable,Comparable<Card>{
	
	private int id;
	private CardEventsHandler eventsHandler;
	private final String frontImg;
	private final String backImg;
	private boolean revealed;	
	private Position.Player owner;
	private Point coord;

	private boolean isCarried=false;
	private String carrier = "";
	
	public Card(CardEventsHandler handler,String frontImg,String backImg) {		
		this.id=IDMaker.getMaker().getId();
		this.eventsHandler=handler;		
		this.backImg=backImg;
		this.frontImg= frontImg;
		this.revealed=false;		
		this.coord=new Point(0,0);
	}
	public Point getCoord() {
		return coord;
	}
	public void setCoord(int x,int y) {
		coord.setX(x);
		coord.setY(y);
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
	public Position.Player getOwner() {
		return owner;
	}
	public void setOwner(Position.Player owner) {
		this.owner = owner;
	}
	public int getId() {
		return id;
	}
	public String getFrontImg() {
		return frontImg;
	}
	public String getBackImg() {
		return backImg;
	}
	
	
	
	//public void setCardImage(Bitmap img) {
	//	this.frontImg = img;
	//}
	
	
//	@Override
//    protected void draw(Canvas canvas,Context context) {
//		Bitmap resizedBitmap=null;
//		Matrix matrix = new Matrix();
//		matrix.postRotate(angle);
//		if(revealed)
//			resizedBitmap = Bitmap.createBitmap(frontImg, 0, 0, frontImg.getScaledWidth(canvas) , frontImg.getScaledHeight(canvas), matrix, true);
//		else
//			resizedBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.back), 0, 0, frontImg.getScaledWidth(canvas) , frontImg.getScaledHeight(canvas), matrix, true);
//		canvas.drawBitmap(resizedBitmap, getX()-25, getY()-20, new Paint());
//		
//		        
//		
//		
//		// if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
//        if(isCarried){
//        	Paint paint = new Paint(); 		   
//        	// draws the name of the carrier.
//            paint.setColor(android.graphics.Color.BLACK); 
//            paint.setTextSize(20); 
//            canvas.drawText(carrier,getX()-25, getY()-20, paint);
//            // draws the hand.
//            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hand),getX()-30, getY()+20 , paint);
//        }        
//       
//	}
	

}
