package communication.actions;

import java.util.ArrayList;

import utils.Card;

import client.controller.ClientController;

public class DealCardAction implements Action{
	private int to;
	private ArrayList<Card>cards=new ArrayList<Card>();
	
	public DealCardAction(ArrayList<Card> cards,int to) {
		for(Card card : cards){
			this.cards.add(card);
		}
		this.to=to;
	}
	@Override
	public void execute() {
		ClientController.get().dealCards(cards,to);
	}

}
