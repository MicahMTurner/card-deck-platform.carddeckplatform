package logic.client;


import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import logic.card.CardLogic;

public abstract class Deck {
	//change to queue?
	public Stack<CardLogic> cards = new Stack<CardLogic>();
	
	
	public Stack<CardLogic> getCards() {
		return cards;
	}
		
	/**
	 * 	pick card in random place and swap with card in random place 
	 *	do that size of deck times (or maybe twice the size?)
	 *  @param timesToShuffle how many times user wants to shuffle
	 */
	public void shuffle(int timesToShuffle){
		Random random=new Random();
		int limit=cards.size();
		int randomPlace;
		for (int j=0;j<timesToShuffle;j++){
			for (int i=0;i<limit;i++){
				randomPlace=random.nextInt(limit);
				swap(i,randomPlace);
				cards.get(i);
			}
		}
	}

	public int getSize(){
		return cards.size();
	}
	private void swap(int i, int randomPlace) {
//		CardLogic temp=cards.get(i);
//		cards.add(i, cards.get(randomPlace));
//		cards.add(randomPlace,temp);		
		Collections.swap(cards,i,randomPlace);
	}

	public CardLogic drawCard() {
		return cards.pop();
		
	}
}
