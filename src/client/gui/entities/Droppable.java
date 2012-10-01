package client.gui.entities;

import handlers.Handler;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import utils.Card;
import utils.Player;
import utils.Point;
import utils.Position;
import utils.droppableLayouts.DroppableLayout;
import utils.droppableLayouts.DroppableLayout.LayoutType;
import utils.droppableLayouts.LineLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import client.controller.ClientController;

/**
 * this class represents an area where cards can be placed at
 *
 */
public abstract class Droppable implements Serializable {

	
	

	private DroppableLayout.LayoutType layoutType;
	protected transient Shape shape = null;
	protected int id;
	protected Position position;
	protected Point scale;
	protected String image;
	protected transient DroppableLayout droppableLayout=null;
	protected transient Paint mPaintForGlow;
	protected int alpha=255;
	protected int droppableColor=0;
	public final int originalDroppableColor=0;
	private Handler handler;
	
	/**
	 * constructor 
	 * @param id droppable's id
	 * @param position droppable's position
	 * @param layoutType droppable's layout
	 */
	public Droppable(int id, Position position ,DroppableLayout.LayoutType layoutType,Handler handler) {
		this.id = id;
		this.position = position;
		this.layoutType = layoutType;
		this.scale = layoutType.getScale();
		this.handler=handler;
	}
	
	
	public boolean onFlipCard(Card card){
		boolean answer=false;
		if (handler!=null && (answer=handler.onFlipCard(card))){
			card.flip();
		}
		return answer;
	}
	protected DroppableLayout getDroppableLayout() {
		if(droppableLayout==null)
			this.droppableLayout=layoutType.getLayout(this);
		
		return droppableLayout;
	}
	
	
	public int getAlpha() {
		return alpha;
	}
	/**
	 * get (but don't remove) the first card in this droppable
	 * @return
	 */
	public abstract Card peek();
	/**
	 * called when a round ended
	 * @param player the player that ended the round
	 */
	public abstract void onRoundEnd(Player player);
	
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	
	public void locationChangedNotify(){
		
		Point size = MetricsConvertion.pointRelativeToPx(getScale());
		shape = new Rectangle(getX() - (size.getX() / 2), getY()
				- (size.getY() / 2), size.getX(), size.getY());
		
		if(droppableLayout!=null){
			droppableLayout.locationChangedNotify();
			rearrange(0);
		}
		
		
	}
	public Shape getShape() {
		if (shape == null) {
			Point size = MetricsConvertion.pointRelativeToPx(getScale());
			shape = new Rectangle(getX() - (size.getX() / 2), getY()
					- (size.getY() / 2), size.getX(), size.getY());
		}
		return shape;
	}
	
	public Point getScale() {
		return scale;
	}
	
	public void setScale(Point scale) {
		this.scale = scale;
	}

	public boolean onDrop(Player player, Droppable from, Card card) {               
        boolean answer=false;           
        ClientController.sendAPI().cardAdded(card, from.getId(), id, player.getId());   
        int placeOfCard=from.getCards().indexOf(card);        

        //check if remove is legal move
        if (from.removeCard(player, card)){
        	if (addCard(player, card)){
        		//remove and add are legal moves
        		
        		answer=true;
        		if (from.getDroppableLayout() != null && answer){
        			from.rearrange(0);	
        		}
        	}else{
        		//add is not legal, return removed card back to its place
        		from.AddInPlace(card,placeOfCard); 
                
        	}
        }        
        return answer;
	}
	
	/**
	 * add given card in given place in this droppable
	 * @param card given card
	 * @param place given place where card should be inserted
	 */
	public abstract void AddInPlace(Card card,int place);
	
	public boolean isFlingabble(){
		if(getDroppableLayout()==null)
			return false;
		if(LayoutType.NONE.equals(getDroppableLayout().getType()))
			return false;
		
		return true;
	}
	public boolean isContain(float x, float y) {
		return getShape().contains(x, y);

	}

	public boolean isIntersect(Line line) {
		return line.intersects(getShape()) || getShape().contains(line);
	}
	/**
	 * get the droppable's current position (can be changed if using LivePosition feature)
	 * @return droppable's current position
	 */
	public Position getPosition() {
		return position;
	}
	
