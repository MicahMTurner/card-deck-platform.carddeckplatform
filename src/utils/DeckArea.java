package utils;


import java.util.AbstractList;
import java.util.LinkedList;

import utils.droppableLayouts.DeckLayout;
import utils.droppableLayouts.DroppableLayout;
import client.controller.ClientController;
import client.gui.entities.Draggable;
import client.gui.entities.Droppable;


/**
 * represents a GUI instance of a deck area
 * @author Yoav
 *
 */
public class DeckArea extends Droppable{
	
	boolean hide=true;
	boolean putOnTop=false;
	

	public LinkedList<Card> cards = new LinkedList<Card>();
	/**
	 * constructor
	 * @param position deck area's position
	 */
	public DeckArea(Position.Button position) {
		super(position.getId(),position, DroppableLayout.LayoutType.DECK,null);
		this.image = "playerarea";
	}

	/**
	 * constructor
	 * @param position deck area's position
	 * @param hide whether to hide or reveal the cards when they are added to the deck area.
	 * @param putOnTop whether to put the card on top or bottom.
	 */
	public DeckArea(Position.Button position, boolean hide, boolean putOnTop){
		super(position.getId(),position, DroppableLayout.LayoutType.DECK,null);
		this.image = "playerarea";
		this.hide = hide;
		this.putOnTop = putOnTop;
		
	}
	
	@Override
	public void AddInPlace(Card card,int place) {
		this.cards.add(place,card);
	}
	@Override
	public AbstractList<Card> getMyCards() {
		return cards;
	}
		

	/**
	 * get number of cards that are in this deck area
	 * @return number of cards that are in this deck area
	 */
	public int getSize(){
		return cards.size();
	}

	@Override
	public void deltCard(Card card) {
		cards.push(card);
		card.setLocation(getX(), getY());
		
	}
	/**
	 * happens when a card is being added to this deck area
	 */
	@Override
	public boolean onCardAdded(Player player, Card card) {
		if(hide)
			card.hide();
		else
			card.reveal();
		
		if(!putOnTop)
			cards.addLast(card);
		else
			cards.add(card);
		
		return true;
		
	}
	/**
	 * happens when a card is being removed to this deck area
	 */
	@Override
	public boolean onCardRemoved(Player player, Card card) {
		cards.remove(card);
		return true;
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



	@Override
	public void onRoundEnd(Player player) {
	}



	@Override
	public Card peek() {
		return cards.peek();
	}
	
	@Override
	public boolean onLongPress(Draggable draggable, Droppable from){
		if(hasRulerCard() || !getCards().contains(draggable))
			return false;	
		
		setRulerCard((Card)draggable);
		ClientController.sendAPI().setRulerCard((Card)draggable, getId());
		return true;
	}

	private boolean hasRulerCard(){
		return ((DeckLayout)getDroppableLayout()).hasRulerCard();
	}
	
	public void setRulerCard(Card card){
		putCardOnBottom(card);
		((DeckLayout)getDroppableLayout()).setRulerCard(card);
		rearrange(0);

	}
}
