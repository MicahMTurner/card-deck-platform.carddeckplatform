package utils;


import handlers.CardEventsHandler;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import carddeckplatform.game.R;
import client.gui.entities.Draggable;
import client.gui.entities.MetricsConvertion;

public abstract class Card extends Draggable implements Comparable<Card>{
	
	private CardEventsHandler eventsHandler;
	private final String frontImg;
	private final String backImg;
	private boolean revealed;	
	private Position.Player owner;
	private Point coord;
	protected float angle = 0;
	private Paint paint;
	
	public Card(CardEventsHandler handler,String frontImg,String backImg) {
		
		this.eventsHandler=handler;		
		this.backImg=backImg;
		this.frontImg= frontImg;
		this.revealed=false;		
		this.coord=new Point(0,0);
		
		paint = new Paint();
	}
	
	@Override
	public int compareTo(Card another) {
		if (this.id==another.id){
			return 0;
		}
		return 1;
	}
	@Override
	public int sensitivityRadius(){
		return 30;
	}
	@Override
	public void setLocation(int x, int y){
		coord.setX(x);
		coord.setY(y);
	}
	
	public Point getCoord() {
		return coord;
	}
	public void setCoord(int x,int y) {
		coord.setX(x);
		coord.setY(y);
	}
	public void setAngle(float angle) {
		this.angle = angle%360;
	}
	public float getAngle() {
		return angle;
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
	public String getFrontImg() {
		return frontImg;
	}
	public String getBackImg() {
		return backImg;
	}
	
	public void randomizeAngle(){
		Random generator = new Random();
		float randomIndex = generator.nextInt(20);
		randomIndex -= 10;
		angle = randomIndex;
	}
	
	
	@Override
    public void draw(Canvas canvas,Context context) {
    	Bitmap resizedBitmap=null;				
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		
		Point p;
		if(!isInHand()){
			p = new Point(6,10);
		}else{
			p = new Point(9,15);
		}
		
		p = MetricsConvertion.pointRelativeToPx(p);
		//matrix.postScale(p.getX(), p.getY());
		int resourceId;
		if(revealed){		
			resourceId=context.getResources().getIdentifier(frontImg, "drawable", "carddeckplatform.game");
			Bitmap frontImg = BitmapFactory.decodeResource(context.getResources(), resourceId);	
			
			matrix.postScale((float)p.getX()/(float)frontImg.getWidth(), (float)p.getY()/(float)frontImg.getHeight());
			
			resizedBitmap = Bitmap.createBitmap(frontImg, 0, 0, frontImg.getScaledWidth(canvas) , frontImg.getScaledHeight(canvas), matrix, true);
		}else{
			resourceId=context.getResources().getIdentifier(backImg, "drawable", "carddeckplatform.game");
			Bitmap backImg = BitmapFactory.decodeResource(context.getResources(), resourceId);
			int w = backImg.getWidth();
			int h = backImg.getHeight();
			matrix.postScale((float)p.getX()/(float)backImg.getWidth(), (float)p.getY()/(float)backImg.getHeight());
			resizedBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.back), 0, 0, backImg.getScaledWidth(canvas) , backImg.getScaledHeight(canvas), matrix, true);
			
		}
		canvas.drawBitmap(resizedBitmap, coord.getX()-resizedBitmap.getWidth()/2, coord.getY()-resizedBitmap.getHeight()/2, paint);

		// if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
        if(isCarried()){
        	Paint paint = new Paint(); 		   
        	// draws the name of the carrier.
            paint.setColor(android.graphics.Color.BLACK); 
            paint.setTextSize(20); 
            canvas.drawText(getCarrier(),getX()-25, getY()-20, paint);
            // draws the hand.
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.hand),getX()-30, getY()+20 , paint);
        } 
       
	}
	

}
