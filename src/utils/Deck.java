package utils;



import handlers.CardEventsHandler;
import logic.client.AbstractDeck;


public class Deck extends AbstractDeck{
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
			super.shuffle(timesToShuffle);
		}
	}
}
