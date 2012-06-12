package president;

import utils.Card;
import utils.Player;
import handlers.PlayerEventsHandler;

public class PlayerHandler implements PlayerEventsHandler{
	
	
	@Override
	public boolean onMyTurn(Player player) {
		if (President.passed){
			player.endTurn();
		}
		return false;
	}

	@Override
	public boolean onTurnEnd(Player player) {
		return false;
	}

	@Override
	public boolean onCardAdded(Player player, Card card) {
		return false;
	}

	@Override
	public boolean onCardRemoved(Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onCardRevealed(Player player, Card card) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onRoundEnd(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

}
