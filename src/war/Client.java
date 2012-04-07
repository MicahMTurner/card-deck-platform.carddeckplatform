package war;

import java.util.ArrayList;

import client.controller.Controller;

import logic.actions.CardsActions;
import logic.actions.PublicActions;
import logic.card.Card;
import logic.client.Deck;
import logic.client.GameLogic;
import logic.client.Player;

public class Client extends GameLogic implements CardsActions,PublicActions{

	@Override
	public String lost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void myTurn() {		
		;
		
	}

	/**
	 * no deck in war game. each has his own cards
	 */
	@Override
	public Card drawCard() {		
		return null;
	}

	@Override
	public Card gotCardFromDeck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showCard(Player player,Card card) {
		
		Controller.getCommands().revealCard(player,card);
	}

	@Override
	public void hideCard(Player player,Card card) {
		Controller.getCommands().hideCard(player,card);
		
	}

	@Override
	public void putInPublic(Player player,Card card) {
		Controller.getCommands().putInPublic(player,card,true);		
		
	}

	@Override
	public void removeFromPublic(Player player,Card card) {
		Controller.getCommands().removeFromPublic(player,card);
	}

	@Override
	public void placedInPublic(Player player,Card card) {
		
		
	}

	@Override
	public void dealCards(Deck deck, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardGiven(Player player,Card card) {
		
		
	}

	@Override
	public void giveCard(Player from,Player to,Card card) {
		Controller.getCommands().giveCard(from,to,card);
		
	}

}
