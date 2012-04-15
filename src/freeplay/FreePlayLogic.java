package freeplay;

import java.util.ArrayList;

import client.controller.actions.DealCardAction;

import communication.messages.Message;
import communication.server.ConnectionsManager;

import logic.actions.CardsActions;
import logic.actions.PublicActions;
import logic.card.CardLogic;
import logic.client.Deck;
import logic.client.GameLogic;
import logic.client.Player;
import logic.host.Host;

public class FreePlayLogic extends GameLogic implements CardsActions,PublicActions {

	@Override
	public String lost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void myTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CardLogic drawCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardLogic gotCardFromDeck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showCard(Player player, CardLogic card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideCard(Player player, CardLogic card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CardLogic putInPublic(Player player, CardLogic card) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeFromPublic(Player player, CardLogic card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placedInPublic(Player player, CardLogic card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealCards(Deck deck, ArrayList<Player> players) {
		// TODO Auto-generated method stub
		deck.shuffle(2);
		ArrayList<CardLogic> cardsToSend = new ArrayList<CardLogic>();
		// convert from Stack to ArrayList.. :(
		for(CardLogic cl : deck.getCards()){
			cardsToSend.add(cl);
		}
		
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(cardsToSend,4)));
	}

	@Override
	public void cardGiven(Player player, CardLogic card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveCard(Player from, Player to, CardLogic card) {
		// TODO Auto-generated method stub
		
	}

}
