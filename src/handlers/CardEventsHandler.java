package handlers;

import utils.Card;
/**
 * 
 * called when card is being hidden or revealed
 *
 */
public interface CardEventsHandler extends Handler{
	/**
	 * called after card is being revealed
	 * @param card the card that got revealed
	 */
	public void onReveal(Card card);
	/**
	 * called after card is being hidden
	 * @param card the card that got hidden
	 */
	public void OnHide(Card card);
}
