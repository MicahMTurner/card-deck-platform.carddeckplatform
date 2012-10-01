package handlers;

import utils.Card;
import utils.Player;
import utils.Public;
/**
 * 
 * handle different actions on public instance.
 * return true if the action is valid, 
 * or false OW to undo last action if it was made before calling the handler.
 *
 */
public interface PublicEventsHandler extends Handler{
	/**
	 * called AFTER given card was added to given public zone, use public.peek() to see
	 * first card in that public before given card was added, and use public.first()
	 * to see first card in that public at the current state (AFTER given card was added
	 * to that public)
	 * @param publicZone where card was added to
	 * @param player the player that placed given card in given public zone, null if logic did it.
	 * @param card the card that was added to given public zone
	 * @return true if given card can be placed at public zone by given player (null if logic), 
	 * false to undo the add card action and call this move illegal.
	 * @see Public
	 */
	public boolean onCardAdded(Public publicZone,Player player,Card card);
	/**
	 * called BEFORE given card was removed from given public zone by given player
	 * @param publicZone the public zone the card is about to be removed from
	 * @param player that made this action, null if logic did it.
	 * @param card given card that will be removed from given public zone
	 * @return true if valid action, false OW
	 */
	public boolean onCardRemoved(Public publicZone,Player player,Card card);
	/**
	 * called AFTER given card was revealed by given player (null if logic) that is in given public zone
	 * @param publicZone where given card is placed
	 * @param player the player that revealed the given card, null if logic did it.
	 * @param card the card the got revealed
	 * @return true if valid move, false OW (undo - hiding the card again)
	 */
	public boolean onCardRevealed(Public publicZone,Player player,Card card);
	/**
	 * called after public.endRound() was activated by given player (given player ended the round),
	 * null if logic did it
	 * @param publicZone what public zone called endRound function
	 * @param player the player that ended the round (null if logic did it)
	 * @return true if according to given public zone the round can be ended, false OW (usually true)
	 */
	public boolean onRoundEnd(Public publicZone,Player player);	
	
}
