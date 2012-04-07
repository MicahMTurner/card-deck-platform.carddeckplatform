package logic.actions;

import logic.card.Card;
import logic.client.Player;

public interface RevealActions {
	public void showCard(Player player,Card card);
	public void hideCard(Player player,Card card);
}
