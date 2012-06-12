package utils;

import handlers.PublicEventsHandler;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import utils.droppableLayouts.DroppableLayout;
import utils.droppableLayouts.DroppableLayout.LayoutType;
import utils.droppableLayouts.HeapLayout;
import utils.droppableLayouts.line.BottomLineLayout;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import carddeckplatform.game.BitmapHolder;
import client.gui.entities.Droppable;
import client.gui.entities.MetricsConvertion;


public class Public extends Droppable{	
	private PublicEventsHandler handler;
	
	private LinkedList<Card> cards=new LinkedList<Card>();	
	
	
	
	public Public(PublicEventsHandler handler,Position.Public position,DroppableLayout.LayoutType layoutType , Point scale) {
//		super(position.getId(),position, new Point(10,13),layoutType);
		super(position.getId(),position, scale,layoutType);
		this.handler=handler;
		
		
		this.image = "freepublic";
		
		//BitmapHolder.get().scaleBitmap(image, this.scale);
	}
	
//	public void setPosition(Position.Public position) {
//		this.position = position;
//	}
//	
	
	@Override
	public boolean onCardAdded(Player player,Card card){
		cards.addFirst(card);
		boolean answer=handler.onCardAdded(this,player, card);
		if (!answer){
			cards.removeFirst();
		}
		return answer; 
	}
	@Override
	public boolean onCardRemoved(Player player,Card card){
		boolean answer=handler.onCardRemoved(this,player, card);
		if (answer || player==null){
			cards.remove(card);
		}		
		return answer;
	}
	
	public void revealCard(Player player,Card card){
		card.reveal();
		handler.onCardRevealed(this,player, card);
	}
	
	public void roundEnded(Player player){
		handler.onRoundEnd(this,player);
	}

//	@Override
//	public int getX() {
//		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getX();		
//	}
//
//	@Override
//	public int getY() {
//		return MetricsConvertion.pointRelativeToPx(position.getPoint()).getY();		
//	}

//	@Override
//	public void draw(Canvas canvas,Context context) {
//		Paint paint=new Paint();
//		paint.setColor(Color.BLUE);
//		canvas.drawCircle(shape.getCenterX(), shape.getCenterY(), 50, paint);
//		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), 0x7f02002e),getX()-28,getY()-27,null);
//	}

	@Override
	public void deltCard(Card card) {
		cards.add(card);
		card.setLocation(getX(), getY());
		
	}

	@Override
	public int cardsHolding() {		
		return cards.size();
	}

	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public void clear() {
		cards.clear();		
	}
	public LinkedList<Card> getMyCards() {
		return cards;
	}
	public Card peek(){
		if (!cards.isEmpty()){
			return cards.get(cards.size()-1);
		}else{
			return null;
		}
	}	
}
