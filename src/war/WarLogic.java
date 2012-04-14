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
import logic.host.Host;




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
		for (int i=0;i<2;i++){
			CardLogic card=deck.drawCard();
			Player player=players.get(i%Host.players.size());
			card.setOwner(player.getUsername());
			player.getHand().add(card);
		}
		
		//for(Player player : players){
		//	ConnectionsManager.getConnectionsManager().sendToAll(new Message(new RecieveCardAction()));
		//}
			
			//ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(0).getHand(),4)));
			//System.out.println(players.get(0).getHand().size());
			
			//ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(players.get(1).getHand(),3)));
			//System.out.println(players.get(0).getHand().size());
			

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
