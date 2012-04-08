package logic.actions;

import logic.card.CardLogic;
import logic.client.Player;

public interface RevealActions {
	public void showCard(Player player,CardLogic card);
	public void hideCard(Player player,CardLogic card);
}
