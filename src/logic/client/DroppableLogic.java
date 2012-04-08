package logic.client;

import java.util.ArrayList;

import logic.card.Card;

public class PublicArea {
	private ArrayList<Card> cards;
	
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void add(String userName,Card card){
		cards.add(card);
	}
}
