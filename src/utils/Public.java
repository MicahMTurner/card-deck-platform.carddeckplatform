package utils;

import handlers.PublicEventsHandler;
import client.gui.entities.Droppable;

public class Public extends Droppable{	
	private PublicEventsHandler handler;
	private Position.Public position;
	
	
	
	public Public(PublicEventsHandler handler,Position.Public position) {
		super(null);
		this.handler=handler;
		this.position=position;
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
	
	

	

}
