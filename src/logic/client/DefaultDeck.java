package logic.client;

import logic.builtIn.defaultCards.Club;
import logic.builtIn.defaultCards.Diamond;
import logic.builtIn.defaultCards.Heart;
import logic.builtIn.defaultCards.Spade;

public class DefaultDeck extends Deck{
	private final int timesToShuffle=2;
	public DefaultDeck() {
		for (int i=2;i<=14;i++){
			
			cards.add(new Heart(i));
			cards.add(new Club(i));
			cards.add(new Diamond(i));
			cards.add(new Spade(i));
		}
		super.shuffle(timesToShuffle);
	}

}
