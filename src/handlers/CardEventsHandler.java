package handlers;

import utils.Card;

public interface CardEventsHandler extends Handler{
	public void onReveal(Card card);
	public void OnHide(Card card);
}
