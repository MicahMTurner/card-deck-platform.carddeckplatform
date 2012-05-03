package utils;

import utils.Card.CardColor;
import IDmaker.IDMaker;
import handlers.CardEventsHandler;
import logic.client.AbstractDeck;


public class Deck extends AbstractDeck{
	private final int timesToShuffle=2;	
	public Deck(CardEventsHandler handler,boolean shuffle) {		
		for (CardColor color : CardColor.values()){
			for (int i=2;i<=14;i++){			
				cards.add(new Card(i,color,handler));							
			}
		}	
		//check if shuffle requested
		if (shuffle){
			//true
			super.shuffle(timesToShuffle);
		}
	}
}
