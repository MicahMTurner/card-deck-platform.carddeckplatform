package handlers;

import utils.Card;
import utils.Player;

public interface PlayerEventsHandler extends Handler {
	public boolean onMyTurn(Player player);
	public boolean onTurnEnd(Player player);
	public boolean onCardAdded(Player target,Player byWhom,Card card);
	public boolean onCardRemoved(Player player,Card card);
	public boolean onCardRevealed(Player player,Card card);
	public boolean onRoundEnd(Player player);
}
