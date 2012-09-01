package utils;


import handlers.CardEventsHandler;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.animation.OvershootInterpolator;
import carddeckplatform.game.BitmapHolder;
import carddeckplatform.game.R;
import carddeckplatform.game.StaticFunctions;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.gui.animations.Animation;
import client.gui.animations.FlipAnimation;
import client.gui.animations.OvershootAnimation;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;

public abstract class Card extends Draggable implements Comparable<Card>{
	
	private CardEventsHandler eventsHandler;
	private boolean revealed;	
	private Position owner;
	private Point coord;
	protected float angle = 0;
	protected Point handScale;
	
	private int backAd;
	
//	private Paint paint;
	
	public Card(CardEventsHandler handler,String frontImg,String backImg) {
		
		this.eventsHandler=handler;		
		this.backImg=backImg;
		this.frontImg= frontImg;
		this.revealed=false;		
		this.coord=new Point(0,0);
		
		Random rand = new Random();
		
		this.backAd = rand.nextInt(3);
		
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
	public void setLocation(float x, float y){
		synchronized (coord){
			coord.setX(x);
			coord.setY(y);
		}
	}
	static public void moveTo(final ArrayList<Pair<Droppable, Droppable>> srcAndDst){
		ArrayList<Animation> animations=new ArrayList<Animation>();
		
		for(Pair pair : srcAndDst){
			for (Card card : ((Droppable)pair.getFirst()).getCards()){
				Animation animation=new FlipAnimation((Droppable)pair.getFirst(), (Droppable)pair.getSecond(), card, false);
				animations.add(animation);
				animation.execute();
			}
		}
		
		for (Animation animation : animations){
			animation.waitForMe();
		}
		
	}
	
	
	
	static public void moveTo(final ArrayList<Pair<Droppable, Droppable>> srcAndDst, int numOfCards){
		ArrayList<Animation> animations=new ArrayList<Animation>();
		
		int counter=0;
		
		for(Pair pair : srcAndDst){
			for (Card card : ((Droppable)pair.getFirst()).getCards()){
				Animation animation=new FlipAnimation((Droppable)pair.getFirst(), (Droppable)pair.getSecond(), card, false);
				animations.add(animation);
				animation.execute();
				counter++;
				if(counter==numOfCards)
					break;
			}
		}
		
		for (Animation animation : animations){
			animation.waitForMe();
		}
		
	}
	
	
	public void moveTo(final Droppable source,final Droppable destination) {
		Animation animation = new FlipAnimation(source, destination, this, false);
		animation.execute();
		//animation.waitForMe();
	}
		
	public void setFrontImg(String frontImg) {
		this.frontImg = frontImg;
	}
	
	public Point getCoord() {
		synchronized(coord){
			return new Point(coord);
		}
	}
//	public void setCoord(float x,float y) {
//			coord.setX(x);
//			coord.setY(y);
//	}
	@Override
	public void setAngle(float angle) {
		this.angle = angle%360;
	}
	@Override
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
	public Position getOwner() {
		return owner;
	}
	public void setOwner(Position owner) {
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
		Bitmap img;			
		Matrix matrix = new Matrix();
		Point p = null;
		if(!isInHand()){
			p = scale;
		}else{
			p = new Point((float)(scale.getX() * 1.5) , (float)(scale.getY() * 1.5));
		}
		
		p = MetricsConvertion.pointRelativeToPx(p);
		if(revealed){				
			img = BitmapHolder.get().getBitmap(frontImg);
		}else{
//			switch (backAd) {
//			case 0:
//				img = BitmapHolder.get().getBitmap(backImg);
//				break;
//			case 1:
//				img = BitmapHolder.get().getBitmap("bluead");
//				break;
//			case 2:
//				img = BitmapHolder.get().getBitmap("yellowad");
//				break;
//			default:
				img = BitmapHolder.get().getBitmap(backImg);
//				break;
//			}
			
		}
		
		// transformations.
		matrix.postScale((float)p.getX()/(float)img.getWidth(), (float)p.getY()/(float)img.getHeight());
		matrix.postTranslate(coord.getX()-p.getX()/2, coord.getY()-p.getY()/2);
		matrix.postRotate(angle , getX(), getY());
		
		canvas.drawBitmap(img, matrix, null);
		
//		if(getAnimationFlags().isAnimated())
//			canvas.drawText("Animated",getX() / 2, getY(), GameEnvironment.get().getPaint());
		// if the card is being carried by another player a hand and the name of the carrier would be drawn near the card's image.
        if(isCarried()){
        	Matrix matrix2 = new Matrix();
        	Point absHandScale = MetricsConvertion.pointRelativeToPx(handScale);
        	
        	matrix2.postScale(absHandScale.getX() / BitmapHolder.get().getBitmap("hand").getWidth(), absHandScale.getY() / BitmapHolder.get().getBitmap("hand").getWidth() );
        	matrix2.postTranslate(getX()-absHandScale.getX(), getY() - absHandScale.getY()/2);
        	Point absScale = MetricsConvertion.pointRelativeToPx(scale);
        	Paint paint = GameEnvironment.get().getPaint();   
        	// draws the name of the carrier.
            paint.setColor(android.graphics.Color.BLACK); 
            paint.setTextSize(20); 
            paint.setColor(Color.WHITE);
            canvas.drawText(getCarrier(),getX()-absScale.getX() / 2, getY()-absScale.getY() / 2, paint);
            // draws the hand.
            //canvas.drawBitmap(BitmapHolder.get().getBitmap("hand"),getX()-absScale.getX(), getY() - absScale.getY()/2 , paint);
            
            canvas.drawBitmap(BitmapHolder.get().getBitmap("hand"), matrix2, null);
        } 
       
	}

	public void flip() {
		if (this.revealed){
			this.hide();
		}else{
			this.reveal();
		}		
	}
	

}
