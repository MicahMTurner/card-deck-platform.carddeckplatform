package logic.client;

import logic.builtIn.defaultCards.Club;
import logic.builtIn.defaultCards.Diamond;
import logic.builtIn.defaultCards.Heart;
import logic.builtIn.defaultCards.Spade;

public class DefaultDeck extends Deck{
	private final int timesToShuffle=2;
	public DefaultDeck() {
		int id=1;
		for (int i=2;i<=14;i++){
			
			cards.add(new Heart(2,id));
			cards.add(new Club(2,id+1));
			cards.add(new Diamond(2,id+2));
			cards.add(new Spade(2,id+3));
			id+=4;
		}
		super.shuffle(timesToShuffle);
	}

}
