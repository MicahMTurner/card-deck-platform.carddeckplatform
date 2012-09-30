package communication.actions;

import java.util.ArrayList;

import client.controller.ClientController;

import utils.Card;
import utils.Player;

public class CardAdded implements Action{
	//private ArrayList<Card> cards=new ArrayList<Card>();
	//private int cardId;
	private Card card;
	private int byWhomId;
	private int from;
	private int to;
	
	
	public CardAdded(Card card, int from,int to,int byWhomId) {		
	//	for(Card card : cards){
	//		this.cards.add(card);
	//	}
		this.from = from;
		this.to=to;
		this.byWhomId=byWhomId;
		this.card=card;
		//this.revealAtEnd=revealAtEnd;
		//this.revealWhileMoving=revealwhileMoving;
	}

	@Override
	public void execute() {
		ClientController.get().cardMoved(card, from, to,byWhomId);
		
	}
	
}
