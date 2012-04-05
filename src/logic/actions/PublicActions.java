package logic.actions;


import logic.card.Card;
import logic.client.Player;

public interface PublicActions {
	public Card putInPublic(Player player);
	public void removeFromPublic(Card card);
	public void placedInPublic(Card card);
	
}
