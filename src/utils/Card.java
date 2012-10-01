package utils;


import handlers.CardEventsHandler;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import carddeckplatform.game.BitmapHolder;
import carddeckplatform.game.gameEnvironment.GameEnvironment;
import client.gui.animations.Animation;
import client.gui.animations.FlipAnimation;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;

public abstract class Card extends Draggable implements Comparable<Card>{
	
	private CardEventsHandler eventsHandler;
	private boolean revealed;	
	private int owner;
	private Point coord;
	private Point shadowOffset;
	protected float angle = 0;
	protected Point handScale;
	private transient Paint cardPaint;
	private transient Paint cardPaintInHand = new Paint();
	private int backAd;
	

	
	public Card(CardEventsHandler handler,String frontImg,String backImg) {
		
		this.eventsHandler=handler;		
		this.backImg=backImg;
		this.frontImg= frontImg;
		this.revealed=false;		
		this.coord=new Point(0,0);
		
		Random rand = new Random();
		
		this.backAd = rand.nextInt(3);
		shadowOffset = new Point(1.5f,1.5f);
		shadowOffset = MetricsConvertion.pointRelativeToPx(shadowOffset);
		
		cardPaintInHand.setAlpha(180);
		cardPaintInHand.setAntiAlias(true);
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
	
	/**
	 * takes each card in one droppable and move them to another droppable.
	 * @param srcAndDst ArrayList of Pairs of droppables.
	 * @see Pair.
	 */
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
	
	
	/**
	 * takes numOfCards cards in one droppable and move them to another droppable.
	 * @param srcAndDst ArrayList of Pairs of droppables.
	 * @see Pair.
	 * @param numOfCards the number of cards that has to move.
	 */
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
		
	/**
	 * set the front image
	 * @param frontImg front image name.
	 */
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
	
	/**
	 * reveals the card.
	 */
	public void reveal(){
		this.revealed = true;
		eventsHandler.onReveal(this);
	}
	
	/**
	 * 
	 * @param revealed
	 */
	public void setRevealed(boolean revealed) {
		this.revealed = revealed;
	}
	
	/**
	 * hides the card.
	 */
	public void hide(){
		this.revealed = false;
		eventsHandler.OnHide(this);
	}
	
	/**
	 * returns whether the card is revealed or not.
	 * @return
	 */
	public boolean isRevealed() {
		return revealed;
	}
	
	/**
	 * gets the owner of the card.
	 * @return the id of the owner
	 */
	public int getOwner() {
		return owner;
	}
	
	/**
	 * set the owner.
	 * @param owner
	 */
	public void setOwner(int owner) {
		this.owner = owner;
	}	
	
	/**
	 * gets card's front image.
	 * @return
	 */
	public String getFrontImg() {
		return frontImg;
	}
	
	/**
	 * gets card's back image.
	 * @return
	 */
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
			p = new Point((float)(scale.getX() * 2.5) , (float)(scale.getY() * 2.5));
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
		
		
		
		if(getAnimationFlags().isAnimated()){
			
			Point offset = new Point(shadowOffset);
			
			if(isInHand()){
				offset.x = offset.x * 2f;
				offset.y = offset.y * 2f;
			}
			
			Bitmap shadow;
			shadow = BitmapHolder.get().getBitmap("cardshadow");
			Matrix matrix2 = new Matrix();
			
			matrix2.postScale((float)p.getX()/(float)shadow.getWidth(), (float)p.getY()/(float)shadow.getHeight());
			matrix2.postTranslate((coord.getX()-p.getX()/2)+offset.getX(), (coord.getY()-p.getY()/2)+offset.getY());
			matrix2.postRotate(angle , getX()+offset.getX(), getY()+offset.getY());
			
			
			canvas.drawBitmap(shadow, matrix2, null);
		}
		if(cardPaint==null){
			cardPaint = new Paint();
		}
		cardPaint.setAntiAlias(true);
		if(isInHand())
			cardPaint.setAlpha(180);
		else
			cardPaint.setAlpha(255);
		
		canvas.drawBitmap(img, matrix, cardPaint);
		
			
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

	/**
	 * flips the card.
	 */
	public void flip() {
		if (this.revealed){
			this.hide();
		}else{
			this.reveal();
		}		
	}
	

}
