package communication.actions;

import java.util.ArrayList;

import client.controller.ClientController;

import utils.Card;
import utils.Player;

public class CardAdded implements Action{
	//private ArrayList<Card> cards=new ArrayList<Card>();
	//private int cardId;
	private Card card;
	private Player byWhom;
	private int from;
	private int to;
	
	
	public CardAdded(Card card, int from,int to,Player byWhom) {		
	//	for(Card card : cards){
	//		this.cards.add(card);
	//	}
		this.from = from;
		this.to=to;
		this.byWhom=byWhom;
		this.card=card;
		//this.revealAtEnd=revealAtEnd;
		//this.revealWhileMoving=revealwhileMoving;
	}

	@Override
	public void execute() {
		ClientController.get().cardMoved(card, from, to,byWhom);
		
	}
	
}
