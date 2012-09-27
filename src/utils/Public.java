package utils;

import handlers.PublicEventsHandler;

import java.util.LinkedList;

import utils.droppableLayouts.DroppableLayout;
import utils.droppableLayouts.DroppableLayout.LayoutType;
import client.gui.entities.Droppable;

/**
 * this class represents an area where cards can be placed for all to see
 * (cards can bee seen faced down or up)
 * @author Yoav
 *
 */
public class Public extends Droppable{	
	private PublicEventsHandler handler;
	
	protected LinkedList<Card> cards=new LinkedList<Card>();	
	
	/**
	 * constructor 
	 * @param handler the public area handler for the game
	 * @param position public area's position
	 * @param layoutType layoutType of the public zone
	 * @see LayoutType
	 */
	public Public(PublicEventsHandler handler,Position.Public position,DroppableLayout.LayoutType layoutType) {
		super(position.getId(),position, layoutType,handler);
		this.handler=handler;			
		this.image = "drop";
		
	}
	
	@Override	
	public boolean onCardAdded(Player player,Card card){
		boolean answer;
		synchronized (cards) {
			cards.addFirst(card);
			answer=handler.onCardAdded(this,player, card);
			if (!answer){
				cards.removeFirst();
			}
		}
		
		
		return answer; 
	}
	
	@Override
	public boolean onCardRemoved(Player player,Card card){
		boolean answer;
		synchronized (cards) {
			answer=handler.onCardRemoved(this,player, card);
			if (answer || player==null){
				cards.remove(card);
			}		
		}
		return answer;
	}
	/**
	 * reaveal a card in this public
	 * @param player player that did the action
	 * @param card the card we want to reaveal
	 */
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
	/**
	 * get first/top card in this droppable (not removing it from the droppable)
	 * if public has more than 1 card, returns the 2nd one (for use in handlers - card is added before handler is called)
	 */
	public Card peek(){
		if (cards.size()>1){
			return cards.get(1);
		}
		return cards.peek();
	}
	public Card first(){		
		return cards.peek();
	}
	@Override
	public void onRoundEnd(Player player) {
		handler.onRoundEnd(this,player);
		
	}	
}
