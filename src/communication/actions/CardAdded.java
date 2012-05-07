package communication.actions;

import java.util.ArrayList;

import client.controller.ClientController;

import utils.Card;

public class CardAdded implements Action{
	private ArrayList<Card> cards=new ArrayList<Card>();
	private int from;
	private int to;
	
	
	public CardAdded(ArrayList<Card> cards, int from,int to) {		
		for(Card card : cards){
			this.cards.add(card);
		}
		this.from = from;
		this.to=to;
		//this.revealAtEnd=revealAtEnd;
		//this.revealWhileMoving=revealwhileMoving;
	}

	@Override
	public void execute() {
		ClientController.get().cardMoved(cards, from, to);
		
	}
	
}
