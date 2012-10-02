package handlers;

import utils.Card;
import utils.Player;
/**
 * 
 * handle different actions on player instance.
 * return true if the action is valid, 
 * or false OW to undo last action if it was made before calling the handler.
 *
 */
public interface PlayerEventsHandler extends Handler {
	
	/**
	 * called after player got his turn
	 * @param player player that starts his turn
	 * @return true if player can start his turn, false OW
	 */
	public boolean onMyTurn(Player player);
	
	/**
	 * called after user used player.endTurn()
	 * @param player player that ended his turn
	 * @return true if valid, false OW
	 * @see Player
	 */
	public boolean onTurnEnd(Player player);
	
	/**
	 * called AFTER a card was given to "target" player 
	 * @param target player that got the card
	 * @param byWhom who game him that card, null if the logic did it (no one used the UI)
	 * @param card given card
	 * @return true if player can be given the given card by that specific player, false OW
	 */
	public boolean onCardAdded(Player target,Player byWhom,Card card);
	
	/**
	 * called BEFORE given card was removed from given player
	 * @param player from whom the card should be removed
	 * @param card the card to remove
	 * @return true if given card can be removed from given player, false OW
	 */
	public boolean onCardRemoved(Player player,Card card);
	
	/**
	 * @deprecated
	 * called after card was revealed
	 * @param player that revealed the given card
	 * @param card gived card that got revealed
	 * @return true if card can be revealed by given player, false OW
	 */
	public boolean onCardRevealed(Player player,Card card);
	
	/**
	 * called after user used player.endRound()
	 * @param player player on whom .endRound() function was called
	 * @see Player
	 * @return true if player can end his turn, false OW (should be usually true)
	 */
	public boolean onRoundEnd(Player player);
	
}
