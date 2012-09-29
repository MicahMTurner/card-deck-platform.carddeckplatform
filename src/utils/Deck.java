package utils;



import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import handlers.CardEventsHandler;

/**
 * represents a logic instance of a deck
 * @author Yoav
 *
 */
public class Deck{
	private Stack<Card> cards = new Stack<Card>();
	private final int timesToShuffle=2;	
	/**
	 * constructor
	 * @param handler card handler which will be used by all the cards
	 * @param shuffle true if want to shuffle the deck, false OW
	 */
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
	/**
	 * draw card from deck
	 * @return card from deck
	 */
	public Card drawCard() {
		return cards.pop();
		
	}
	
	public Stack<Card> getCards() {
		return cards;
	}
	/**
	 * get how many cards are in the deck
	 * @return number that represents the number of cards that are currently in the deck
	 */
	public int getSize() {
		return cards.size();
	}
}
