package president;

import utils.Card;
import utils.Player;
import utils.Public;
import handlers.PublicEventsHandler;

public class PublicHandler implements PublicEventsHandler{

	@Override
	public boolean onCardAdded(Public publicZone, Player player, Card card) {
		if (card.compareTo(publicZone.peek())>0 && publicZone.cardsHolding()<2){
			return true;
		}
		return false;
	}

	@Override
	public boolean onCardRemoved(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
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

}
