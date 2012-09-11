package utils;



import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import handlers.CardEventsHandler;


public class Deck{
	private Stack<Card> cards = new Stack<Card>();
	private final int timesToShuffle=2;	
	public Deck(CardEventsHandler handler,boolean shuffle) {
		for (StandartCard.Color color : StandartCard.Color.values()){
			
			for (int i=2;i<=14;i++){			
				cards.add(new StandartCard(handler,color.getCode()+i,"back",i,color));							
			}
		}	
		//check if shuffle requested
		if (shuffle){
			//true
			shuffle(timesToShuffle);
		}
	}
	
	/**
	 * 	pick card in random place and swap with card in random place 
	 *	do that size of deck times multiple time to shuffle.
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
	private void swap(int i, int randomPlace) {
		Collections.swap(cards,i,randomPlace);
	}

	public Card drawCard() {
		return cards.pop();
		
	}
	
	public Stack<Card> getCards() {
		return cards;
	}

	public int getSize() {
		return cards.size();
	}
}
