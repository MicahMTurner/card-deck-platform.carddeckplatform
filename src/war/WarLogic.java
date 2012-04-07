package war;


import java.util.ArrayList;

import client.controller.Controller;

import logic.card.Card;
import logic.client.Deck;
import logic.client.Player;
import logic.actions.PublicActions;
import logic.actions.CardsActions;
import logic.client.GameLogic;
import communication.messages.SendCardMessage;
import communication.server.ServerMessageSender;




public class WarLogic extends GameLogic implements CardsActions,PublicActions{
	private ServerMessageSender serverMessageSender;
	public WarLogic() {
	}

	@Override
	public String lost() {
		
		return null;
	}

	@Override
	public void myTurn() {
		
		
	}

	@Override
	public void dealCards(Deck deck,ArrayList<Player> players) {
		deck.shuffle(2);
		int size=deck.getSize();
		for (int i=0;i<size;i++){
			
			serverMessageSender.sendTo(new SendCardMessage(deck.Draw().getId()), players.get(i%players.size()).getId());
		}
		
	}


	@Override
	public Card drawCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card gotCardFromDeck() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void showCard(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideCard(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card putInPublic(Player player, Card card) {		
		Controller.outgoingAPI().endTurn();
	}

	@Override
	public void removeFromPublic(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void placedInPublic(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cardGiven(Player player, Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveCard(Player from, Player to, Card card) {
		// TODO Auto-generated method stub
		
	}


}
