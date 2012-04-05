package logic.actions;

import java.util.ArrayList;

import logic.card.Card;
import logic.client.Player;

import logic.client.Deck;

public interface CardsActions {
	public void dealCards(Deck deck,ArrayList<Player> players); 
	public void cardGiven(Card card);
	public void giveCard();
}
