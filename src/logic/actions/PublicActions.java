package logic.actions;


import logic.card.Card;
import logic.client.Player;

public interface PublicActions {
	public Card putInPublic(Player player,Card card);
	public void removeFromPublic(Player player,Card card);
	public void placedInPublic(Player player,Card card);
	
}
