package client.gui.entities;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Random;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import utils.Card;
import utils.Player;
import utils.Point;
import utils.Position;
import utils.droppableLayouts.DroppableLayout;
import utils.droppableLayouts.DroppableLayout.LayoutType;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import carddeckplatform.game.BitmapHolder;
import client.controller.ClientController;

public abstract class Droppable implements Serializable {
	public int getGlowColor() {
		return glowColor;
	}

	public void setGlowColor(int glowColor) {
		this.glowColor = glowColor;
	}

	/**
	 * **************************************************<br/>
	 * **************************************************<br/>
	 * ****18**********************************19********<br/>
	 * ****************1**2**3**4**5*********************<br/>
	 * ****************6**7**8**9**10********************<br/>
	 * ****************11*12*13*14*15********************<br/>
	 * **************************************************<br/>
	 * ****16**********************************17********<br/>
	 * **************************************************<br/>
	 * 
	 * @author Yoav
	 * 
	 */
	
	enum DroppableStatus{
		MINIMIZED,NORMAl,SELECTED,DRAGGED,
	}
	
	// protected Point point;
	// public Stack<GuiCard>guiCards=new Stack<GuiCard>();
	// protected Stack<Card> cards = new Stack<Card>();
	// protected ArrayList<Card> cards;
	private DroppableLayout.LayoutType layoutType;
	protected transient Shape shape = null;
	protected int id;
	protected Position position;
	protected Point scale;
	protected String image;
	protected transient DroppableLayout droppableLayout=null;
	protected transient Paint mPaintForGlow;
	protected int alpha=255;
	protected int glowColor=0;
	
	
	
	private DroppableLayout getDroppableLayout() {
		if(droppableLayout==null)
			this.droppableLayout=layoutType.getLayout(this);
		
		return droppableLayout;
	}
	
	
	public int getAlpha() {
		return alpha;
	}
	public abstract Card peek();
	public abstract void onRoundEnd(Player player);
	public void setAlpha(int alpha) {
		this.alpha = alpha;
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
		if (addCard(player, card) && from.removeCard(player, card)){
			answer=true;
//			if (droppableLayout != null && answer){
//				rearrange(0);
//			}
		}
		
		return answer;

	}
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

	public abstract void deltCard(Card card);
	protected abstract AbstractList<Card> getMyCards();
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
		boolean answer;
		answer = onCardAdded(player, card);
		if (getDroppableLayout() != null && answer){
			rearrange(0);
		}
		return answer;
	}

	public boolean removeCard(Player player, Card card){
		boolean answer;
		answer = onCardRemoved(player, card);
		if (getDroppableLayout() != null && answer){
			rearrange(0);
		}
		return answer;
	}
	
	public void sort(){
		Collections.sort(getMyCards());
	}
	
	public void sort(Comparator comperator){
		Collections.sort(getMyCards(), comperator);
	}
	
	public abstract boolean onCardAdded(Player player, Card card);
	
	public abstract boolean onCardRemoved(Player player, Card card);

	public ArrayList<Draggable> draw(Canvas canvas, Context context) {
		ArrayList<Draggable> holding=null;
		Bitmap img = BitmapHolder.get().getBitmap(image);
		if (img!=null){
			Matrix matrix = new Matrix();

		
			Point absScale = MetricsConvertion.pointRelativeToPx(getScale());

			matrix.postScale((float) absScale.getX() / (float) img.getWidth(),(float) absScale.getY() / (float) img.getHeight());
			matrix.postTranslate(getX() - absScale.getX() / 2, getY() - absScale.getY() / 2);

		
			canvas.drawBitmap(img, matrix, null);
			if(mPaintForGlow==null){
				mPaintForGlow=new Paint();			
				mPaintForGlow.setDither(true);
				mPaintForGlow.setAntiAlias(true);
				mPaintForGlow.setFilterBitmap(true);  
			
			}		
			ColorFilter colorFilterTint = new LightingColorFilter(Color.WHITE,glowColor);
			mPaintForGlow.setColorFilter(colorFilterTint);
			canvas.drawBitmap(img, matrix, mPaintForGlow);
		
		}
		AbstractList<Card>cards = getCards();
		int size=cards.size()-1;
		Card card=null;
		for (int i=size;i>=0;i--){
			card=cards.get(i);
				if (!card.isInHand() && !card.isCarried()){
					card.draw(canvas, context);
				}else{
					if (holding==null){
						holding=new ArrayList<Draggable>();
					}
					holding.add(card);
				}
		}
		return holding;
		// canvas.drawBitmap(img,getX()-(img.getWidth() /
		// 2),getY()-(img.getHeight() / 2),null);
	}

	public Droppable(int id, Position position, Point scale,DroppableLayout.LayoutType layoutType) {
		this.id = id;
		this.scale = scale;
		this.position = position;
		this.layoutType = layoutType;
//		this.droppableLayout=layoutType.getLayout(this);
		// this.shape=getShape();
		// this.cards=new ArrayList<Card>();
		// this.point=new Point(190,175);
		// this.myId=IDMaker.getMaker().getId(position);
	}

	public float getX() {
		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getX();
	}

	public float getY() {
		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getY();
	}
	public void rearrange(int index) {
		if(cardsHolding()==0)
			return;
		Point droppableSize = MetricsConvertion.pointRelativeToPx(getScale());
		Point card=MetricsConvertion.pointRelativeToPx(getCards().get(0).getScale());
		
		
		
		if (getDroppableLayout() != null)
			getDroppableLayout().rearrange(index, droppableSize.getX()-card.getX(), droppableSize.getY()-card.getY());

	}
	public abstract int cardsHolding();

	public abstract boolean isEmpty();

	public abstract void clear();

	public int getId() {
		return id;
	}

	public void setPosition(Position relativePosition) {
		this.position = relativePosition;
		AbstractList<Card>cards=getCards();
		if (getDroppableLayout() != null){
			rearrange(0);
		}
//		for (Card card : cards){
//			card.setLocation(getX(), getY());
//		}

	}

}
