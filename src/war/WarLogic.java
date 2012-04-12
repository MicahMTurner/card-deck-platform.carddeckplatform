package war;


import java.util.ArrayList;

import war.actions.RecieveCardAction;

import communication.messages.Message;
import communication.server.ConnectionsManager;

import client.controller.ClientController;
import client.controller.actions.AddPlayerAction;
import client.controller.actions.DealCardAction;

import logic.card.CardLogic;
import logic.client.Deck;
import logic.client.Player;
import logic.actions.PublicActions;
import logic.actions.CardsActions;
import logic.client.GameLogic;




public class WarLogic extends GameLogic implements CardsActions,PublicActions{
	
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
			players.get(i%2).getHand().add(deck.drawCard());
		}
		
		//for(Player player : players){
		//	ConnectionsManager.getConnectionsManager().sendToAll(new Message(new RecieveCardAction()));
		//}
		try {
			Thread.sleep(1000);
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(0).getHand(),4)));
			Thread.sleep(1000);
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(1).getHand(),3)));
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//players.get(0).
		
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
		//ClientController.outgoingAPI().endTurn();
		return card;
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
	public void cardGiven(Player player, CardLogic card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveCard(Player from, Player to, CardLogic card) {
		// TODO Auto-generated method stub
		
	}


}
