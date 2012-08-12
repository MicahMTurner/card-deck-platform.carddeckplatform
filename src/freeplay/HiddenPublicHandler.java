package freeplay;

import utils.Card;
import utils.Player;
import utils.Public;
import handlers.PublicEventsHandler;

public class HiddenPublicHandler  implements PublicEventsHandler {

	@Override
	public boolean onCardAdded(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
		card.hide();
		return true;
	}

	@Override
	public boolean onCardRemoved(Public publicZone, Player player, Card card) {
		// TODO Auto-generated method stub
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

}
