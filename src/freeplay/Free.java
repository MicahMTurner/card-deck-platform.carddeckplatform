package freeplay;

import java.util.ArrayList;
import java.util.Queue;

import client.gui.entities.Droppable;

import utils.Deck;
import utils.Position;
import utils.Position.Player;
import utils.Public;
import logic.client.AbstractDeck;
import logic.client.Game;

public class Free extends Game{

	@Override
	public AbstractDeck getDeck() {
		return super.deck;
	}

	@Override
	public Queue<Player> setTurns() {
		return null;
	}

	@Override
	public int minPlayers() {
		return 2;
	}

	@Override
	public int cardsForEachPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void dealCards() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLayouts(ArrayList<Droppable> droppables) {
		droppables.add(new Deck(new CardHandler(), true,Position.Button.BOTLEFT));
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
