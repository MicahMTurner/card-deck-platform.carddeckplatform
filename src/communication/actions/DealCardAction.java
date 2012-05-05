package communication.actions;

import java.util.ArrayList;

import utils.Card;

import client.controller.ClientController;

public class DealCardAction implements Action{
	private int from;
	private int to;
	private ArrayList<Card>cards=new ArrayList<Card>();
	private boolean revealWhileMoving;
	private boolean revealAtEnd;
	
	public DealCardAction(ArrayList<Card> cards,boolean revealWhileMoving,boolean revealAtEnd,int from,int to) {
		for(Card card : cards){
			this.cards.add(card);
		}
		this.revealAtEnd=revealAtEnd;
		this.revealWhileMoving=revealWhileMoving;
		this.from=from;
		this.to=to;
	}
	@Override
	public void execute() {
		ClientController.getController().dealCards(cards, from, to,revealWhileMoving,revealAtEnd);
		
		
	}

}
