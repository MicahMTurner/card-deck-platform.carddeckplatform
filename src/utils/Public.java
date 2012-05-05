package utils;

import java.util.ArrayList;

import IDmaker.IDMaker;
import android.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import handlers.PublicEventsHandler;
import client.gui.entities.Droppable;


public class Public extends Droppable{	
	private PublicEventsHandler handler;
	private Position.Public position;
	private ArrayList<Card> cards=new ArrayList<Card>();
	private int id;
	
	
	
	public Public(PublicEventsHandler handler,Position.Public position) {
		this.handler=handler;
		this.position=position;
		this.id=IDMaker.getMaker().getId();
	}
	
	public void setPosition(Position.Public position) {
		this.position = position;
	}
	
	public Position.Public getPosition() {
		return position;
	}
	
	public int sensitivityRadius() {		
		return 30;
	}
	
	@Override
	public void addCard(Player player,Card card){
		cards.add(card);
		handler.onCardAdded(this,player, card);
	}
	@Override
	public void removeCard(Player player,Card card){
		cards.remove(card);
		handler.onCardRemoved(this,player, card);
		
	}
	public void revealCard(Player player,Card card){
		card.reveal();
		handler.onCardRevealed(this,player, card);
	}
	public void roundEnded(Player player){
		handler.onRoundEnd(this,player);
	}

	@Override
	public int getX() {
		return position.getX();		
	}

	@Override
	public int getY() {
		return position.getY();
		
	}

	@Override
	public void draw(Canvas canvas,Context context) {
		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), 0x7f02002e),getX()-28,getY()-27,null);
	}

	@Override
	public void deltCard(Card card) {
		cards.add(card);
		
	}

	@Override
	public int getMyId() {		
		return id;
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
	public ArrayList<Card> getCards() {
		return cards;
	}
	public Card peek(){
		return cards.get(cards.size()-1);
	}
	
	

	

}
