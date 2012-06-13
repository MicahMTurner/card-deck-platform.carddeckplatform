package utils;

import java.util.AbstractList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import utils.droppableLayouts.DroppableLayout;
import carddeckplatform.game.BitmapHolder;
import client.gui.entities.Droppable;
import handlers.ButtonEventsHandler;

public class Button{
	private ButtonEventsHandler handler;
	private String text;
	private String image;
	private Position.Button position;
	public Button(ButtonEventsHandler handler,Position.Button position,String text) {
		//super(position.getId(), position, new Point(10,13), DroppableLayout.LayoutType.NONE);
		this.handler=handler;
		this.text=text;
		this.image="button.png";
		this.position=position;
		//image="button.png";
	}
	public Position.Button getPosition() {
		return position;
	}
	public void setPosition(Position.Button position) {
		this.position = position;
	}
	public void onClic(){
		handler.onClick();
	}
	public void draw(Canvas canvas, Context context){
		Bitmap button=BitmapHolder.get().getBitmap(image);
		//canvas.drawBitmap(button, matrix, paint)
	}
//	@Override
//	public boolean onDrop(Player player, Droppable from, Card card) {
//		// TODO Auto-generated method stub
//		return super.onDrop(player, from, card);
//	}
//	@Override
//	public void deltCard(Card card) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected AbstractList<Card> getMyCards() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean onCardAdded(Player player, Card card) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean onCardRemoved(Player player, Card card) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int cardsHolding() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public boolean isEmpty() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public void clear() {
//		// TODO Auto-generated method stub
//		
//	}
	
}
