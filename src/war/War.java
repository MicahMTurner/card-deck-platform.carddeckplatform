package war;

import java.util.ArrayList;
import java.util.Queue;

import communication.actions.DealCardAction;
import communication.messages.Message;
import communication.server.ConnectionsManager;


import utils.Card;
import utils.Deck;
import utils.Player;
import utils.Position;
import utils.Public;

import logic.client.AbstractDeck;
import logic.client.Game;




public class War extends Game{
	static public boolean tie=false;


	@Override
	public Queue<Position.Player> setTurns() {
		return utils.Turns.clockWise(Position.Player.BOTTOM);
		
	}

	@Override
	public int minPlayers() {		
		return 2;
	}

	@Override
	public int cardsForEachPlayer() {
		return 0;
	}

	

	@Override
	public String toString() {		
		return "War";
	}

	@Override
	public void dealCards() {
		//TODO add "default deal" or "deal all cards"
		int deckSize=deck.getSize();
		
		int numOfPlayers=players.size();
		
		ArrayList<ArrayList<Card>> playersCards= new ArrayList<ArrayList<Card>>();
		for (int i=0;i<numOfPlayers;i++){
			playersCards.add(new ArrayList<Card>());
		}

		for (int i=0;i<deckSize;i++){			
			Card card=deck.drawCard();			
			//card.setOwner(players.get(i%players.size()).getPosition());
			playersCards.get(i%players.size()).add(card);			
			
		}
		for (int i=0;i<players.size();i++){
			ConnectionsManager.getConnectionsManager().sendToAll(new Message(new DealCardAction(playersCards.get(i),false,false,-1,players.get(0).getId())));
		}
		
	}

	@Override
	public void getLayouts(ArrayList<Public> publics){//,ArrayList<Button>buttons) {
		publics.add(new Public(new PublicHandler(),Position.Public.MIDLEFT));
		publics.add(new Public(new PublicHandler(),Position.Public.MIDRIGHT));
	}

	@Override
	public Player createPlayer(String userName,
			utils.Position.Player position) {
		
		return new Player(userName,position,new PlayerHandler());
	}

	@Override
	public AbstractDeck getDeck() {		
		return new Deck(new CardHandler(),true);
	}	
	
}
	