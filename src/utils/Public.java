package utils;

import handlers.PublicEventsHandler;

import java.util.LinkedList;

import utils.droppableLayouts.DroppableLayout;
import client.gui.entities.Droppable;


public class Public extends Droppable{	
	private PublicEventsHandler handler;
	
	protected LinkedList<Card> cards=new LinkedList<Card>();	
	
	
	
	public Public(PublicEventsHandler handler,Position.Public position,DroppableLayout.LayoutType layoutType) {
		super(position.getId(),position, layoutType);
		this.handler=handler;
		
		
		this.image = "freepublic";
		
	}
	
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


	@Override
	public void deltCard(Card card) {
		cards.addFirst(card);
		card.setLocation(getX(), getY());
		
	}
	@Override
	public void AddInPlace(Card card,int place) {
		this.cards.add(place,card);
		
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
		if (cards.size()>1){
			return cards.get(1);
		}
		return cards.peek();
//		if (!cards.isEmpty()){
//			return cards.get(cards.size()-1);
//		}else{
//			return null;
//		}
	}

	@Override
	public void onRoundEnd(Player player) {
		handler.onRoundEnd(this,player);
		
	}	
}
