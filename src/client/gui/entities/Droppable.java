package client.gui.entities;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Stack;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import utils.Card;
import utils.Player;
import utils.Point;
import utils.Position;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import carddeckplatform.game.BitmapHolder;
import client.controller.ClientController;

public abstract class Droppable implements Serializable{
	/**
	 *  **************************************************<br/>
	 *  **************************************************<br/>
	 *  ****18**********************************19********<br/>
	 *  ****************1**2**3**4**5*********************<br/>
	 *  ****************6**7**8**9**10********************<br/>
	 *  ****************11*12*13*14*15********************<br/>
	 *  **************************************************<br/>
	 *  ****16**********************************17********<br/>
	 *  **************************************************<br/>
	 * 
	 * @author Yoav
	 *
	 */
	
	
	//protected Point point;	
	//public Stack<GuiCard>guiCards=new Stack<GuiCard>(); 
	//protected Stack<Card> cards = new Stack<Card>();
	//protected ArrayList<Card> cards; 
	
	public Shape getShape() {		
		Point size = MetricsConvertion.pointRelativeToPx(this.scale);
		return new Rectangle(getX() - (size.getX() / 2), getY() - (size.getY() / 2), size.getX() , size.getY());
	}
	
	//protected transient Shape shape;
	protected int id;
	protected Position position;

	protected Point scale;
	protected String image;
	
	public void onDrop(Player player,Droppable from, Card card){
		from.removeCard(player,card);
		//card.setCoord(getX(), getY());
		ClientController.sendAPI().cardAdded(card, from.getId(),id,player);
		addCard(player,card);

		
	}
	public boolean isContain(float x,float y){
		//return shape.contains(x, y);
		return getShape().contains(x, y);
		
	}
	public boolean isIntersect(Line line){
//		try {
		//return line.intersects(shape)||shape.contains(line);
		return line.intersects(getShape())||getShape().contains(line);
//		} catch (Exception e) {
//			// TODO: handle exception
//			// TODO: handle exception
//			this.shape=getShape();
//			return line.intersects(shape)||shape.contains(line);
//		}
		
		
	}
	
	
	
	
	public Position getPosition(){
		return position;
	}
	public void onCardAdded(Player byWhom, Card card) {
		//card.setCoord(getX(),getY());
		addCard(byWhom, card);
	}
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Droppable)) { 
            return false;
        }
        Droppable otherDroppable = (Droppable)other;
        return this.id==otherDroppable.id;		
	}
	public abstract void deltCard(Card card);
	public abstract AbstractList<Card> getCards();
	public abstract void addCard(Player player,Card card);	
	public abstract void removeCard(Player player,Card card);
	
	public void draw(Canvas canvas,Context context){
		Bitmap img = BitmapHolder.get().getBitmap(image);
		canvas.drawBitmap(img,getX()-(img.getWidth() / 2),getY()-(img.getHeight() / 2),null);
	}
	
	public Droppable(int id,Position position, Point scale){
		this.id=id;
		this.scale = scale;
		this.position=position;
		//this.shape=getShape();
		//this.cards=new ArrayList<Card>();		
		//this.point=new Point(190,175);
		//this.myId=IDMaker.getMaker().getId(position);
	}

	
	public float getX() {
		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getX();		
	}

	public float getY() {
		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getY();		
	}
	public abstract int cardsHolding();
	public abstract boolean isEmpty();
	public abstract void clear();
	public int getId(){
		return id;
	}
	
	public void setPosition(Position relativePosition) {
	this.position=relativePosition;
		
	}

	
		
}
