package freeplay;

import java.util.ArrayList;
import java.util.Queue;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;

import client.controller.ClientController;
import client.gui.entities.Droppable;

import utils.AbstractDeck;
import utils.Card;
import utils.Deck;
import utils.Position;
import utils.Position.Player;
import utils.Public;
import logic.client.Game;

public class FreePlay extends Game{

	@Override
	public Deck getDeck() {
		return new Deck(new CardHandler(), true);
	}

	@Override
	public Queue<Player> setTurns() {
		return null;
	}

	@Override
	public int minPlayers() {
		return 1;
	}

	@Override
	public int cardsForEachPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void dealCards() {
		int size=deck.getSize();
		ArrayList<Card>cards=new ArrayList<Card>();
		for (int i=0;i<size;i++){
			cards.add(deck.drawCard());
		}
		ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(cards,false,false,-1,ClientController.get().getZone(Position.Button.BOTLEFT).getId())));
	}

	@Override
	public void getLayouts(ArrayList<Droppable> droppables) {
		droppables.add(new AbstractDeck(Position.Button.BOTLEFT));
		//(new CardHandler(), true,));
		droppables.add(new Public(new PublicHandler(), Position.Public.MID));
	}

	@Override
	public String toString() {
		return "free play";
	}

	@Override
	public utils.Player createPlayer(String userName, Player position) {
		
		return new utils.Player(userName, position, new PlayerHandler());
	}

}
