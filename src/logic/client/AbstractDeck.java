package logic.client;


import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import utils.Card;


public abstract class AbstractDeck {
	//change to queue?
	public Stack<Card> cards = new Stack<Card>();
	
	
	public Stack<Card> getCards() {
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
		Collections.swap(cards,i,randomPlace);
	}

	public Card drawCard() {
		return cards.pop();
		
	}
}