	public int indexOfDraggabale(Draggable draggable) {

		return getCards().indexOf(draggable);

	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Droppable)) {
			return false;
		}
		Droppable otherDroppable = (Droppable) other;
		return this.id == otherDroppable.id;
	}
	/**
	 * deal card to this droppable (without calling the handler's function)
	 * @param card the card we want to deal
	 */
	public abstract void deltCard(Card card);
	
	protected abstract List<Card> getMyCards();
	
	public AbstractList<Card> getCards(){
		synchronized(this){		
			try{
			AbstractList<Card> cloned=new ArrayList<Card>();
			for (Card card : getMyCards()){
				cloned.add(card);
			}
			return cloned;
			}
			catch(ConcurrentModificationException e){
				return new ArrayList<Card>();
						
			}
		}
	}

	
	
	public boolean addCard(Player player, Card card){
		boolean answer=false;

		answer = onCardAdded(player, card);
		if (getDroppableLayout() != null && answer){
			card.getAnimationFlags().resetFlags();
			rearrange(0);
		}
		return answer;
	}

	public boolean removeCard(Player player, Card card){
		boolean answer;
		answer = onCardRemoved(player, card);
		
		return answer;
	}
	
	public void sort(){
		if(getDroppableLayout().getClass().isInstance(LineLayout.class));
			Collections.sort(getMyCards());
	}
	
	public void sort(Comparator comperator){
		Collections.sort(getMyCards(), comperator);
	}
	/**
	 * move given card to front of this droppable (if it contains the card) 
	 * @param card given card we want to move to front
	 */
	public void putCardOnTop(Card card){
		List<Card> cards = getMyCards();
		
		// check if the card is not in the list for some reason.
		if(!cards.contains(card))
			return;
	
		cards.remove(card);
		cards.add(0, card);
	
	}
	/**
	 * happens when a card is being added to this droppable
	 * @param player the player who added the card (null if logic of the game did it)
	 * @param card the card that was added
	 * @return true if adding card to this droppable is valid action, false OW
	 */
	public abstract boolean onCardAdded(Player player, Card card);
	
	public void putCardOnBottom(Card card){
		List<Card> cards = getMyCards();
		
		// check if the card is not in the list for some reason.
		if(!cards.contains(card))
			return;
	
		cards.remove(card);
		cards.add(cards.size(), card);
	
	}
	
	/**
	 * happens when a card is being removed to this droppable
	 * @param player the player who removed the card (null if logic of the game did it)
	 * @param card the card that was removed
	 * @return true if removing card to this droppable is valid action, false OW
	 */
	public abstract boolean onCardRemoved(Player player, Card card);
	/**
	 * drawing method of cards in this public area
	 * @param canvas canvas instance
	 * @param context context instance
	 */
	public void drawMyCards(Canvas canvas,Context context){
		AbstractList<Card>cards = getCards();
		int size=cards.size()-1;
		Card card=null;
		for (int i=size;i>=0;i--){
			card=cards.get(i);
			if(!card.getAnimationFlags().isAnimated())
				card.draw(canvas, context);
			else
				Table.animatedCards.add(card);
		}	
	}
	/**
	 * drawing method of public area
	 * @param canvas canvas instance
	 * @param context context instance
	 */
	public void draw(Canvas canvas, Context context) {
		float radius = 10f;
		Shape s = getShape();
		//canvas.clipPath(clipPath);
		//canvas.drawBitmap(img, matrix, null);
		initiatePaintForGlow();
		ColorFilter colorFilterTint = new LightingColorFilter(Color.WHITE,droppableColor);
		mPaintForGlow.setColorFilter(colorFilterTint);
		mPaintForGlow.setAlpha(50);
		//mPaintForGlow.setARGB(50, 0, 250, 0);
		//canvas.drawBitmap(img, matrix, mPaintForGlow);	
		canvas.drawRoundRect(new RectF(new Rect((int)s.getMinX(), (int)s.getMinY(),(int)s.getMaxX(), (int)s.getMaxY())), radius, radius, mPaintForGlow);
			
		
	}

	private void initiatePaintForGlow() {
		if(mPaintForGlow==null){
			mPaintForGlow=new Paint();			
			mPaintForGlow.setDither(true);
			mPaintForGlow.setAntiAlias(true);
			mPaintForGlow.setFilterBitmap(true);  			ColorFilter colorFilterTint = new LightingColorFilter(Color.WHITE,droppableColor);
			mPaintForGlow.setColorFilter(colorFilterTint);	
		}				
	}


	/**
	 * get x point of droppable
	 * @return x point of droppable
	 */
	public float getX() {
		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getX();
	}
	/**
	 * get y point of droppable
	 * @return y point of droppable
	 */
	public float getY() {
		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getY();
	}
	/**
	 * rearrange cards in this droppable
	 * @param index should be 0
	 */
	public void rearrange(int index) {
		try {
			if(cardsHolding()==0)
				return;
			Point droppableSize = MetricsConvertion.pointRelativeToPx(getScale());
			Point card=MetricsConvertion.pointRelativeToPx(getCards().get(0).getScale());
			
			
			
			if (getDroppableLayout() != null)
				getDroppableLayout().rearrange(index, droppableSize.getX()-card.getX(), droppableSize.getY()-card.getY());
		} catch (Exception e) {			
		}
		

	}
	/**
	 * get how many cards this public area is holding
	 * @return the number of cards this public area is holding
	 */
	public abstract int cardsHolding();
	
	/**
	 * checks if public area is empty from cards 
	 * @return true is no cards in this public area, false OW
	 */
	public abstract boolean isEmpty();

	/**
	 * clear all cards in this public area
	 */
	public abstract void clear();

	/**
	 * get public area's ID
	 * @return public area's ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * set position of this public area according to the player's relative position around the table
	 * @param relativePosition player's relative position around the table
	 */
	public void setPosition(Position relativePosition) {
		this.position = relativePosition;
		if (getDroppableLayout() != null){
			rearrange(0);
		}
	}
	
	public int getGlowColor() {
		return droppableColor;
	}

	public void setGlowColor(int glowColor) {
		this.droppableColor = glowColor;
	}
	
	
	public boolean onLongPress(Draggable draggable, Droppable from){
		return false;
	}

}
