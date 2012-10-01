package freeplay;

import handlers.PublicEventsHandler;
import utils.Card;
import utils.Player;
import utils.Public;

public class PublicHandler implements PublicEventsHandler{

	private boolean publicCardsVisible;
	
	public void setPublicCardsVisible(boolean publicCardsVisible) {
		this.publicCardsVisible = publicCardsVisible;
	}
	
	@Override
	public boolean onCardAdded(Public publicZone, Player player, Card card) {
		if(publicCardsVisible)
			card.reveal();
		else
			card.hide();
		return true;
	}

	@Override
	public boolean onCardRemoved(Public publicZone, Player player, Card card) {		
		//card.hide();
		return true;
	}

	@Override
	public boolean onCardRevealed(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onRoundEnd(Public publicZone, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFlipCard(Card card) {
		// TODO Auto-generated method stub
		return true;
	}

}
