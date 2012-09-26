package handlers;

import utils.Card;
import utils.Player;
import utils.Public;

public interface PublicEventsHandler extends Handler{
	public boolean onCardAdded(Public publicZone,Player player,Card card);
	public boolean onCardRemoved(Public publicZone,Player player,Card card);
	public boolean onCardRevealed(Public publicZone,Player player,Card card);
	public boolean onRoundEnd(Public publicZone,Player player);	
	
}
