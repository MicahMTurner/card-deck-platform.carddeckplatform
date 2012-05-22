package war;


import utils.Card;
import utils.Player;
import handlers.PlayerEventsHandler;

public class PlayerHandler implements PlayerEventsHandler{

	@Override
	public boolean onMyTurn(Player player) {		
		return true;
	}

	@Override
	public boolean onTurnEnd(Player player) {		
		return true;
	}

	@Override
	public boolean onCardAdded(Player player, Card card) {
		card.setCoord(player.getX(), player.getY());
		return true;
	}

	@Override
	public boolean onCardRemoved(Player player, Card card) {	
		return true;
	}

	@Override
	public boolean onCardRevealed(Player player, Card card) {		
		return false;
	}

	@Override
	public boolean onRoundEnd(Player player) {
		return true;
	}

}
