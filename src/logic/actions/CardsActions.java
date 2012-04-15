package logic.actions;

import java.util.ArrayList;

import logic.card.CardLogic;
import logic.client.Player;

import logic.client.Deck;

public interface CardsActions {
	public void dealCards(Deck deck,ArrayList<Player> players); 
	public void cardGiven(Player player,CardLogic card);
	public void giveCard(Player from,Player to,CardLogic card);
}
